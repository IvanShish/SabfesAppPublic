package com.heroku.git.sabfesapp.security.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEventListener {

    @EventListener
    public void authenticationFailed(AuthenticationFailureBadCredentialsEvent event) {

        String username = (String) event.getAuthentication().getPrincipal();

//        System.out.println("Auth failed: " + username);
        // update the failed login count for the user
        // ...
    }
}
