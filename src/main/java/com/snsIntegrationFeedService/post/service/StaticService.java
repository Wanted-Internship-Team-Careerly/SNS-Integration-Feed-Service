package com.snsIntegrationFeedService.post.service;

import com.snsIntegrationFeedService.hashtag.service.HashtagService;
import com.snsIntegrationFeedService.post.CountType;
import com.snsIntegrationFeedService.post.DateType;
import com.snsIntegrationFeedService.post.dto.request.StaticsRequest;
import com.snsIntegrationFeedService.post.dto.response.StaticsResponse;
import com.snsIntegrationFeedService.post.repository.PostRepository;
import com.snsIntegrationFeedService.postHashtag.service.PostHashtagService;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StaticService {

    private final PostRepository postRepository;
    private final HashtagService hashtagService;
    private final PostHashtagService postHashtagService;

//    public List<StaticsResponse> getListStaticsResponse(StaticsRequest request) {
//        String value = String.valueOf(request.getValue());
//        String type = String.valueOf(request.getType());
//        Date startDate = this.checkStartDate(request.getStart());
//        Date endDate = this.checkEndDate(request.getEnd());
//        String hashtag = request.getHashtag();
//
//    }

    public StaticsResponse getDateStaticsResponse(String value, Date date, String hashtag) {
        List<Long> postIds = postHashtagService.getPostIdsByHashtag(hashtag);
        long count = postRepository.findCountByValueAndDate(value, date);

        return StaticsResponse.builder()
                .date(date)
                .num(count)
                .build();
    }

//    public StaticsResponse getHourStaticsResponse() {
//
//    }

    private Date checkStartDate(Date startDate) {

        if (startDate == null) {
            // 만약 startDate가 입력되지 않은 경우, 오늘로부터 7일 전 날짜를 기본값으로 설정
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date()); // 현재 날짜 설정
            calendar.add(Calendar.DAY_OF_MONTH, -7); // 7일 전 날짜로 설정
            startDate = calendar.getTime();

            return startDate;
        } else {
            return startDate;
        }
    }

    private Date checkEndDate(Date endDate) {
        if (endDate == null) {
            // 만약 startDate가 입력되지 않은 경우, 오늘로부터 7일 전 날짜를 기본값으로 설정
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date()); // 현재 날짜 설정
            endDate = calendar.getTime();

            return endDate;
        } else {
            return endDate;
        }
    }
}
