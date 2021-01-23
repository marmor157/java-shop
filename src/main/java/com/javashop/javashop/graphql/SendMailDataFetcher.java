package com.javashop.javashop.graphql;

import com.javashop.javashop.model.SigninPayload;
import com.javashop.javashop.model.User;
import com.javashop.javashop.repository.UserRepository;
import com.javashop.javashop.service.MailService;
import graphql.schema.DataFetcher;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.Date;

@Component
public class SendMailDataFetcher {
    @Autowired
    private MailService mailService;

    public DataFetcher sendMailToUser() {
        return  dataFetchingEnvironment -> {
            String mail = dataFetchingEnvironment.getArgument("mail");
            String title = dataFetchingEnvironment.getArgument("title");
            String text = dataFetchingEnvironment.getArgument("text");

            try {
                mailService.sendMail(mail, title, text, true);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return "Send";
        };
    }
}
