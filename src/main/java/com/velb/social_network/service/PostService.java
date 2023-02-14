package com.velb.social_network.service;

import com.velb.social_network.exception.ExceptionMessage;
import com.velb.social_network.exception.PostNotFoundException;
import com.velb.social_network.model.dto.post.PostAbbreviatedResponseDto;
import com.velb.social_network.model.dto.post.PostCreatingDto;
import com.velb.social_network.model.entity.Post;
import com.velb.social_network.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;


    public UUID create(UUID userId, PostCreatingDto creatingDto) {
        return postRepository.insert(
                        Post.builder()
                                .postId(UUID.randomUUID())
                                .userId(userId)
                                .content(creatingDto.getContent())
                                .creatingTime(LocalTime.now())
                                .creatingDate(LocalDate.now())
                                .build())
                .getPostId();
    }

    public List<PostAbbreviatedResponseDto> getPosts(UUID userId) {
        return postRepository.findAllDtoByAuthorIdAndPostId(userId);
    }

    public Post get(UUID userId, UUID postId) {
        return postRepository.findByAuthorIdAndPostId(userId, postId).orElseThrow(()
                -> new PostNotFoundException(new ExceptionMessage().POST_NOT_FOUND_MESSAGE));
    }

    public void delete(UUID userId, UUID postId) {
        postRepository.deleteByAuthorIdAndPostId(userId, postId);
    }
}
