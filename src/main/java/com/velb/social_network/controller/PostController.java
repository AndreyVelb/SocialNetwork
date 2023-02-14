package com.velb.social_network.controller;

import com.velb.social_network.model.dto.post.PostAbbreviatedResponseDto;
import com.velb.social_network.model.dto.post.PostCreatingDto;
import com.velb.social_network.model.entity.Post;
import com.velb.social_network.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${http.url.version}")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @GetMapping(value = "/{userId}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("authentication.principal.id == #userId")
    public List<PostAbbreviatedResponseDto> getSelfUserPosts(@PathVariable UUID userId) {
        return postService.getPosts(userId);
    }

    @GetMapping(value = "/{userId}/posts/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("authentication.principal.id == #userId")
    public Post getSelfUserPost(@PathVariable UUID userId, @PathVariable UUID postId) {
        return postService.get(userId, postId);
    }

    @PostMapping(value = "/{userId}/posts", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("authentication.principal.id == #userId")
    public ResponseEntity<Void> create(@PathVariable UUID userId, @RequestBody PostCreatingDto postCreatingDto) {
        UUID postId = postService.create(userId, postCreatingDto);
        URI location = URI.create("/api/v1/" + userId + "/posts/" + postId);
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/{userId}/posts/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("authentication.principal.id == #userId")
    public void deletePost(@PathVariable UUID userId, @PathVariable UUID postId) {
        postService.delete(userId, postId);
    }

    @GetMapping(value = "/users/{userId}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<PostAbbreviatedResponseDto> getPosts(@PathVariable UUID userId) {
        return postService.getPosts(userId);
    }

    @GetMapping(value = "/users/{userId}/posts/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Post getPost(@PathVariable UUID userId, @PathVariable UUID postId) {
        return postService.get(userId, postId);
    }

}
