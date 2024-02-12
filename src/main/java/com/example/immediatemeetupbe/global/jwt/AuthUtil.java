package com.example.immediatemeetupbe.global.jwt;

import com.example.immediatemeetupbe.domain.member.entity.Member;
import com.example.immediatemeetupbe.global.exception.BaseException;
import com.example.immediatemeetupbe.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static com.example.immediatemeetupbe.global.exception.BaseExceptionStatus.ERROR_GET_MEMBER;
import static com.example.immediatemeetupbe.global.exception.BaseExceptionStatus.NO_EXIST_MEMBER;

@Component
@RequiredArgsConstructor
public class AuthUtil {

    private final MemberRepository memberRepository;

    public Member getLoginMember() {
        try {
            String loginMember = SecurityContextHolder.getContext().getAuthentication().getName();
            return memberRepository.findByEmail(loginMember)
                    .orElseThrow(() -> new BaseException(NO_EXIST_MEMBER + loginMember));
        } catch (BaseException e) {
            throw new BaseException(ERROR_GET_MEMBER.getMessage());
        }
    }
}
