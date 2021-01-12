package com.javashop.javashop.graphql;

import com.javashop.javashop.model.Category;
import com.javashop.javashop.model.Subcategory;
import com.javashop.javashop.model.User;
import com.javashop.javashop.repository.CategoryRepository;
import com.javashop.javashop.repository.SubcategoryRepository;
import com.javashop.javashop.repository.UserRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class SubcategoryDataFetchers {
    @Autowired
    private SubcategoryRepository subcategoryRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public DataFetcher getSubcategoryDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  subcategoryRepository.findById(id);
        };
    }

    public DataFetcher getAllSubcategoryDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer page = dataFetchingEnvironment.getArgument("page");
            Integer perPage = dataFetchingEnvironment.getArgument("perPage");
            String sortField = dataFetchingEnvironment.getArgument("sortField");
            String sortOrder = dataFetchingEnvironment.getArgument("sortOrder");
            LinkedHashMap<String, Object> filter = dataFetchingEnvironment.getArgument("filter");

            Sort.Direction order = null;
            if(sortOrder.toUpperCase().equals("DESC")){
                order = Sort.Direction.DESC;
            }
            else{
                order = Sort.Direction.ASC;
            }

            if(sortField==""){
                sortField = "id";
            }

            Page<Subcategory> subcategoryPage = subcategoryRepository.findAll(PageRequest.of(page,perPage, Sort.by(order,sortField)));
            return subcategoryPage;
        };
    }

    public DataFetcher getAllSubcategoryMetaDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  subcategoryRepository.findById(id);
        };
    }

    public DataFetcher createSubcategoryDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");
            String name = (String) l.get("name");
            Integer categoryID = Integer.parseInt((String) l.get("categoryID"));
            Subcategory subcategory = new Subcategory(name);
            subcategory.setCategory(categoryRepository.getOne(categoryID));
            categoryRepository.getOne(categoryID).getSubcategories().add(subcategory);

            return subcategoryRepository.save(subcategory);
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
            if(l.containsKey("categoryID")){
                Integer categoryID = Integer.parseInt((String) l.get("categoryID"));
                subcategory.setCategory(categoryRepository.getOne(categoryID));
                categoryRepository.getOne(categoryID).getSubcategories().add(subcategory);
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
