package com.velb.social_network.repository;

import com.velb.social_network.model.entity.Comment;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends CassandraRepository<Comment, UUID> {

    @Query("SELECT * FROM comments WHERE post_id = :postId")
    List<Comment> getAllByPostId(@Param("postId") UUID postId);

}
