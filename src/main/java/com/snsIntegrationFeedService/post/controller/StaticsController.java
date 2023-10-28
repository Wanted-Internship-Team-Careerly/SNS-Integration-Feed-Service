package com.snsIntegrationFeedService.post.controller;

import com.snsIntegrationFeedService.post.CountType;
import com.snsIntegrationFeedService.post.DateType;
import com.snsIntegrationFeedService.post.dto.request.StaticsRequest;
import com.snsIntegrationFeedService.post.dto.response.StaticsResponse;
import com.snsIntegrationFeedService.post.service.PostService;
import com.snsIntegrationFeedService.post.service.StaticService;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StaticsController {

    private final StaticService staticService;

//    @GetMapping(value = "/api/posts/statics")
//    public ResponseEntity<List<StaticsResponse>> getResponse(
//            @RequestParam(value = "value", required = false) CountType countType,
//            @RequestParam(value = "type") DateType dateType,
//            @RequestParam(value = "hashtag", required = false, defaultValue = "본인계정") String hashtag,
//            @RequestParam(value = "start", required = false)
//            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
//            @RequestParam(value = "start", required = false)
//            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
//
//        return ResponseEntity.ok()
//                .body(staticService.getStaticsResponse(countType, dateType, hashtag, startDate, endDate));
//
//    }

//    @GetMapping(value = "/api/posts/statics")
//    public ResponseEntity<List<StaticsResponse>> getResponse(@ModelAttribute StaticsRequest request) {
//
//        return ResponseEntity.ok()
//                .body(staticService.getListStaticsResponse(request));
//    }

}
