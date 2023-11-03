package com.snsIntegrationFeedService.post.dto.request;

import com.snsIntegrationFeedService.post.CountType;
import com.snsIntegrationFeedService.post.DateType;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class StaticsRequest {
    private CountType value;
    private DateType type;
    private String hashtag;

    private String userAccount;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date start;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date end;

    public StaticsRequest(CountType value, DateType type, String hashtag, Date start, Date end) {
        this.value = (value != null) ? value : CountType.count;
        this.type = type;
        this.hashtag = hashtag;
        this.start = start;
        this.end = end;
    }
}
