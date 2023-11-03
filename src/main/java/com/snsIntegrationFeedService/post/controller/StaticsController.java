package com.snsIntegrationFeedService.post.controller;

import com.snsIntegrationFeedService.common.security.UserDetailsImpl;
import com.snsIntegrationFeedService.post.dto.request.StaticsRequestDto;
import com.snsIntegrationFeedService.post.dto.response.StaticsResponseDto;

import com.snsIntegrationFeedService.post.service.StaticService;

import com.snsIntegrationFeedService.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StaticsController {

    private final StaticService staticService;
    private final UserService userService;

    @GetMapping(value = "/api/posts/statics")
    public ResponseEntity<List<StaticsResponseDto>> getResponse(@AuthenticationPrincipal UserDetailsImpl userDetails, @ModelAttribute StaticsRequestDto request) {

        return ResponseEntity.ok()
                .body(staticService.getListStaticsResponse(request, userDetails.getUser()));
    }

}
