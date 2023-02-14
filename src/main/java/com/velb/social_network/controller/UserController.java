package com.velb.social_network.controller;

import com.velb.social_network.model.entity.User;
import com.velb.social_network.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("${http.url.version}")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("authentication.principal.id == #userId")
    public User getSelfUserInfo(@PathVariable UUID userId) {
        return userService.getUserInfo(userId);
    }

    @GetMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public User getAnotherUserInfo(@PathVariable UUID userId) {
        return userService.getUserInfo(userId);
    }

}
