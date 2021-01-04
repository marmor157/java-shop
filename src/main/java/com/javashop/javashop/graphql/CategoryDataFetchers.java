package com.javashop.javashop.graphql;

import com.javashop.javashop.model.Category;
import com.javashop.javashop.repository.CategoryRepository;
import com.javashop.javashop.repository.UserRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class CategoryDataFetchers {
    @Autowired
    private CategoryRepository categoryRepository;

    public DataFetcher getCategoryDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  categoryRepository.findById(id);
        };
    }

    public DataFetcher createCategoryDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            String name = (String) l.get("name");

            return categoryRepository.save(new Category(name));
        };
    }

    public DataFetcher updateCategoryDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer id =Integer.parseInt((String) l.get("id"));
            Category category = categoryRepository.getOne(id);
            if(l.containsKey("name")){
                String name = (String) l.get("name");
                category.setName(name);
            }

            return categoryRepository.save(category);
        };
    }
}
