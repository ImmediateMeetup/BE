package com.example.immediatemeetupbe.domain.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class MemberEditRequest {

    private String email;
    private String name;
    private MultipartFile profileImage;
    private String address;
    private String phoneNumber;
}
