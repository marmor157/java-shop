package com.javashop.javashop.graphql;

import com.javashop.javashop.model.Order;
import com.javashop.javashop.model.User;
import com.javashop.javashop.repository.*;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

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
    @Autowired
    private ProductRepository productRepository;

    public DataFetcher getOrderDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  orderRepository.findById(id);
        };
    }

    public DataFetcher getAllOrderDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer page = dataFetchingEnvironment.getArgument("page");
            Integer perPage = dataFetchingEnvironment.getArgument("perPage");
            String sortField = dataFetchingEnvironment.getArgument("sortField");
            String sortOrder = dataFetchingEnvironment.getArgument("sortOrder");
            LinkedHashMap<String, Object> filter = dataFetchingEnvironment.getArgument("filter");

            Sort.Direction order = null;
            if(sortOrder.toUpperCase().equals("DESC")){
                order = Sort.Direction.DESC;
            }
            else{
                order = Sort.Direction.ASC;
            }

            if(sortField==""){
                sortField = "id";
            }

            Page<Order> orderPage = orderRepository.findAll(PageRequest.of(page,perPage, Sort.by(order,sortField)));
            return orderPage;
        };
    }

    public DataFetcher getAllOrderMetaDataFetcher() {
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
            List<String> productIDs = (List<String>) l.get("productIDs");

            Order order = new Order(date, price);
            order.setDeliveryAddress(deliveryAddressRepository.getOne(deliveryAddressID));
            deliveryAddressRepository.getOne(deliveryAddressID).getOrders().add(order);
            order.setShipmentMethod(shipmentMethodRepository.getOne(shipmentMethodID));
            shipmentMethodRepository.getOne(shipmentMethodID).getOrders().add(order);
            order.setUser(userRepository.getOne(userID));
            userRepository.getOne(userID).getOrders().add(order);
            for (String prodID: productIDs){
                order.getProducts().add(productRepository.getOne(Integer.parseInt(prodID)));
                productRepository.getOne(Integer.parseInt(prodID)).getOrders().add(order);
            }

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
                deliveryAddressRepository.getOne(deliveryAddressID).getOrders().add(order);
            }
            if(l.containsKey("shipmentMethodID")){
                Integer shipmentMethodID = Integer.parseInt((String) l.get("shipmentMethodID"));
                order.setShipmentMethod(shipmentMethodRepository.getOne(shipmentMethodID));
                shipmentMethodRepository.getOne(shipmentMethodID).getOrders().add(order);
            }
            if(l.containsKey("userID")){
                Integer userID = Integer.parseInt((String) l.get("userID"));
                order.setUser(userRepository.getOne(userID));
                userRepository.getOne(userID).getOrders().add(order);
            }
            if(l.containsKey("productIDs")){
                List<String> productIDs = (List<String>) l.get("productIDs");
                for (String prodID: productIDs){
                    order.getProducts().add(productRepository.getOne(Integer.parseInt(prodID)));
                    productRepository.getOne(Integer.parseInt(prodID)).getOrders().add(order);
                }
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
