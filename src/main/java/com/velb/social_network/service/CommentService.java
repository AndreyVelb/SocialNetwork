package com.velb.social_network.service;

import com.velb.social_network.model.dto.comment.CommentCreatingDto;
import com.velb.social_network.model.entity.Comment;
import com.velb.social_network.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;


    public List<Comment> getCommentsByPostId(UUID postId) {
        return commentRepository.getAllByPostId(postId);
    }

    public UUID create(UUID postId, UUID userId, CommentCreatingDto creatingDto) {
        return commentRepository.insert(
                Comment.builder()
                        .postId(postId)
                        .authorId(userId)
                        .commentId(UUID.randomUUID())
                        .content(creatingDto.getContent())
                        .authorFullName(creatingDto.getAuthorFullName())
                        .creatingTime(LocalTime.now())
                        .creatingDate(LocalDate.now())
                        .build())
                .getCommentId();
    }

}
