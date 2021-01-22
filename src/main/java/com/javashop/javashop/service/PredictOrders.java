package com.javashop.javashop.service;

import com.javashop.javashop.model.Order;
import com.javashop.javashop.model.Product;
import com.javashop.javashop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Component
public class PredictOrders {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MailService mailService;

    @Scheduled(cron = "0 0 7 * * *")
    @Scheduled(cron = "0 35 11 * * *")
    public void sendPredictOrder() {
        Date nowDate = new Date();
        String title = "Prognoza potrzeb zamówień" + nowDate;
        String text = "Prognoza potrzeb zamówień z dnia "+ nowDate + ":\n";
        List<Order> orders = orderRepository.findAll();
        Set<Product> products = new HashSet<>();
        for(Order o : orders){
            if(o.getDate().isAfter(LocalDate.now().minusDays(1))){
                for(Product p : o.getProducts()){
                    products.add(p);
                }
            }
        }
        text += "Produkty: " + Arrays.toString(products.toArray()) + "\n";
        text += "Laczna liczba zamowionych produktow: " + products.stream().count();
        System.out.println(text);
        try {
            mailService.sendMail("szefSklepu123456543219@gmail.com", title, text, false);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
