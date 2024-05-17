package com.example.bookapprestapi.services;

import com.example.bookapprestapi.RequestDTO.JwtRequestDTO;
import com.example.bookapprestapi.RequestDTO.RegisterReaderDTO;
import com.example.bookapprestapi.ResponseDTO.JwtResponseDTO;
import com.example.bookapprestapi.models.Reader;
import com.example.bookapprestapi.util.exceptions.AppErrorResponse;
import com.example.bookapprestapi.util.jwt.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final ReaderService readerService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    public ResponseEntity<?> createAuthToken(JwtRequestDTO authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppErrorResponse("Некорректный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = readerService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponseDTO(token));
    }

    public ResponseEntity<?> registerReader(RegisterReaderDTO registerReaderDTO) {
        if (!registerReaderDTO.getPassword().equals(registerReaderDTO.getConfirmPassword())) {
            return new ResponseEntity<>(new AppErrorResponse("Пароли не совпадают"), HttpStatus.BAD_REQUEST);
        }
        if (readerService.findByUsername(registerReaderDTO.getUsername()).isPresent()) {
            return new ResponseEntity<>(new AppErrorResponse("Пользователь с указанным именем уже существует"), HttpStatus.BAD_REQUEST);
        }
        readerService.addUser(convertDTOtoModel(registerReaderDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public Reader convertDTOtoModel(RegisterReaderDTO registerReaderDTO) {
        return modelMapper.map(registerReaderDTO, Reader.class);
    }
}
