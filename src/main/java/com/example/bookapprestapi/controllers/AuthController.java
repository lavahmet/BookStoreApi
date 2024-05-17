package com.example.bookapprestapi.controllers;

import com.example.bookapprestapi.RequestDTO.JwtRequestDTO;
import com.example.bookapprestapi.RequestDTO.RegisterReaderDTO;
import com.example.bookapprestapi.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequestDTO authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/reg")
    public ResponseEntity<?> createNewUser(@RequestBody RegisterReaderDTO registerReaderDTO) {
        return authService.registerReader(registerReaderDTO);
    }


}
