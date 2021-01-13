package com.javashop.javashop.graphql;

import com.javashop.javashop.model.Product;
import com.javashop.javashop.model.Supplier;
import com.javashop.javashop.model.TaxCategory;
import com.javashop.javashop.repository.TaxCategoryRepository;
import com.javashop.javashop.repository.UserRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class TaxCategoryDataFetchers {
    @Autowired
    private TaxCategoryRepository taxCategoryRepository;

    public DataFetcher getTaxCategoryDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  taxCategoryRepository.findById(id);
        };
    }

    public DataFetcher getAllTaxCategoryDataFetcher() {
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

            if(sortField.equals("")){
                sortField = "id";
            }

            Page<TaxCategory> taxCategoryPage = taxCategoryRepository.findAll(PageRequest.of(page,perPage, Sort.by(order,sortField)));
            return taxCategoryPage;
        };
    }

    public DataFetcher getAllTaxCategoryMetaDataFetcher() {
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

            if(sortField.equals("")){
                sortField = "id";
            }
            Page<TaxCategory> productPage = taxCategoryRepository.findAll(PageRequest.of(page,perPage, Sort.by(order,sortField)));
            Metadata metadata = new Metadata(productPage.stream().count());
            return metadata;
        };
    }

    public DataFetcher createTaxCategoryDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer taxRate = (Integer) l.get("taxRate");
            String name = (String) l.get("name");
            return taxCategoryRepository.save(new TaxCategory(name, taxRate));
        };
    }

    public DataFetcher updateTaxCategoryDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer id =Integer.parseInt((String) l.get("id"));
            TaxCategory taxCategory = taxCategoryRepository.getOne(id);
            if(l.containsKey("name")){
                String name = (String) l.get("name");
                taxCategory.setName(name);
            }
            if(l.containsKey("taxRate")){
                Integer taxRate = (Integer) l.get("taxRate");
                taxCategory.setTaxRate(taxRate);
            }

            return taxCategoryRepository.save(taxCategory);
        };
    }

    public DataFetcher deleteTaxCategoryDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id =Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            TaxCategory taxCategory = taxCategoryRepository.getOne(id);
            taxCategoryRepository.delete(taxCategory);
            return taxCategory;
        };
    }
}
