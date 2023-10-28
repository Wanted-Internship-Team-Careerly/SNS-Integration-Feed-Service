package com.snsIntegrationFeedService.post.dto.response;

import com.snsIntegrationFeedService.post.entity.Post;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
//@NoArgsConstructor
@Getter
@Builder
public class StaticsResponse {

    private Date date;

    private long num;

    public static StaticsResponse from(Date date, int num) {
        return StaticsResponse.builder()
                .date(date)
                .num(num)
                .build();
    }

}
