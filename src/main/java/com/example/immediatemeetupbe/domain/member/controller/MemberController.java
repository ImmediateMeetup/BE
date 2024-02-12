package com.example.immediatemeetupbe.domain.member.controller;

import com.example.immediatemeetupbe.domain.member.dto.request.MemberLoginRequest;
import com.example.immediatemeetupbe.domain.member.dto.request.MemberModifyRequest;
import com.example.immediatemeetupbe.domain.member.dto.request.MemberSignUpRequest;
import com.example.immediatemeetupbe.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberLoginRequest memberLoginRequest) {
        return ResponseEntity.ok(memberService.login(memberLoginRequest));
    }

    @PatchMapping("modify-user")
    public ResponseEntity<Void> modifyProfile(MemberModifyRequest memberModifyRequest) {
        memberService.modifyProfile(memberModifyRequest);
        return ResponseEntity.ok().build();
    }
}
