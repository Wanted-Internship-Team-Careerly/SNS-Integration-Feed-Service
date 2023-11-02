package com.snsIntegrationFeedService.post.controller;

import com.snsIntegrationFeedService.post.dto.request.StaticsRequest;
import com.snsIntegrationFeedService.post.dto.response.StaticsResponse;

import com.snsIntegrationFeedService.post.service.StaticService;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StaticsController {

    private final StaticService staticService;

    @GetMapping(value = "/api/posts/statics")
    public ResponseEntity<List<StaticsResponse>> getResponse(@ModelAttribute StaticsRequest request) {

        return ResponseEntity.ok()
                .body(staticService.getListStaticsResponse(request));
    }

}
