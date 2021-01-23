package com.javashop.javashop.graphql;

import com.javashop.javashop.model.Metadata;
import com.javashop.javashop.model.Order;
import com.javashop.javashop.repository.*;
import graphql.schema.DataFetcher;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private OrderOpinionRepository orderOpinionRepository;
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

    public DataFetcher getAllOrdersDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer page = dataFetchingEnvironment.getArgument("page");
            page = page == null ? 0: page;
            Integer perPage = dataFetchingEnvironment.getArgument("perPage");
            perPage = perPage == null ? Integer.MAX_VALUE: perPage;
            String sortField = dataFetchingEnvironment.getArgument("sortField");
            String sortOrder = dataFetchingEnvironment.getArgument("sortOrder");
            LinkedHashMap<String, Object> filter = dataFetchingEnvironment.getArgument("filter");

            Sort.Direction order = Sort.Direction.DESC;;
            if(sortOrder!=null && sortOrder.toUpperCase().equals("DESC")){
                order = Sort.Direction.DESC;
            }
            else{
                order = Sort.Direction.ASC;
            }

            if(sortField==null) sortField = "";
            if(sortField!=null && sortField.equals("")){
                sortField = "id";
            }
            if(filter!=null){
                List<Integer> idsInt = null;
                Integer productID = null;
                Integer deliveryAddressID = null;
                Integer shipmentMethodID = null;
                Integer userID = null;

                if(filter.containsKey("ids")){
                    idsInt = new ArrayList<>();
                    final List<String> ids = (List<String>) filter.get("ids");
                    for(String s : ids) idsInt.add(Integer.valueOf(s));
                }
                if(filter.containsKey("deliveryAddressID")){
                    deliveryAddressID = Integer.parseInt((String) filter.get("deliveryAddressID"));
                }
                if(filter.containsKey("shipmentMethodID")){
                    shipmentMethodID = Integer.parseInt((String) filter.get("shipmentMethodID"));
                }
                if(filter.containsKey("productID")){
                    productID = Integer.parseInt((String) filter.get("productID"));
                }
                if(filter.containsKey("userID")){
                    userID = Integer.parseInt((String) filter.get("userID"));
                }
                return orderRepository.findByDeliveryAddressIdAndShipmentMethodIdAndUserIdAndIdIn(deliveryAddressID,shipmentMethodID, userID,
                                                                                                    idsInt,PageRequest.of(page,perPage, Sort.by(order,sortField)));
            }
            Page<Order> orderPage = orderRepository.findAll(PageRequest.of(page,perPage, Sort.by(order,sortField)));
            return orderPage;
        };
    }

    public DataFetcher getAllOrdersMetaDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> filter = dataFetchingEnvironment.getArgument("filter");

            if(filter!=null){
                if(filter.containsKey("ids")){
                    final List<String> ids = (List<String>) filter.get("ids");
                    List<Integer> idsInt = new ArrayList<>();
                    for(String s : ids) idsInt.add(Integer.valueOf(s));
                    return new Metadata(orderRepository.countByIdIn(idsInt));
                }
            }
            Metadata metadata = new Metadata(orderRepository.count());
            return metadata;
        };
    }

    public DataFetcher createOrderDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer deliveryAddressID = Integer.parseInt((String) l.get("deliveryAddressID"));
            Integer shipmentMethodID = Integer.parseInt((String) l.get("shipmentMethodID"));
            Integer userID = Integer.parseInt((String) l.get("userID"));
            List<String> productIDs = (List<String>) l.get("productIDs");
            String status = "zgłoszone";
            if(l.containsKey("status")){
                status = (String) l.get("status");
            }

            Order order = new Order(LocalDate.now(), 0, status);
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
            if(l.containsKey("date")){
                String dateStr = (String) l.get("date");
                LocalDate date = LocalDate.parse(dateStr);
                order.setDate(date);
            }
            if(l.containsKey("status")){
                String status = (String) l.get("status");
                order.setStatus(status);
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
            order.setDeleteDate(LocalDate.now());

            return orderRepository.save(order);
        };
    }

}
