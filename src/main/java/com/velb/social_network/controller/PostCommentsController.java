package com.velb.social_network.controller;

import com.velb.social_network.model.dto.comment.CommentCreatingDto;
import com.velb.social_network.model.entity.Comment;
import com.velb.social_network.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class PostCommentsController {

    private final CommentService commentService;


    @GetMapping(value = "/users/{userId}/posts/{postId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Comment> getPostComments(@PathVariable UUID postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping(value = "/{userId}/posts/{postId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("authentication.principal.id == #userId")
    public List<Comment> getSelfPostComments(@PathVariable UUID postId, @PathVariable UUID userId) {
        return commentService.getCommentsByPostId(postId);
    }

    @PostMapping(value = "/users/{userId}/posts/{postId}/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(@PathVariable UUID userId, @PathVariable UUID postId, @RequestBody CommentCreatingDto commentCreatingDto) {
        UUID commentId = commentService.create(postId, userId, commentCreatingDto);
        URI location = URI.create("/api/v1/" + userId + "/posts/" + postId + "/comments/" + commentId);
        return ResponseEntity.created(location).build();
    }

    @PostMapping(value = "/{userId}/posts/{postId}/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("authentication.principal.id == #userId")
    public ResponseEntity<Void> createSelfPostComment(@PathVariable UUID userId, @PathVariable UUID postId, @RequestBody CommentCreatingDto commentCreatingDto) {
        UUID commentId = commentService.create(postId, userId, commentCreatingDto);
        URI location = URI.create("/api/v1/" + userId + "/posts/" + postId + "/comments/" + commentId);
        return ResponseEntity.created(location).build();
    }

}
