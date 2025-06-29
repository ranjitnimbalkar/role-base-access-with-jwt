package com.jwt.rbac.app.controller;

import com.jwt.rbac.app.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final JwtService jwtService;

    @GetMapping("/login")
    public String login(@RequestHeader("user") String user) throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
        return jwtService.generateToken(user);
    }

}
