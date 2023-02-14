package com.velb.social_network.repository;

import com.velb.social_network.model.entity.AuthorizationUserDetails;
import com.velb.social_network.model.entity.User;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorizationUserDetailsRepository extends CassandraRepository<AuthorizationUserDetails, UUID> {

    @Query("SELECT mail, id, password, role, last_name, first_name FROM authorization_user_details WHERE mail = :mail")
    Optional<AuthorizationUserDetails> findByMail(@Param("mail") String mail);

}
