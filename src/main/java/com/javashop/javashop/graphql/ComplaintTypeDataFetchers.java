package com.javashop.javashop.graphql;

import com.javashop.javashop.model.ComplaintType;
import com.javashop.javashop.repository.ComplaintTypeRepository;
import com.javashop.javashop.repository.UserRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class ComplaintTypeDataFetchers {
    @Autowired
    private ComplaintTypeRepository complaintTypeRepository;

    public DataFetcher getComplaintTypeDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  complaintTypeRepository.findById(id);
        };
    }

    public DataFetcher createComplaintTypeDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            String name = (String) l.get("name");

            return complaintTypeRepository.save(new ComplaintType(name));
        };
    }

    public DataFetcher updateComplaintTypeDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer id =Integer.parseInt((String) l.get("id"));
            ComplaintType complaintType = complaintTypeRepository.getOne(id);
            if(l.containsKey("name")){
                String name = (String) l.get("name");
                complaintType.setName(name);
            }

            return complaintTypeRepository.save(complaintType);
        };
    }
}
