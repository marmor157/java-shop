package com.javashop.javashop.graphql;

import com.javashop.javashop.model.Metadata;
import com.javashop.javashop.model.Opinion;
import com.javashop.javashop.repository.OpinionRepository;
import com.javashop.javashop.repository.ProductRepository;
import com.javashop.javashop.repository.UserRepository;
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

    public DataFetcher getAllOpinionsDataFetcher() {
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
                    return  opinionRepository.findByIdIn(idsInt, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
            }
            Page<Opinion> opinionPage = opinionRepository.findAll(PageRequest.of(page,perPage, Sort.by(order,sortField)));
            return opinionPage;
        };
    }

    public DataFetcher getAllOpinionsMetaDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> filter = dataFetchingEnvironment.getArgument("filter");

            if(filter!=null){
                if(filter.containsKey("ids")){
                    final List<String> ids = (List<String>) filter.get("ids");
                    List<Integer> idsInt = new ArrayList<>();
                    for(String s : ids) idsInt.add(Integer.valueOf(s));
                    return new Metadata(opinionRepository.countByIdIn(idsInt));
                }
            }
            Metadata metadata = new Metadata(opinionRepository.count());
            return metadata;
        };
    }

    public DataFetcher createOpinionDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer mark = (Integer) l.get("mark");
            String text = (String) l.get("text");
            LocalDate date = LocalDate.now();
            Integer productID = Integer.parseInt((String) l.get("productID"));
            Integer userID = Integer.parseInt((String) l.get("userID"));
            Opinion opinion = new Opinion(mark, text, date);
            opinion.setUser(userRepository.getOne(userID));
            userRepository.getOne(userID).getOpinions().add(opinion);
            opinion.setProduct(productRepository.getOne(productID));
            productRepository.getOne(productID).getOpinions().add(opinion);

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
            if(l.containsKey("date")){
                String dateStr = (String) l.get("date");
                LocalDate date = LocalDate.parse(dateStr);
                opinion.setDate(date);
            }
            if(l.containsKey("productID")){
                Integer productID = Integer.parseInt((String) l.get("productID"));
                opinion.setProduct(productRepository.getOne(productID));
                productRepository.getOne(productID).getOpinions().add(opinion);
            }
            if(l.containsKey("userID")){
                Integer userID = Integer.parseInt((String) l.get("userID"));
                opinion.setUser(userRepository.getOne(userID));
                userRepository.getOne(userID).getOpinions().add(opinion);
            }

            return opinionRepository.save(opinion);
        };
    }

    public DataFetcher deleteOpinionDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id =Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            Opinion opinion = opinionRepository.getOne(id);
            opinion.setDeleteDate(LocalDate.now());

            return opinionRepository.save(opinion);
        };
    }

}
