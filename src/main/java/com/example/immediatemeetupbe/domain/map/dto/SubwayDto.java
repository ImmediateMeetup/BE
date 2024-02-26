package com.example.immediatemeetupbe.domain.map.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubwayDto {

    //    {"STATN_ID":"0150","STATN_NM":"서울역","ROUTE":"1호선","CRDNT_Y":"37.556228","CRDNT_X":"126.972135"}
    @JsonProperty("STATN_ID")
    private String STATN_ID;
    @JsonProperty("STATN_NM")
    private String STATN_NM;
    @JsonProperty("ROUTE")
    private String ROUTE;
    @JsonProperty("CRDNT_Y")
    private double CRDNT_Y;
    @JsonProperty("CRDNT_X")
    private double CRDNT_X;

    @Override
    public String toString() {
        return "SubwayDto{" +
            "STATN_ID='" + STATN_ID + '\'' +
            ", STATN_NM='" + STATN_NM + '\'' +
            ", ROUTE='" + ROUTE + '\'' +
            ", CRDNT_Y='" + CRDNT_Y + '\'' +
            ", CRDNT_X='" + CRDNT_X + '\'' +
            '}';
    }

    public SubwayDto(String stainId, String statnNm, String route, long crdntY, long crdntX) {
        this.STATN_ID = stainId;
        this.STATN_NM = statnNm;
        this.ROUTE = route;
        this.CRDNT_Y = crdntY;
        this.CRDNT_X = crdntX;
    }

    public SubwayDto() {

    }

}
