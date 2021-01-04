package com.javashop.javashop.graphql;

import com.javashop.javashop.model.Subcategory;
import com.javashop.javashop.repository.SubcategoryRepository;
import com.javashop.javashop.repository.UserRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class SubcategoryDataFetchers {
    @Autowired
    private SubcategoryRepository subcategoryRepository;

    public DataFetcher getSubcategoryDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  subcategoryRepository.findById(id);
        };
    }

    public DataFetcher createSubcategoryDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            String name = (String) l.get("name");

            return subcategoryRepository.save(new Subcategory(name));
        };
    }

    public DataFetcher updateSubcategoryDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer id =Integer.parseInt((String) l.get("id"));
            Subcategory subcategory = subcategoryRepository.getOne(id);
            if(l.containsKey("name")){
                String name = (String) l.get("name");
                subcategory.setName(name);
            }

            return subcategoryRepository.save(subcategory);
        };
    }

    public DataFetcher deleteSubcategoryDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id =Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            Subcategory subcategory = subcategoryRepository.getOne(id);
            subcategoryRepository.delete(subcategory);

            return subcategory;
        };
    }
}
