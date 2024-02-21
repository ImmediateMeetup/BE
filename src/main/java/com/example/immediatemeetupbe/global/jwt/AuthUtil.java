package com.example.immediatemeetupbe.global.jwt;

import com.example.immediatemeetupbe.domain.member.entity.Member;
import com.example.immediatemeetupbe.global.exception.BusinessException;
import com.example.immediatemeetupbe.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static com.example.immediatemeetupbe.global.exception.ErrorCode.ERROR_GET_MEMBER;
import static com.example.immediatemeetupbe.global.exception.ErrorCode.NO_EXIST_MEMBER;

@Component
@RequiredArgsConstructor
public class AuthUtil {

    private final MemberRepository memberRepository;

    public Member getLoginMember() {
        try {
            String loginMember = SecurityContextHolder.getContext().getAuthentication().getName();
            return memberRepository.findByEmail(loginMember)
                    .orElseThrow(() -> new BusinessException(NO_EXIST_MEMBER));
        } catch (BusinessException e) {
            throw new BusinessException(ERROR_GET_MEMBER);
        }
    }
}
