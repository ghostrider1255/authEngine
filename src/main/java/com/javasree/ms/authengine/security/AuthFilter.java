package com.javasree.ms.authengine.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javasree.ms.authengine.model.dto.UserDto;
import com.javasree.ms.authengine.model.vo.RequestLogin;
import com.javasree.ms.authengine.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.*;


import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

public class AuthFilter extends UsernamePasswordAuthenticationFilter {
    private UserService userService;
    private Environment environment;

    public AuthFilter(UserService userService ,Environment environment){
        this.userService = userService;
        this.environment = environment;
    }

    public AuthFilter(UserService userService , Environment environment, AuthenticationManager authenticationManager ){
        this(userService,environment);
        super.setAuthenticationManager(authenticationManager);
    }

    //@throws(AuthenticationException.class)
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response ) throws AuthenticationException {
        try{
            RequestLogin requestLoginModel = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken
                            (requestLoginModel.getEmail(),
                                    requestLoginModel.getPassword(),
                                    new java.util.ArrayList())
            );
        }catch (IOException ioException){
            System.out.println(ioException.getMessage());
            throw new RuntimeException(ioException);
        }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request,
                                         HttpServletResponse response,
                                         FilterChain chain,
                                         Authentication authResult) {
        //super.successfulAuthentication(request, response, chain, authResult)

        String userName = ((User)authResult.getPrincipal()).getUsername();
        UserDto userDto  = userService.getUserDetailsByEmail(userName);
        UserDetails userDetails = userService.loadUserByUsername(userName);

        String authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        String token = Jwts.builder()
                .setSubject(userDto.getUserId())
                .setExpiration( new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("jwt.token.expiration"))))
                .claim("roles",authorities)
                .signWith(SignatureAlgorithm.HS512, environment.getProperty("jwt.token.secret"))
                .compact();

        response.addHeader("token",token);
        response.addHeader("userId",userDto.getUserId());
    }
}
