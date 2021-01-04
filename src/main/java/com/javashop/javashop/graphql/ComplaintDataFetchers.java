package com.javashop.javashop.graphql;

import com.javashop.javashop.model.Complaint;
import com.javashop.javashop.repository.ComplaintRepository;
import com.javashop.javashop.repository.UserRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

@Component
public class ComplaintDataFetchers {
    @Autowired
    private ComplaintRepository complaintRepository;

    public DataFetcher getComplaintDataFetcher() {
        return dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return complaintRepository.findById(id);
        };
    }

    public DataFetcher createComplaintDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            String dateStr = (String) l.get("date");
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);

            return complaintRepository.save(new Complaint(date));
        };
    }

    public DataFetcher updateComplaintDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer id =Integer.parseInt((String) l.get("id"));
            Complaint complaint = complaintRepository.getOne(id);
            if(l.containsKey("date")){
                String dateStr = (String) l.get("date");
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
                complaint.setDate(date);
            }

            return complaintRepository.save(complaint);
        };
    }
}
