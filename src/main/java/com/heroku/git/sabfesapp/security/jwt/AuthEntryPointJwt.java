package com.heroku.git.sabfesapp.security.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // This method will be triggered anytime unauthenticated User requests a secured HTTP resource and an AuthenticationException is thrown.
        logger.error("Unauthorized error: {}", authException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
//        response.resetBuffer();
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.setHeader("Content-Type", "application/json");
//        response.getOutputStream().print("{\"errorMessage\":\"" + authException.getMessage() + "\"}");
//        response.flushBuffer();
    }

}
