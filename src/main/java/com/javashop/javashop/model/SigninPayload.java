package com.javashop.javashop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Getter
@Setter
@NoArgsConstructor
public class SigninPayload {
    private String token;


    public SigninPayload(String token) {
        this.token = token;
    }
}