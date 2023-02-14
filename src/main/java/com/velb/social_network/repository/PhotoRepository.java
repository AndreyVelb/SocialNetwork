package com.velb.social_network.repository;

import com.velb.social_network.model.entity.Photo;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PhotoRepository extends CassandraRepository<Photo, UUID> {

    @Query("SELECT * FROM photos WHERE user_id = :userId AND photo_id = :photoId")
    Optional<Photo> findByUserIdAndPhotoId(@Param("userId") UUID userId, @Param("photoId") UUID photoId);

    @Query("DELETE FROM photos WHERE user_id = :userId AND photo_id = :photoId")
    void deleteByUserIdAndPhotoId(@Param("userId") UUID userId, @Param("photoId") UUID photoId);

    @Query("SELECT * FROM photos WHERE user_id = :userId")
    List<Photo> findAllPhotoByUserId(@Param("userId") UUID userId);
}
