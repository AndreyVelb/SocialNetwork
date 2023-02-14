package com.velb.social_network.repository;

import com.velb.social_network.model.dto.post.PostAbbreviatedResponseDto;
import com.velb.social_network.model.entity.Post;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends CassandraRepository<Post, UUID> {

    //TODO проверить правильность посредствам запросу в реальную базу
    @Query("SELECT * FROM posts WHERE author_id = :authorId AND post_id = :postId")
    Optional<Post> findByAuthorIdAndPostId(@Param("authorId") UUID authorId, @Param("postId") UUID postId);

    @Query("DELETE FROM posts WHERE author_id = :authorId AND post_id = :postId")
    void deleteByAuthorIdAndPostId(@Param("authorId") UUID authorId, @Param("postId") UUID postId);

    @Query("SELECT author_id, post_id, content FROM posts WHERE author_id = :authorId")
    List<PostAbbreviatedResponseDto> findAllDtoByAuthorIdAndPostId(@Param("authorId") UUID authorId);

}
