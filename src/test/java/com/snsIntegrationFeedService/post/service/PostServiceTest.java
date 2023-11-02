//package com.snsIntegrationFeedService.post.service;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import com.snsIntegrationFeedService.hashtag.repository.HashtagRepository;
//import com.snsIntegrationFeedService.hashtag.service.HashtagService;
//import com.snsIntegrationFeedService.post.dto.request.CreatePostRequest;
//import com.snsIntegrationFeedService.post.entity.Post;
//import com.snsIntegrationFeedService.post.entity.PostTypeEnum;
//import com.snsIntegrationFeedService.post.repository.PostRepository;
//import com.snsIntegrationFeedService.postHashtag.repository.PostHashtagRepository;
//import com.snsIntegrationFeedService.postHashtag.service.PostHashtagService;
//import com.snsIntegrationFeedService.user.entity.User;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//@ExtendWith(MockitoExtension.class)
//class PostServiceTest {
//    @InjectMocks
//    private PostService postService;
//
//    @Mock
//    private PostRepository postRepository;
//
//    @Mock
//    private HashtagService hashtagService;
//
//    @Mock
//    private HashtagRepository hashtagRepository;
//
//    @Mock
//    private PostHashtagService postHashtagService;
//
//    @Mock
//    private PostHashtagRepository postHashtagRepository;
//
//    User user;
//
//    @BeforeEach
//    void init() {
//        user = User.builder()
//                .account("account")
//                .email("careerly@wanted.com")
//                .password("password")
//                .build();
//    }
//
//
//    @Test
//    void createPost() {
//        HashtagService hashtagService = new HashtagService(hashtagRepository);
//        PostHashtagService postHashtagService = new PostHashtagService(postHashtagRepository, hashtagService);
//        PostService postService = new PostService(postRepository, hashtagService, postHashtagService);
//
//
//        // given
//        String title = "title";
//        String content = "content";
//        PostTypeEnum postTypeEnum = PostTypeEnum.FACEBOOK;
//        String hashtag = "wanted";
//        CreatePostRequest request = new CreatePostRequest(postTypeEnum, title, content, hashtag);
//
//        //when
//        Post saved = postService.createPost(user, request);
//
//        //then
//        assertEquals(saved.getTitle(), title);
//    }
//
//
//
//    @Test
//    void createRandomPost() {
//    }
//}