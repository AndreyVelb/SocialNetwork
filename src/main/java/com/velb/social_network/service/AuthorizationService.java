package com.velb.social_network.service;

import com.velb.social_network.exception.ExceptionMessage;
import com.velb.social_network.model.auxiliary.Role;
import com.velb.social_network.model.security.CustomUserDetails;
import com.velb.social_network.repository.AuthorizationUserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorizationService implements UserDetailsService {

    private final AuthorizationUserDetailsRepository authorizationUserDetailsRepository;


    @Override
    public CustomUserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        return authorizationUserDetailsRepository.findByMail(mail)
                .map(userDetails -> new CustomUserDetails(
                        userDetails.getMail(),
                        userDetails.getEncryptedPassword(),
                        List.of(Role.values()),
                        userDetails.getId(),
                        userDetails.getLastName(),
                        userDetails.getFirstName()))
                .orElseThrow(() -> new UsernameNotFoundException(new ExceptionMessage().USER_NAME_NOT_FOUND_MESSAGE));
    }

}
