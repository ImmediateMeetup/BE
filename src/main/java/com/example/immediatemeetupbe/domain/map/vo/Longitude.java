package com.example.immediatemeetupbe.domain.map.vo;

import lombok.Value;

@Value
public class Longitude {

    private static final Long LONGITUDE_KOREA_MIN = 33L;
    private static final Long LONGITUDE_KOREA_MAX = 43L;

    Long longitude;

    public Longitude(Long longitude) {
        if (LONGITUDE_KOREA_MIN < longitude && LONGITUDE_KOREA_MAX > longitude) {
            this.longitude = longitude;
        }
        throw new IllegalArgumentException("유효하지 않은 경도 값입니다.");
    }
}
