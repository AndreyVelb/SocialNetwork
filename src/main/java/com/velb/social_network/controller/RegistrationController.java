package com.velb.social_network.controller;

import com.velb.social_network.model.dto.user.UserRegistrationDto;
import com.velb.social_network.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @SneakyThrows
    public ResponseEntity<Void> register(
            @RequestPart("registrationData") UserRegistrationDto dto, @RequestPart("photo") MultipartFile photo) {
        String userName = userService.create(dto, photo);
        URI location = URI.create("/api/v1/consumers/" + userName);
        return ResponseEntity.created(location).build();
    }

}
