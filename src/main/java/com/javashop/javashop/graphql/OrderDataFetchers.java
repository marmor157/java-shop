package com.javashop.javashop.graphql;

import com.javashop.javashop.model.DeliveryAddress;
import com.javashop.javashop.model.Order;
import com.javashop.javashop.model.User;
import com.javashop.javashop.repository.DeliveryAddressRepository;
import com.javashop.javashop.repository.OrderRepository;
import com.javashop.javashop.repository.ShipmentMethodRepository;
import com.javashop.javashop.repository.UserRepository;
import com.sun.xml.bind.v2.util.QNameMap;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

@Component
public class OrderDataFetchers {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;
    @Autowired
    private ShipmentMethodRepository shipmentMethodRepository;
    @Autowired
    private UserRepository userRepository;

    public DataFetcher getOrderDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  orderRepository.findById(id);
        };
    }

    public DataFetcher createOrderDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer price = (Integer) l.get("price");
            String dateStr = (String) l.get("date");
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
            Integer deliveryAddressID = Integer.parseInt((String) l.get("deliveryAddressID"));
            Integer shipmentMethodID = Integer.parseInt((String) l.get("shipmentMethodID"));
            Integer userID = Integer.parseInt((String) l.get("userID"));
            Order order = new Order(date, price);
            order.setDeliveryAddress(deliveryAddressRepository.getOne(deliveryAddressID));
            order.setShipmentMethod(shipmentMethodRepository.getOne(shipmentMethodID));
            order.setUser(userRepository.getOne(userID));
            return orderRepository.save(order);
        };
    }

    public DataFetcher updateOrderDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer id =Integer.parseInt((String) l.get("id"));
            Order order = orderRepository.getOne(id);
            if(l.containsKey("price")){
                Integer price = (Integer) l.get("price");
                order.setPrice(price);
            }
            if(l.containsKey("date")){
                String dateStr = (String) l.get("date");
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
                order.setDate(date);
            }
            if(l.containsKey("deliveryAddressID")){
                Integer deliveryAddressID = Integer.parseInt((String) l.get("deliveryAddressID"));
                order.setDeliveryAddress(deliveryAddressRepository.getOne(deliveryAddressID));
            }
            if(l.containsKey("shipmentMethodID")){
                Integer shipmentMethodID = Integer.parseInt((String) l.get("shipmentMethodID"));
                order.setShipmentMethod(shipmentMethodRepository.getOne(shipmentMethodID));
            }
            if(l.containsKey("userID")){
                Integer userID = Integer.parseInt((String) l.get("userID"));
                order.setUser(userRepository.getOne(userID));
            }

            return orderRepository.save(order);
        };
    }

    public DataFetcher deleteOrderDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id =Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            Order order = orderRepository.getOne(id);
            orderRepository.delete(order);

            return order;
        };
    }

}
