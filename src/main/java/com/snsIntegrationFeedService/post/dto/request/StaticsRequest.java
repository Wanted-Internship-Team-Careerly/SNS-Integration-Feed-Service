package com.snsIntegrationFeedService.post.dto.request;

import com.snsIntegrationFeedService.post.CountType;
import com.snsIntegrationFeedService.post.DateType;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class StaticsRequest {
    private CountType value;
    private DateType type;
    private String hashtag;
    private Date start;
    private Date end;

    public StaticsRequest(CountType value, DateType type, String hashtag, Date start, Date end) {
        this.value = (value != null) ? value : CountType.count;
        this.type = type;
        this.hashtag = (hashtag != null) ? hashtag : "본인계정";
        this.start = start;
        this.end = end;
    }
}
