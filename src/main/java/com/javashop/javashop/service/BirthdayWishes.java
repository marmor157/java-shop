package com.javashop.javashop.service;

import com.javashop.javashop.model.User;
import com.javashop.javashop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class BirthdayWishes {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailService mailService;

    @Scheduled(cron = "0 0 12 * * *")
    public void sendBirthdayWishes() {
        List<User> users = userRepository.findAll();
        for(User u : users){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date nowDate = new Date();
            if(simpleDateFormat.format(u.getBirthDate()).equals(simpleDateFormat.format(nowDate))){
                try {
                    String text = "Wszystkeog dobrego z okazji twoich urodzin "+ u.getName() +"\n, zapraszamy na zakupy do naszego sklepu!";
                    mailService.sendMail(u.getEmail(), "Życzenia urodzinowe", text, false);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
