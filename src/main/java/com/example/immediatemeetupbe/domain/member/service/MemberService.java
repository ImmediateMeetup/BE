package com.example.immediatemeetupbe.domain.member.service;

import com.example.immediatemeetupbe.domain.member.dto.TokenDto;
import com.example.immediatemeetupbe.domain.member.dto.request.MemberLoginRequest;
import com.example.immediatemeetupbe.domain.member.dto.request.MemberModifyRequest;
import com.example.immediatemeetupbe.domain.member.dto.request.MemberSignUpRequest;
import com.example.immediatemeetupbe.domain.member.dto.response.EmailConfirmResponse;
import com.example.immediatemeetupbe.domain.member.entity.Member;
import com.example.immediatemeetupbe.domain.member.entity.auth.RefreshToken;
import com.example.immediatemeetupbe.global.aws.S3Util;
import com.example.immediatemeetupbe.global.exception.BaseException;
import com.example.immediatemeetupbe.global.exception.BaseExceptionStatus;
import com.example.immediatemeetupbe.global.jwt.AuthUtil;
import com.example.immediatemeetupbe.global.jwt.TokenProvider;
import com.example.immediatemeetupbe.global.mail.MailUtil;
import com.example.immediatemeetupbe.domain.member.repository.MemberRepository;
import com.example.immediatemeetupbe.domain.member.repository.RefreshTokenRepository;
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
            throw new BaseException(BaseExceptionStatus.EMAIL_ALREADY_EXIST.getMessage());
        }

        if (!request.getPassword().equals(request.getCheckedPassword())){
            throw new BaseException(BaseExceptionStatus.PASSWORD_UNCHECK.getMessage());
        }

        Member member = memberRepository.save(request.toEntity());
        member.encodePassword(passwordEncoder);
    }

    @Transactional
    public String login(MemberLoginRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BaseException(BaseExceptionStatus.NO_AUTH_MEMBER.getMessage()));

        if (!member.checkPassword(passwordEncoder, request.getPassword())) {
            throw new BaseException(BaseExceptionStatus.WRONG_PASSWORD.getMessage());
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
            throw new BaseException(BaseExceptionStatus.EMAIL_ALREADY_EXIST.getMessage());
        }
    }

    public EmailConfirmResponse verifiedCode(String email, String authCode) {
        this.checkDuplicatedEmail(email);
        String redisAuthCode = redisService.getValues(AUTH_CODE_PREFIX + email);
        if(redisService.checkExistsValue(redisAuthCode) && redisAuthCode.equals(authCode)) {
           return EmailConfirmResponse.from("인증 성공");
        }
        return EmailConfirmResponse.from("인증 실패");
    }
}
