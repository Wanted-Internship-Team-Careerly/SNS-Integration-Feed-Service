package com.snsIntegrationFeedService.post.service;

import com.snsIntegrationFeedService.common.error.CustomErrorCode;
import com.snsIntegrationFeedService.common.exception.CustomException;
import com.snsIntegrationFeedService.post.CountType;
import com.snsIntegrationFeedService.post.dto.request.StaticsRequestDto;
import com.snsIntegrationFeedService.post.dto.response.StaticsResponseDto;
import com.snsIntegrationFeedService.post.repository.PostRepository;
import com.snsIntegrationFeedService.user.entity.User;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class StaticService {

    private final PostRepository postRepository;

    // value 가 count, type 이 date인 경우를 가정
    public List<StaticsResponseDto> getListStaticsResponse(StaticsRequestDto request, User user) {
        List<StaticsResponseDto> staticsResponses = new ArrayList<>();

        // todo
        // start가 end보다 더 뒤일 경우 예외처리
        checkStartAndEndDate(request);

        // hashtag 값 검증
        if (request.getHashtag() == null) {
            request.setHashtag(user.getAccount());
        }

        // value 값 검증
        if (request.getValue() == null) {
            request.setValue(CountType.count);
        }

        // Calendar 객체를 사용하여 startDate를 설정합니다.
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(request.getStart());

        while (!calendar.getTime().after(request.getEnd())) {

            // 현재 날짜를 가져옵니다.
            Date currentDate = calendar.getTime();
            int count = postRepository.findByStaticsRequest(request, currentDate);

            StaticsResponseDto staticsResponse = StaticsResponseDto.builder().date(currentDate).num(count)
                    .build();
            staticsResponses.add(staticsResponse);

            // 다음 날짜로 이동
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return staticsResponses;
    }

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

    private void checkStartAndEndDate(StaticsRequestDto request) {
        // todo
        // start가 end보다 더 뒤일 경우 예외처리
        Date startDate = new Date();
        Date endDate = new Date();
        try {
            startDate = this.checkStartDate(request.getStart());
            endDate = this.checkEndDate(request.getEnd());
            if (startDate.after(endDate)) {
                throw new CustomException(CustomErrorCode.NOT_AVAIABLE_DATE);
            }
        } catch (Exception e) {
            log.error(">> startDate와 endDate가 유요하지 않습니다. {}", e.getMessage());
        }

        request.setStart(startDate);
        request.setEnd(endDate);
    }
}
