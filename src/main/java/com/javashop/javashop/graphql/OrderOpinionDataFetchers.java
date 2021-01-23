package com.javashop.javashop.graphql;

import com.javashop.javashop.model.Metadata;
import com.javashop.javashop.model.Opinion;
import com.javashop.javashop.model.Order;
import com.javashop.javashop.model.OrderOpinion;
import com.javashop.javashop.repository.*;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class OrderOpinionDataFetchers {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderOpinionRepository orderOpinionRepository;

    public DataFetcher getOrderOpinionDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  orderOpinionRepository.findById(id);
        };
    }

    public DataFetcher getAllOrderOpinionsDataFetcher() {
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
                if(filter.containsKey("ids")){
                    final List<String> ids = (List<String>) filter.get("ids");
                    List<Integer> idsInt = new ArrayList<>();
                    for(String s : ids) idsInt.add(Integer.valueOf(s));
                    return  orderOpinionRepository.findByIdIn(idsInt, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
                else if(filter.containsKey("userID")){
                    Integer userID = Integer.parseInt((String) filter.get("userID"));
                    return  orderOpinionRepository.findByUserId(userID, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
                else if(filter.containsKey("orderID")){
                    Integer orderID = Integer.parseInt((String) filter.get("orderID"));
                    return  orderOpinionRepository.findByOrderId(orderID, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
            }
            Page<OrderOpinion> opinionPage = orderOpinionRepository.findAll(PageRequest.of(page,perPage, Sort.by(order,sortField)));
            return opinionPage;
        };
    }

    public DataFetcher getAllOrderOpinionsMetaDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> filter = dataFetchingEnvironment.getArgument("filter");

            if(filter!=null){
                if(filter.containsKey("ids")){
                    final List<String> ids = (List<String>) filter.get("ids");
                    List<Integer> idsInt = new ArrayList<>();
                    for(String s : ids) idsInt.add(Integer.valueOf(s));
                    return new Metadata(orderOpinionRepository.countByIdIn(idsInt));
                }
            }
            Metadata metadata = new Metadata(orderOpinionRepository.count());
            return metadata;
        };
    }

    public DataFetcher createOrderOpinionDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer markShipment = (Integer) l.get("markShipment");
            Integer markTime = (Integer) l.get("markTime");
            Integer markCustomerService = (Integer) l.get("markCustomerService");
            LocalDate date = LocalDate.now();
            Integer orderID = Integer.parseInt((String) l.get("orderID"));
            Integer userID = Integer.parseInt((String) l.get("userID"));
            OrderOpinion orderOpinion = new OrderOpinion(markShipment, markTime, markCustomerService, date);
            orderOpinion.setUser(userRepository.getOne(userID));
            userRepository.getOne(userID).getOrderOpinions().add(orderOpinion);
            orderOpinion.setOrder(orderRepository.getOne(orderID));
            orderRepository.getOne(orderID).setOrderOpinion(orderOpinion);

            return orderOpinionRepository.save(orderOpinion);
        };
    }

    public DataFetcher updateOrderOpinionDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer id =Integer.parseInt((String) l.get("id"));
            OrderOpinion orderOpinion = orderOpinionRepository.getOne(id);
            if(l.containsKey("markShipment")){
                Integer markShipment = (Integer) l.get("markShipment");
                orderOpinion.setMarkShipment(markShipment);
            }
            if(l.containsKey("markTime")){
                Integer markTime = (Integer) l.get("markTime");
                orderOpinion.setMarkTime(markTime);
            }
            if(l.containsKey("markCustomerService")){
                Integer markCustomerService = (Integer) l.get("markCustomerService");
                orderOpinion.setMarkCustomerService(markCustomerService);
            }
            if(l.containsKey("date")){
                String dateStr = (String) l.get("date");
                LocalDate date = LocalDate.parse(dateStr);
                orderOpinion.setDate(date);
            }
            if(l.containsKey("orderID")){
                Integer orderID = Integer.parseInt((String) l.get("orderID"));
                orderOpinion.setOrder(orderRepository.getOne(orderID));
                orderRepository.getOne(orderID).setOrderOpinion(orderOpinion);
            }
            if(l.containsKey("userID")){
                Integer userID = Integer.parseInt((String) l.get("userID"));
                orderOpinion.setUser(userRepository.getOne(userID));
                userRepository.getOne(userID).getOrderOpinions().add(orderOpinion);
            }

            return orderOpinionRepository.save(orderOpinion);
        };
    }

    public DataFetcher deleteOrderOpinionDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id =Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            OrderOpinion orderOpinion = orderOpinionRepository.getOne(id);
            orderOpinion.setDeleteDate(LocalDate.now());

            return orderOpinionRepository.save(orderOpinion);
        };
    }

}
