package com.javashop.javashop.graphql;

import com.javashop.javashop.model.Opinion;
import com.javashop.javashop.repository.OpinionRepository;
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

            return opinionRepository.save(new Opinion(mark, text, date));
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
