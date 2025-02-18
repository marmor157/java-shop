package com.javashop.javashop.graphql;

import com.javashop.javashop.model.*;
import com.javashop.javashop.repository.*;
import graphql.schema.DataFetcher;
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
public class ComplaintDataFetchers {
    @Autowired
    private ComplaintRepository complaintRepository;
    @Autowired
    private ComplaintTypeRepository complaintTypeRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    public DataFetcher getComplaintDataFetcher() {
        return dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return complaintRepository.findById(id);
        };
    }

    public DataFetcher getAllComplaintsDataFetcher() {
        return dataFetchingEnvironment -> {
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
                Integer complaintTypeID = null;
                Integer orderID = null;
                Integer userID = null;

                if(filter.containsKey("ids")){
                    idsInt = new ArrayList<>();
                    final List<String> ids = (List<String>) filter.get("ids");
                    for(String s : ids) idsInt.add(Integer.valueOf(s));
                }
                if(filter.containsKey("productID")){
                    productID = Integer.parseInt((String) filter.get("productID"));
                }
                if(filter.containsKey("complaintTypeID")){
                    complaintTypeID = Integer.parseInt((String) filter.get("complaintTypeID"));
                }
                if(filter.containsKey("orderID")){
                    orderID = Integer.parseInt((String) filter.get("orderID"));
                }
                if(filter.containsKey("userID")){
                    userID = Integer.parseInt((String) filter.get("userID"));
                }
                return complaintRepository.findByComplaintTypeIdAndOrderIdAndProductIdAndUserIdAndIdIn(complaintTypeID,orderID, productID, userID,
                                                                                        idsInt,PageRequest.of(page,perPage, Sort.by(order,sortField)));

            }
            Page<Complaint> complaintPage = complaintRepository.findAll(PageRequest.of(page,perPage, Sort.by(order,sortField)));
            return complaintPage;
        };
    }

    public DataFetcher getAllComplaintsMetaDataFetcher() {
        return dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> filter = dataFetchingEnvironment.getArgument("filter");

            if(filter!=null){
                if(filter.containsKey("ids")){
                    final List<String> ids = (List<String>) filter.get("ids");
                    List<Integer> idsInt = new ArrayList<>();
                    for(String s : ids) idsInt.add(Integer.valueOf(s));
                    return new Metadata(complaintRepository.countByIdIn(idsInt));
                }
            }
            Metadata metadata = new Metadata(productRepository.count());
            return metadata;
        };
    }

    public DataFetcher createComplaintDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            LocalDate date = LocalDate.now();
            Integer complaintTypeID = Integer.parseInt((String) l.get("complaintTypeID"));
            Integer orderID = Integer.parseInt((String) l.get("orderID"));
            Integer productID = Integer.parseInt((String) l.get("productID"));
            Integer userID = Integer.parseInt((String) l.get("userID"));
            String status = "Zgłoszona";
            if(l.containsKey("status")){
                status = (String) l.get("status");
            }
            Complaint complaint = new Complaint(date, status);
            complaint.setComplaintType(complaintTypeRepository.getOne(complaintTypeID));
            complaintTypeRepository.getOne(complaintTypeID).getComplaints().add(complaint);
            complaint.setOrder(orderRepository.getOne(orderID));
            orderRepository.getOne(orderID).getComplaints().add(complaint);
            complaint.setProduct(productRepository.getOne(productID));
            productRepository.getOne(productID).getComplaints().add(complaint);
            complaint.setUser(userRepository.getOne(userID));
            userRepository.getOne(userID).getComplaints().add(complaint);

            return complaintRepository.save(complaint);
        };
    }

    public DataFetcher updateComplaintDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer id =Integer.parseInt((String) l.get("id"));
            Complaint complaint = complaintRepository.getOne(id);
            if(l.containsKey("date")){
                String dateStr = (String) l.get("date");
                LocalDate date = LocalDate.parse(dateStr);
                complaint.setDate(date);
            }
            if(l.containsKey("status")){
                String status = (String) l.get("status");
                complaint.setStatus(status);
            }
            if(l.containsKey("complaintTypeID")){
                Integer complaintTypeID = Integer.parseInt((String) l.get("complaintTypeID"));
                complaint.setComplaintType(complaintTypeRepository.getOne(complaintTypeID));
                complaintTypeRepository.getOne(complaintTypeID).getComplaints().add(complaint);
            }
            if(l.containsKey("orderID")){
                Integer orderID = Integer.parseInt((String) l.get("orderID"));
                complaint.setOrder(orderRepository.getOne(orderID));
                orderRepository.getOne(orderID).getComplaints().add(complaint);
            }
            if(l.containsKey("productID")){
                Integer productID = Integer.parseInt((String) l.get("productID"));
                complaint.setProduct(productRepository.getOne(productID));
                productRepository.getOne(productID).getComplaints().add(complaint);
            }
            if(l.containsKey("userID")){
                Integer userID = Integer.parseInt((String) l.get("userID"));
                complaint.setUser(userRepository.getOne(userID));
                userRepository.getOne(userID).getComplaints().add(complaint);
            }

            return complaintRepository.save(complaint);
        };
    }

    public DataFetcher deleteComplaintDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id =Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            Complaint complaint = complaintRepository.getOne(id);
            complaint.setDeleteDate(LocalDate.now());

            return complaintRepository.save(complaint);
        };
    }


}
