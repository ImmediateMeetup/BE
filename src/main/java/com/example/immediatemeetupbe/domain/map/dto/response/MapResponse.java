package com.example.immediatemeetupbe.domain.map.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MapResponse {

    private String subwayId;
    private String subwayName;
    private String route;
    private double longitude;
    private double latitude;
}
