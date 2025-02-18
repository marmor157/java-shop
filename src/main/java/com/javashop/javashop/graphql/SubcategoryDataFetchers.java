package com.javashop.javashop.graphql;

import com.javashop.javashop.model.*;
import com.javashop.javashop.repository.CategoryRepository;
import com.javashop.javashop.repository.SubcategoryRepository;
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

    public DataFetcher getAllSubcategoriesDataFetcher() {
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
                if(filter.containsKey("ids") && filter.containsKey("categoryID")){
                    final List<String> ids = (List<String>) filter.get("ids");
                    Integer categoryID = Integer.parseInt((String) filter.get("categoryID"));
                    List<Integer> idsInt = new ArrayList<>();
                    for(String s : ids) idsInt.add(Integer.valueOf(s));
                    return  subcategoryRepository.findByCategoryIdAndIdIn(categoryID,idsInt, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
                else if(filter.containsKey("ids")){
                    final List<String> ids = (List<String>) filter.get("ids");
                    List<Integer> idsInt = new ArrayList<>();
                    for(String s : ids) idsInt.add(Integer.valueOf(s));
                    return  subcategoryRepository.findByIdIn(idsInt, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
                else if(filter.containsKey("categoryID")){
                    Integer categoryID = Integer.parseInt((String) filter.get("categoryID"));
                    return  subcategoryRepository.findByCategoryId(categoryID, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
                else if(filter.containsKey("productID")){
                    final Integer productID = Integer.parseInt((String) filter.get("productID"));
                    return subcategoryRepository.findByProducts_Id(productID, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
            }
            Page<Subcategory> subcategoryPage = subcategoryRepository.findAll(PageRequest.of(page,perPage, Sort.by(order,sortField)));
            return subcategoryPage;
        };
    }

    public DataFetcher getAllSubcategoriesMetaDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> filter = dataFetchingEnvironment.getArgument("filter");

            if(filter!=null){
                if(filter.containsKey("ids")){
                    final List<String> ids = (List<String>) filter.get("ids");
                    List<Integer> idsInt = new ArrayList<>();
                    for(String s : ids) idsInt.add(Integer.valueOf(s));
                    return new Metadata(subcategoryRepository.countByIdIn(idsInt));
                }
                else if(filter.containsKey("productID")){
                    final Integer productID = Integer.parseInt((String) filter.get("productID"));
                    return new Metadata(subcategoryRepository.countByProducts_Id(productID));
                }
            }
            Metadata metadata = new Metadata(subcategoryRepository.count());
            return metadata;
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
            subcategory.setDeleteDate(LocalDate.now());

            return subcategoryRepository.save(subcategory);
        };
    }
}
