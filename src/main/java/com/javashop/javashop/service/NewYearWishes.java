package com.javashop.javashop.service;

import com.javashop.javashop.model.User;
import com.javashop.javashop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.List;

@Component
public class NewYearWishes {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailService mailService;

    @Scheduled(cron = "0 0 0 1 1 *")
    public void sendBirthdayWishes() {
        List<User> users = userRepository.findAll();
        for(User u : users){
            try {
                String text = "Wszystkeog dobrego z okazji Nowego Roku "+u.getName()+", zapraszamy na zakupy do naszego sklepu!";
                mailService.sendMail(u.getEmail(), "Życzenia noworoczne", text, false);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
}
