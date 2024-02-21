package com.example.immediatemeetupbe.domain.member.service;

import com.example.immediatemeetupbe.domain.member.dto.MemberDto;
import com.example.immediatemeetupbe.domain.member.dto.TokenDto;
import com.example.immediatemeetupbe.domain.member.dto.request.EditPasswordRequest;
import com.example.immediatemeetupbe.domain.member.dto.request.MemberLoginRequest;
import com.example.immediatemeetupbe.domain.member.dto.request.MemberModifyRequest;
import com.example.immediatemeetupbe.domain.member.dto.request.MemberSignUpRequest;
import com.example.immediatemeetupbe.domain.member.dto.response.EmailConfirmResponse;
import com.example.immediatemeetupbe.domain.member.dto.response.MemberProfileResponse;
import com.example.immediatemeetupbe.domain.member.dto.response.MemberResponse;
import com.example.immediatemeetupbe.domain.member.entity.Member;
import com.example.immediatemeetupbe.domain.member.entity.auth.RefreshToken;
import com.example.immediatemeetupbe.global.aws.S3Util;
import com.example.immediatemeetupbe.global.exception.BusinessException;
import com.example.immediatemeetupbe.global.exception.ErrorCode;
import com.example.immediatemeetupbe.global.jwt.AuthUtil;
import com.example.immediatemeetupbe.global.jwt.TokenProvider;
import com.example.immediatemeetupbe.global.mail.MailUtil;
import com.example.immediatemeetupbe.domain.member.repository.MemberRepository;
import com.example.immediatemeetupbe.domain.member.repository.RefreshTokenRepository;
import com.example.immediatemeetupbe.global.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.immediatemeetupbe.global.exception.ErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MailService mailService;
    private final RedisService redisService;
    private final TokenProvider tokenProvider;
    private final AuthUtil authUtil;
    private final MailUtil mailUtil;
    private final S3Util s3Util;

    private static final String AUTH_CODE_PREFIX = "AuthCode ";

    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

    @Transactional
    public void signUp(MemberSignUpRequest request) {
        if (memberRepository.findByEmail(request.getEmail()).isPresent()){
            throw new BusinessException(EMAIL_ALREADY_EXIST);
        }

        if (!request.getPassword().equals(request.getCheckedPassword())){
            throw new BusinessException(PASSWORD_UNCHECK);
        }

        Member member = memberRepository.save(request.toEntity());
        member.encodePassword(passwordEncoder);
    }

    @Transactional
    public String login(MemberLoginRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException(NOT_VALIDATE_EMAIL));

        if (!member.checkPassword(passwordEncoder, request.getPassword())) {
            throw new BusinessException(WRONG_PASSWORD);
        }

        List<String> roles = new ArrayList<>();
        roles.add(member.getAuthority().name());

        TokenDto token = tokenProvider.createAllToken(member.getEmail(), roles);
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByEmail(member.getEmail());
        if(refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(token.getRefreshToken()));
        } else {
            RefreshToken newToken = new RefreshToken(token.getRefreshToken(), request.getEmail());
            refreshTokenRepository.save(newToken);
        }

        return token.getAccessToken();
    }

    @Transactional
    public void modifyProfile(MemberModifyRequest request) {
        Member member = authUtil.getLoginMember();

        if (request.getProfileImage() != null) {
            String profileImage = s3Util.uploadFile(request.getProfileImage());
            member.modify(request.getEmail(), request.getName(), profileImage,
                    request.getPhoneNumber(), request.getAddress());
            return;
        }

        member.modify(request.getEmail(), request.getName(),
                member.getProfileImage(), request.getPhoneNumber(),
                request.getAddress());
    }

    public void sendCodeToEmail(String toEmail) {
        checkDuplicatedEmail(toEmail);
        String title = "[우리 지금 만나, 당장 만나] 이메일 인증 번호";
        String authCode = mailUtil.createCode();
        mailService.sendEmail(toEmail, title, authCode);
        // 이메일 인증 요청 시 인증 번호 Redis에 저장 ( key = "AuthCode " + Email / value = AuthCode )
        redisService.setValues(AUTH_CODE_PREFIX + toEmail,
                authCode, Duration.ofMillis(this.authCodeExpirationMillis));
    }

    private void checkDuplicatedEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()) {
            log.debug("MemberServiceImpl.checkDuplicatedEmail exception occur email: {}", email);
            throw new BusinessException(EMAIL_ALREADY_EXIST);
        }
    }

    public EmailConfirmResponse verifiedCode(String email, String authCode) {
        checkDuplicatedEmail(email);
        String redisAuthCode = redisService.getValues(AUTH_CODE_PREFIX + email);
        if(redisService.checkExistsValue(redisAuthCode) && redisAuthCode.equals(authCode)) {
           return EmailConfirmResponse.from("인증 성공");
        }
        return EmailConfirmResponse.from("인증 실패");
    }

    @Transactional
    public void editPassword(EditPasswordRequest request) {
        if(!request.getPassword().equals(request.getCheckPassword())) {
            throw new BusinessException(PASSWORD_UNCHECK);
        }

        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException(NOT_VALIDATE_EMAIL));
        String password = passwordEncoder.encode(request.getPassword());

        member.editPassword(password);
    }

    @Transactional
    public void deleteMember() {
        Member member = authUtil.getLoginMember();
        memberRepository.delete(member);
    }

    public MemberProfileResponse retrieveMyProfile() {
        Member member = authUtil.getLoginMember();

        return MemberProfileResponse.builder()
                .email(member.getEmail())
                .name(member.getName())
                .image(member.getProfileImage())
                .phoneNumber(member.getPhoneNumber())
                .address(member.getAddress())
                .build();
    }

    public MemberProfileResponse retrieveMemberProfile(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(NO_EXIST_MEMBER));

        return MemberProfileResponse.builder()
                .email(member.getEmail())
                .name(member.getName())
                .image(member.getProfileImage())
                .phoneNumber(member.getPhoneNumber())
                .address(member.getAddress())
                .build();
    }

    public MemberResponse getMemberByKeyword(String keyword) {
        List<Member> members = memberRepository.findByEmailContainingOrNameContaining(keyword, keyword);

//        if (members.isEmpty()) {
//            throw new BusinessException(NO_EXIST_MEMBER);
//        }

        List<MemberDto> memberDtoList = members.stream()
                .map(MemberDto::from)
                .toList();

        return MemberResponse.builder()
                .members(memberDtoList)
                .build();
    }
}
