package com.bloggingApp.bloggingApp.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthorizationResponse {
    private String token;
}
