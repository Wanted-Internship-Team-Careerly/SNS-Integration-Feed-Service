package com.snsIntegrationFeedService.post.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PostsResponseDto {
	private List<PostDetailResponseDto> data;
	private int pageCount;
	private int page;

	public static PostsResponseDto from(List<PostDetailResponseDto> list, int pageCount, int page) {
		return PostsResponseDto.builder()
			.data(list)
			.pageCount(pageCount)
			.page(page)
			.build();
	}
}
