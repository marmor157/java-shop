package com.javashop.javashop.graphql;

import com.javashop.javashop.model.*;
import com.javashop.javashop.repository.*;
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
public class ProductSupplierDataFetchers {
    @Autowired
    private ProductSupplierRepository productSupplierRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SupplierRepository supplierRepository;

    public DataFetcher getProductSupplierDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  productSupplierRepository.findById(id);
        };
    }

    public DataFetcher getAllProductSuppliersDataFetcher() {
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
                    return  productSupplierRepository.findByIdIn(idsInt, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
            }
            Page<ProductSupplier> productSupplierPage = productSupplierRepository.findAll(PageRequest.of(page,perPage, Sort.by(order,sortField)));
            return productSupplierPage;
        };
    }

    public DataFetcher getAllProductSuppliersMetaDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> filter = dataFetchingEnvironment.getArgument("filter");

            if(filter!=null){
                if(filter.containsKey("ids")){
                    final List<String> ids = (List<String>) filter.get("ids");
                    List<Integer> idsInt = new ArrayList<>();
                    for(String s : ids) idsInt.add(Integer.valueOf(s));
                    return new Metadata(productSupplierRepository.countByIdIn(idsInt));
                }
            }
            Metadata metadata = new Metadata(productSupplierRepository.count());
            return metadata;
        };
    }

    public DataFetcher createProductSupplierDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer price = (Integer) l.get("price");
            Integer productID = Integer.parseInt((String) l.get("productID"));
            Integer supplierID = Integer.parseInt((String) l.get("supplierID"));
            ProductSupplier productSupplier = new ProductSupplier(price);
            productSupplier.setProduct(productRepository.getOne(productID));
            productRepository.getOne(productID).getSuppliers().add(productSupplier);
            productSupplier.setSupplier(supplierRepository.getOne(supplierID));
            supplierRepository.getOne(supplierID).getProducts().add(productSupplier);

            return productSupplierRepository.save(productSupplier);
        };
    }

    public DataFetcher updateProductSupplierDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer id =Integer.parseInt((String) l.get("id"));
            ProductSupplier productSupplier = productSupplierRepository.getOne(id);
            if(l.containsKey("price")){
                Integer price = (Integer) l.get("price");
                productSupplier.setPrice(price);
            }
            if(l.containsKey("productID")){
                Integer productID = Integer.parseInt((String) l.get("productID"));
                productSupplier.setProduct(productRepository.getOne(productID));
                productRepository.getOne(productID).getSuppliers().add(productSupplier);
            }
            if(l.containsKey("supplierID")){
                Integer supplierID = Integer.parseInt((String) l.get("supplierID"));
                productSupplier.setSupplier(supplierRepository.getOne(supplierID));
                supplierRepository.getOne(supplierID).getProducts().add(productSupplier);
            }

            return productSupplierRepository.save(productSupplier);
        };
    }

    public DataFetcher deleteProductSupplierDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id =Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            ProductSupplier productSupplier = productSupplierRepository.getOne(id);
            productSupplierRepository.delete(productSupplier);

            return productSupplier;
        };
    }

}
