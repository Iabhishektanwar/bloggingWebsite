package com.bloggingApp.bloggingApp.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthorizationRequest {

    private String username;
    private String password;
}
