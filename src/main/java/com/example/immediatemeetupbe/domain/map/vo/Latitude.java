package com.example.immediatemeetupbe.domain.map.vo;

import lombok.Value;

@Value
public class Latitude {

    private static final Long LATITUDE_KOREA_MIN = 33L;
    private static final Long LATITUDE_KOREA_MAX = 43L;

    Long latitude;

    public Latitude(Long latitude) {
        if (LATITUDE_KOREA_MIN > latitude && LATITUDE_KOREA_MAX < latitude) {
            throw new IllegalArgumentException("유효하지 않은 위도 값입니다.");
        }
        this.latitude = latitude;
    }
}
