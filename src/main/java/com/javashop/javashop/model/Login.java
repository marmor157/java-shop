package com.javashop.javashop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Getter
@Setter
@NoArgsConstructor
public class Login {
    private String login;
    private String password;


    public Login(String login, String password) {
        this.login = login;
        this.password = password;
    }
}