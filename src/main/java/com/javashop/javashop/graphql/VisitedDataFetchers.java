package com.javashop.javashop.graphql;

import com.javashop.javashop.model.Metadata;
import com.javashop.javashop.model.Visited;
import com.javashop.javashop.repository.ProductRepository;
import com.javashop.javashop.repository.UserRepository;
import com.javashop.javashop.repository.VisitedRepository;
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
public class VisitedDataFetchers {
    @Autowired
    private VisitedRepository visitedRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    public DataFetcher getVisitedDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  visitedRepository.findById(id);
        };
    }

    public DataFetcher getAllVisitedDataFetcher() {
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
                    return  visitedRepository.findByIdIn(idsInt, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
            }
            Page<Visited> visitedPage = visitedRepository.findAll(PageRequest.of(page,perPage, Sort.by(order,sortField)));
            return visitedPage;
        };
    }

    public DataFetcher getAllVisitedMetaDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> filter = dataFetchingEnvironment.getArgument("filter");

            if(filter!=null){
                if(filter.containsKey("ids")){
                    final List<String> ids = (List<String>) filter.get("ids");
                    List<Integer> idsInt = new ArrayList<>();
                    for(String s : ids) idsInt.add(Integer.valueOf(s));
                    return new Metadata(visitedRepository.countByIdIn(idsInt));
                }
            }
            Metadata metadata = new Metadata(visitedRepository.count());
            return metadata;
        };
    }

    public DataFetcher createVisitedDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            LocalDate date = LocalDate.now();
            Integer productID = Integer.parseInt((String) l.get("productID"));
            Integer userID = Integer.parseInt((String) l.get("userID"));
            Visited visited = new Visited(date);
            visited.setProduct(productRepository.getOne(productID));
            productRepository.getOne(productID).getVisited().add(visited);
            visited.setUser(userRepository.getOne(userID));
            userRepository.getOne(userID).getVisited().add(visited);

            return visitedRepository.save(visited);
        };
    }

    public DataFetcher updateVisitedDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer id =Integer.parseInt((String) l.get("id"));
            Visited visited = visitedRepository.getOne(id);
            if(l.containsKey("date")){
                String dateStr = (String) l.get("date");
                LocalDate date = LocalDate.parse(dateStr);
                visited.setDate(date);
            }
            if(l.containsKey("productID")){
                Integer productID = Integer.parseInt((String) l.get("productID"));
                visited.setProduct(productRepository.getOne(productID));
                productRepository.getOne(productID).getVisited().add(visited);
            }
            if(l.containsKey("userID")){
                Integer userID = Integer.parseInt((String) l.get("userID"));
                visited.setUser(userRepository.getOne(userID));
                userRepository.getOne(userID).getVisited().add(visited);
            }

            return visitedRepository.save(visited);
        };
    }

    public DataFetcher deleteVisitedDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id =Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            Visited visited = visitedRepository.getOne(id);
            visited.setDeleteDate(LocalDate.now());

            return visitedRepository.save(visited);
        };
    }

}
