package com.javashop.javashop.service;

import com.javashop.javashop.model.Order;
import com.javashop.javashop.model.User;
import com.javashop.javashop.repository.OrderRepository;
import com.javashop.javashop.repository.UserRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Component
public class Raport {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MailService mailService;

    @Scheduled(cron = "0 0 9 1 * *")
    public void sendRaport() {
        Integer orderCount = 0;
        Integer priceSum = 0;
        Date nowDate = new Date();
        String title = "Raport z dnia " + nowDate;
        String text = "Raport miesięczny z dnia "+ nowDate + " za miesiąc " + LocalDate.now().minusMonths(1).getMonth() +":\n";
        List<Order> orders = orderRepository.findAll();
        for(Order o : orders){
            if(o.getDate().isAfter(LocalDate.now().minusMonths(1)) && o.getDate().isBefore(LocalDate.now())){
                orderCount++;
                text += "Zamowienie " + orderCount + " na kwotę:" + o.getPrice() + "\n";
                priceSum += o.getPrice();
            }
        }
        text += "Dokonano " + orderCount;
        text += "Przychod: " + priceSum;
        try {
            mailService.sendMail("szefSklepu123456543219@gmail.com", title, text, false);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
