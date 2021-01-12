package com.javashop.javashop.service;

import com.javashop.javashop.model.User;
import com.javashop.javashop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.List;

@Component
public class ChristmasWishes {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailService mailService;

    @Scheduled(cron = "0 0 12 24 12 *")
    public void sendChristamsWishes() {
        List<User> users = userRepository.findAll();
        for(User u : users){
            try {
                String text = "Wszystkeog dobrego z okazji Świąt "+ u.getName() +", zapraszamy na zakupy do naszego sklepu!";
                mailService.sendMail(u.getEmail(), "Życzenia świąteczne!", text, false);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
}
