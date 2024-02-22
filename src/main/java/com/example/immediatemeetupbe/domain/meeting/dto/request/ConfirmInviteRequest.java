package com.example.immediatemeetupbe.domain.meeting.dto.request;

import lombok.Getter;

@Getter
public class ConfirmInviteRequest {
    private Long meetingId;
    private ConfirmationStatus status;

    public enum ConfirmationStatus {
        ACCEPTED,
        REJECTED
    }
}
