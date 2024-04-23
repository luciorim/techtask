package com.luciorim.techtask.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luciorim.techtask.constants.Role;
import com.luciorim.techtask.constants.TokenType;
import com.luciorim.techtask.dto.request.RequestAuthDto;
import com.luciorim.techtask.dto.request.RequestRegisterUserDto;
import com.luciorim.techtask.dto.response.ResponseAuthDto;
import com.luciorim.techtask.exceptions.DbObjectNotFoundException;
import com.luciorim.techtask.model.Token;
import com.luciorim.techtask.model.User;
import com.luciorim.techtask.repository.TokenRepository;
import com.luciorim.techtask.repository.UserRepository;
import com.luciorim.techtask.security.JwtService;
import com.luciorim.techtask.service.AuthService;
import com.luciorim.techtask.service.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.luciorim.techtask.constants.ValueConstants.ZONE_ID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    @Override
    public void registerUser(RequestRegisterUserDto requestRegisterUserDto) {
        userRepository.findUserByEmail(requestRegisterUserDto.getEmail().trim())
                .ifPresent(usr -> {
                    throw new IllegalArgumentException("User with email "+ requestRegisterUserDto.getEmail() + " already exists");
                });

        if(!requestRegisterUserDto.getPassword().trim().equals(requestRegisterUserDto.getConfirmPassword().trim())){
            throw new IllegalArgumentException("Passwords do not match");
        }

        log.info("Requested profile picture for user {}", requestRegisterUserDto.getProfilePicture());
        User user = new User();
        user.setEmail(requestRegisterUserDto.getEmail());
        user.setFirstName(requestRegisterUserDto.getFirstName());
        user.setLastName(requestRegisterUserDto.getLastName());
        user.setPassword(passwordEncoder.encode(requestRegisterUserDto.getPassword()));
        user.setRole(Role.USER);
        user.setCreatedAt(LocalDateTime.now(ZONE_ID));

        if(requestRegisterUserDto.getProfilePicture() != null){
            MultipartFile profilePicture = requestRegisterUserDto.getProfilePicture();
            String filename = imageService.upload(profilePicture);
            user.setImageUrl(filename);
            log.info("Profile picture: {}", filename);
        }

        log.info("Registered user: {}", user);
        userRepository.save(user);
    }

    @Override
    public ResponseAuthDto authenticateUser(RequestAuthDto authRequest) {
        User user = userRepository.findUserByEmail(authRequest.getEmail())
                .orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "User doesn't exist"));

        if(!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("Passwords do not match");
        }

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        return ResponseAuthDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }


    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }


    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException{

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Token is empty");
        }

        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail != null) {

            var user = userRepository.findUserByEmail(userEmail)
                    .orElseThrow(() -> new DbObjectNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Token is invalid"));

            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = ResponseAuthDto.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);

            } else {
                throw new IllegalArgumentException("Token is invalid");
            }
        } else
            throw new IllegalArgumentException("Token is empty");

    }


}
