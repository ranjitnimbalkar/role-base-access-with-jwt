package com.jwt.rbac.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

@RestController
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/test/authority")
    @PreAuthorize("hasAuthority('USER')")
    public String hasAuthority() throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {      ;
        return "You got the access USER AUTHORITY!!";
    }

    @GetMapping("/test/role")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String hasRole() throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {      ;
        return "You got the access as USER ROLE!!";
    }

    @GetMapping("/test/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String hasRoleAdmin() throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {      ;
        return "You got the access as ADMIN ROLE!!";
    }

}
