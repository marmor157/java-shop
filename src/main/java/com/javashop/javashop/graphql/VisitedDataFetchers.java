package com.javashop.javashop.graphql;

import com.javashop.javashop.model.Visited;
import com.javashop.javashop.repository.ProductRepository;
import com.javashop.javashop.repository.UserRepository;
import com.javashop.javashop.repository.VisitedRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

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

    public DataFetcher createVisitedDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            String dateStr = (String) l.get("date");
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
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
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
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
            visitedRepository.delete(visited);

            return visited;
        };
    }

}
