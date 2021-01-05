package com.javashop.javashop.graphql;

import com.javashop.javashop.model.Opinion;
import com.javashop.javashop.repository.OpinionRepository;
import com.javashop.javashop.repository.ProductRepository;
import com.javashop.javashop.repository.UserRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

@Component
public class OpinionDataFetchers {
    @Autowired
    private OpinionRepository opinionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    public DataFetcher getOpinionDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  opinionRepository.findById(id);
        };
    }

    public DataFetcher createOpinionDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer mark = (Integer) l.get("mark");
            String text = (String) l.get("text");
            String dateStr = (String) l.get("date");
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
            Integer productID = Integer.parseInt((String) l.get("productID"));
            Integer userID = Integer.parseInt((String) l.get("userID"));
            Opinion opinion = new Opinion(mark, text, date);
            opinion.setUser(userRepository.getOne(userID));
            opinion.setProduct(productRepository.getOne(productID));

            return opinionRepository.save(opinion);
        };
    }

    public DataFetcher updateOpinionDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer id =Integer.parseInt((String) l.get("id"));
            Opinion opinion = opinionRepository.getOne(id);
            if(l.containsKey("mark")){
                Integer mark = (Integer) l.get("mark");
                opinion.setMark(mark);
            }
            if(l.containsKey("text")){
                String text = (String) l.get("text");
                opinion.setText(text);
            }
            if(l.containsKey("text")){
                String text = (String) l.get("text");
                opinion.setText(text);
            }
            if(l.containsKey("date")){
                String dateStr = (String) l.get("date");
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
                opinion.setDate(date);
            }
            if(l.containsKey("text")){
                Integer productID = Integer.parseInt((String) l.get("productID"));
                opinion.setProduct(productRepository.getOne(productID));
            }
            if(l.containsKey("userID")){
                Integer userID = Integer.parseInt((String) l.get("userID"));
                opinion.setUser(userRepository.getOne(userID));
            }

            return opinionRepository.save(opinion);
        };
    }

    public DataFetcher deleteOpinionDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id =Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            Opinion opinion = opinionRepository.getOne(id);
            opinionRepository.delete(opinion);

            return opinion;
        };
    }

}
