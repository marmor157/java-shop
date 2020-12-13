package com.javashop.javashop.bookdetails;

import com.google.common.collect.ImmutableMap;
import com.javashop.javashop.model.Complaint;
import com.javashop.javashop.repository.ComplaintRepository;
import com.javashop.javashop.repository.ComplaintTypeRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class GraphQLDataFetchers {


    @Autowired
    private ComplaintTypeRepository complaintTypeRepository;
    @Autowired
    private ComplaintRepository complaintRepository;

    public DataFetcher getComplaintById() {
        return dataFetchingEnvironment -> {
            Integer complaintId = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));

            return complaintRepository.findById(complaintId);
        };
    }

    public DataFetcher getComplaintTypeDataFetcher() {
        return dataFetchingEnvironment -> {
            System.out.println(dataFetchingEnvironment.getSource().toString());
            Complaint complaint = dataFetchingEnvironment.getSource();
            return complaintTypeRepository.findById(complaint.getComplaintTypeId());
        };
    }

}