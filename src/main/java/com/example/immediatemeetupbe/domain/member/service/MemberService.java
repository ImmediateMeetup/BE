package com.example.immediatemeetupbe.domain.member.service;

import com.example.immediatemeetupbe.domain.member.dto.request.MemberLoginRequest;
import com.example.immediatemeetupbe.domain.member.dto.request.MemberSignUpRequest;
import com.example.immediatemeetupbe.domain.member.entity.Member;
import com.example.immediatemeetupbe.global.exception.BaseException;
import com.example.immediatemeetupbe.global.exception.BaseExceptionStatus;
import com.example.immediatemeetupbe.global.jwt.TokenProvider;
import com.example.immediatemeetupbe.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

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

        return tokenProvider.createToken(member.getEmail(), roles);
    }
}
