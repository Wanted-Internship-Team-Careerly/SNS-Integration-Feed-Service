package com.snsIntegrationFeedService.post.dto;

import com.snsIntegrationFeedService.post.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PostDetailResponseDto {

  private String postId;
  private String type;
  private String title;
  private String content;
  private List<String> hashtag;
  private long viewCount;
  private long likeCount;
  private long shareCount;
  private LocalDateTime modifiedAt;
  private LocalDateTime createdAt;

  public static PostDetailResponseDto from(Post post, List<String> postHashtags) {
    return PostDetailResponseDto.builder()
        .postId(post.getPostId())
        .type(post.getType().name())
        .title(post.getTitle())
        .content(post.getContent())
        .hashtag(postHashtags)
        .viewCount(post.getViewCount())
        .likeCount(post.getLikeCount())
        .shareCount(post.getShareCount())
        .modifiedAt(post.getModifiedAt())
        .createdAt(post.getModifiedAt())
        .build();
  }
}
