package com.javashop.javashop.graphql;

import com.javashop.javashop.model.Category;
import com.javashop.javashop.model.Metadata;
import com.javashop.javashop.repository.CategoryRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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

    public DataFetcher getAllCategoriesDataFetcher() {
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
                    return  categoryRepository.findByIdIn(idsInt, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
                else if(filter.containsKey("productID")){
                    final Integer productID = Integer.parseInt((String) filter.get("productID"));
                    return categoryRepository.findByProduct_Id(productID, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
            }
            Page<Category> categoryPage = categoryRepository.findAll(PageRequest.of(page,perPage, Sort.by(order,sortField)));
            return categoryPage;
        };
    }

    public DataFetcher getAllCategoriesMetaDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> filter = dataFetchingEnvironment.getArgument("filter");

            if(filter!=null){
                if(filter.containsKey("ids")){
                    final List<String> ids = (List<String>) filter.get("ids");
                    List<Integer> idsInt = new ArrayList<>();
                    for(String s : ids) idsInt.add(Integer.valueOf(s));
                    return new Metadata(categoryRepository.countByIdIn(idsInt));
                }
                else if(filter.containsKey("productID")){
                    final Integer productID = Integer.parseInt((String) filter.get("productID"));
                    return new Metadata(categoryRepository.countByProduct_Id(productID));
                }
            }
            Metadata metadata = new Metadata(categoryRepository.count());
            return metadata;
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

    public DataFetcher deleteCategoryDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id =Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            Category category = categoryRepository.getOne(id);
            categoryRepository.delete(category);

            return category;
        };
    }
}
