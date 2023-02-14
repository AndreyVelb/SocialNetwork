package com.velb.social_network.repository;

import com.velb.social_network.model.entity.User;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CassandraRepository<User, UUID> {

    @Query("SELECT * FROM users WHERE id = :id")
    Optional<User> findByUserId(@Param("id") UUID id);

}
