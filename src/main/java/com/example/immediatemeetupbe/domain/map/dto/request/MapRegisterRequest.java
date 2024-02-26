package com.example.immediatemeetupbe.domain.map.dto.request;

import com.example.immediatemeetupbe.domain.map.vo.Latitude;
import com.example.immediatemeetupbe.domain.map.vo.Longitude;
import lombok.Getter;

@Getter
public class MapRegisterRequest {
    private Longitude longitude;
    private Latitude latitude;
}
