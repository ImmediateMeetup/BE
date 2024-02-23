package com.example.immediatemeetupbe.domain.map.controller;

import com.example.immediatemeetupbe.domain.map.dto.request.MapRegisterRequest;
import com.example.immediatemeetupbe.domain.map.dto.response.MapResponse;
import com.example.immediatemeetupbe.domain.map.service.MapService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("http://localhost:8080/api/meet-up/map")
public class MapController {

    private final MapService mapService;

    @PostMapping("/{meeting_id}")
    public ResponseEntity<MapResponse> registerUserLocation(
        @PathVariable("meeting_id") Long meeting_id,
        @RequestBody MapRegisterRequest mapRegisterRequest) {
        return ResponseEntity.ok()
            .body(mapService.registerUserLocation(meeting_id, mapRegisterRequest));
    }

    @PatchMapping("/{meeting_id}")
    public ResponseEntity<MapResponse> modifyUserLocation(
        @PathVariable("meeting_id") Long meeting_id,
        @RequestBody MapRegisterRequest mapRegisterRequest) {
        return ResponseEntity.ok()
            .body(mapService.modifyUserLocation(meeting_id, mapRegisterRequest));
    }

//    @GetMapping("/point/{meeting_id}")
//    public ResponseEntity<MapResponse> getCalculatePoint(
//        @PathVariable("meeting_id") Long meeting_id) {
//        return ResponseEntity.ok()
//            .body(mapService.getCalculatePoint(meeting_id));
//    }

}
