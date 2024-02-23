package com.example.immediatemeetupbe.domain.map.vo;

import lombok.Getter;
import lombok.Value;

@Getter
public class Point {

    long x;
    long y;

    public Point(long x, long y) {
        this.x = x;
        this.y = y;
    }

}