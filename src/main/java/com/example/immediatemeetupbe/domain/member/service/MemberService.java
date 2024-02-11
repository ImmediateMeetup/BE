package com.example.immediatemeetupbe.domain.member.service;

import com.example.immediatemeetupbe.domain.member.dto.request.MemberSignUpRequest;
import com.example.immediatemeetupbe.domain.member.entity.Member;
import com.example.immediatemeetupbe.repository.MemberRepository;
import com.example.immediatemeetupbe.global.error.exception.EmailAlreadyExistException;
import com.example.immediatemeetupbe.global.error.exception.PasswordUncheckedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    public void signUp(MemberSignUpRequest request) {
        if (memberRepository.findByEmail(request.getEmail()).isPresent()){
            throw new EmailAlreadyExistException();
        }

        if (!request.getPassword().equals(request.getCheckedPassword())){
            throw new PasswordUncheckedException();
        }

        Member member = memberRepository.save(request.toEntity());
        member.encodePassword(passwordEncoder);
    }
}
