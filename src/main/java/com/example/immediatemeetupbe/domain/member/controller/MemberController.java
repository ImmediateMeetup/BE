package com.example.immediatemeetupbe.domain.member.controller;

import com.example.immediatemeetupbe.domain.member.dto.request.EditPasswordRequest;
import com.example.immediatemeetupbe.domain.member.dto.request.MemberLoginRequest;
import com.example.immediatemeetupbe.domain.member.dto.request.MemberModifyRequest;
import com.example.immediatemeetupbe.domain.member.dto.request.MemberSignUpRequest;
import com.example.immediatemeetupbe.domain.member.dto.response.EmailConfirmResponse;
import com.example.immediatemeetupbe.domain.member.dto.response.MemberProfileResponse;
import com.example.immediatemeetupbe.domain.member.dto.response.MemberResponse;
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

    @GetMapping("/search")
    public ResponseEntity<MemberResponse> getMemberByKeyword(@RequestParam String keyword) {
        return ResponseEntity.ok(memberService.getMemberByKeyword(keyword));
    }

    @PostMapping
    public ResponseEntity<Void> signUp(@Valid @RequestBody MemberSignUpRequest memberSignUpRequest) {
        memberService.signUp(memberSignUpRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberLoginRequest memberLoginRequest) {
        return ResponseEntity.ok(memberService.login(memberLoginRequest));
    }

    @PatchMapping("/edit-password")
    public ResponseEntity<Void> editPassword(
        @Valid @RequestBody EditPasswordRequest editPasswordRequest) {
        memberService.editPassword(editPasswordRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/modify-user")
    public ResponseEntity<Void> modifyProfile(MemberModifyRequest memberModifyRequest) {
        memberService.modifyProfile(memberModifyRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMember() {
        memberService.deleteMember();
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<MemberProfileResponse> retrieveMyProfile() {
        return ResponseEntity.ok(memberService.retrieveMyProfile());
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberProfileResponse> retrieveMemberProfile(
        @PathVariable Long memberId) {
        return ResponseEntity.ok(memberService.retrieveMemberProfile(memberId));
    }

    @PostMapping("/emails/verification-requests")
    public ResponseEntity<Void> sendMessage(@RequestParam("email") @Valid String email) {
        memberService.sendCodeToEmail(email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/emails/verifications")
    public ResponseEntity<EmailConfirmResponse> verificationEmail(
        @RequestParam("email") @Valid String email,
        @RequestParam("code") String authCode) {
        return ResponseEntity.ok(memberService.verifiedCode(email, authCode));
    }
}
