package com.javashop.javashop.graphql;

import antlr.Token;
import com.fasterxml.jackson.core.JsonToken;
import com.javashop.javashop.model.SigninPayload;
import com.javashop.javashop.model.User;
import com.javashop.javashop.repository.UserRepository;
import graphql.schema.DataFetcher;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class LoginDataFetcher {
    @Autowired
    private UserRepository userReposiotry;

    public DataFetcher getLoginDataFetcher() {
        return  dataFetchingEnvironment -> {
            String login = dataFetchingEnvironment.getArgument("login");
            String password = dataFetchingEnvironment.getArgument("password");
            User user = userReposiotry.findByLogin(login);
            if(user==null){
                return new SigninPayload("Wrong login!");
            }
            String jwt = null;
            if(BCrypt.checkpw(password, user.getPassword())){
                jwt = Jwts.builder().setExpiration(new Date())
                                .claim("id", user.getId())
                                .claim("name", user.getName())
                                .claim("surname", user.getSurname())
                                .claim("email", user.getEmail())
                                .claim("role", user.getRole().getName())
                                .signWith(SignatureAlgorithm.HS256,"shop".getBytes("UTF-8"))
                                .compact();
            }
            else{
                jwt = "Password is inncorect!";
            }

            SigninPayload signinPayload = new SigninPayload(jwt);
            return signinPayload;
        };
    }
}
