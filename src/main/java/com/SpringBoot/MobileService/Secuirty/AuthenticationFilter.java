package com.SpringBoot.MobileService.Secuirty;

import com.SpringBoot.MobileService.model.UserLoginRequestModel;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserLoginRequestModel creds= new ObjectMapper().readValue(request.getInputStream(),UserLoginRequestModel.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getEmail(),creds.getPassword(),new ArrayList<>()));

        }
         catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
     String userName = ((User) authResult.getPrincipal()).getUsername();

        try {
            String token = Jwts.builder()
                    .setSubject(userName)
                    .setExpiration(new Date(System.currentTimeMillis()+ SecurityConstance.EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS512,SecurityConstance.TOKEN_SECRETE)
                    .compact();
            response.addHeader(SecurityConstance.HEADER_STRING,SecurityConstance.TOKEN_PREFIX);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
