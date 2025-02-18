package com.javashop.javashop.graphql;

import com.javashop.javashop.model.ComplaintType;
import com.javashop.javashop.model.Metadata;
import com.javashop.javashop.model.Product;
import com.javashop.javashop.repository.ComplaintTypeRepository;
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
import java.util.function.Predicate;

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

    public DataFetcher getAllComplaintTypesDataFetcher() {
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
                    return  complaintTypeRepository.findByIdIn(idsInt, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
            }
            Page<ComplaintType> complaintTypePage = complaintTypeRepository.findAll(PageRequest.of(page,perPage, Sort.by(order,sortField)));
            return complaintTypePage;
        };
    }

    public DataFetcher getAllComplaintTypesMetaDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> filter = dataFetchingEnvironment.getArgument("filter");

            if(filter!=null){
                if(filter.containsKey("ids")){
                    final List<String> ids = (List<String>) filter.get("ids");
                    List<Integer> idsInt = new ArrayList<>();
                    for(String s : ids) idsInt.add(Integer.valueOf(s));
                    return new Metadata(complaintTypeRepository.countByIdIn(idsInt));
                }
            }
            Metadata metadata = new Metadata(complaintTypeRepository.count());
            return metadata;
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

    public DataFetcher deleteComplaintTypeDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id =Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            ComplaintType complaintType = complaintTypeRepository.getOne(id);
            complaintType.setDeleteDate(LocalDate.now());

            return complaintTypeRepository.save(complaintType);
        };
    }
}
