package com.example.immediatemeetupbe.domain.User.controller;

import com.example.immediatemeetupbe.domain.User.dto.request.MemberSignUpRequest;
import com.example.immediatemeetupbe.domain.User.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meet-up/user")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Void> signUp(@Valid @RequestBody MemberSignUpRequest memberSignUpRequest) {
        memberService.signUp(memberSignUpRequest);
        return ResponseEntity.ok().build();
    }
}
