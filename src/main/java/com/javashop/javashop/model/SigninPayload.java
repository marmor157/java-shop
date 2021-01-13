package com.javashop.javashop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SigninPayload {
    private String token;
    private User user;


    public SigninPayload(String token, User user) {
        this.token = token;
        this.user = user;
    }
}