package com.snsIntegrationFeedService.post.dto.response;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
//@NoArgsConstructor
@Getter
@Builder
public class StaticsResponseDto {

    private Date date;

    private long num;

    public static StaticsResponseDto from(Date date, int num) {
        return StaticsResponseDto.builder()
                .date(date)
                .num(num)
                .build();
    }

}
