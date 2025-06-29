package com.jwt.rbac.app.filter;

import com.jwt.rbac.app.security.UserInfoService;
import com.jwt.rbac.app.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    public static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQURNSU4iLCJzdWIiOiJhZG1pbiIsImlhdCI6MTc1MTM1MTA5NywiZXhwIjoxNzUxMzUyODk3fQ.qNba5sRiy1ONCGpsQhQD6UproL5acfZDUWxWoRJjffU";
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private JwtService jwtService;
    private UserInfoService userInfoService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        jwtService = mock(JwtService.class);
        userInfoService = mock(UserInfoService.class);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService, userInfoService);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
        SecurityContextHolder.clearContext();
    }

    @Test
    void testValidToken() throws Exception {

        when(request.getHeader("Authorization")).thenReturn("Bearer " + TOKEN);
        when(jwtService.extractUsername(TOKEN)).thenReturn("user");
        when(jwtService.validateToken(eq(TOKEN), any())).thenReturn(true);
        Claims claims = Jwts.claims();
        claims.put("role", "USER");
        when(jwtService.extractAllClaims(eq(TOKEN))).thenReturn(claims);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //assertNotNull();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        assertTrue(authorities.stream().anyMatch(grantedAuthorities -> grantedAuthorities.getAuthority().equals("USER")));
    }

    @Test
    void testInvalidToken() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer " + TOKEN);
        when(jwtService.extractUsername(TOKEN)).thenReturn("user");
        when(jwtService.validateToken(eq(TOKEN), any())).thenReturn(false);
        when(jwtService.extractUsername(TOKEN)).thenReturn("user");
        when(jwtService.validateToken(eq("invalidToken"), any())).thenReturn(false);
        Claims claims = Jwts.claims();
        claims.put("role", "USER");
        when(jwtService.extractAllClaims(eq(TOKEN))).thenReturn(claims);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testMissingToken() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}