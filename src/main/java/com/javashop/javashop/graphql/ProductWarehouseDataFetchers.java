package com.javashop.javashop.graphql;

import com.javashop.javashop.model.*;
import com.javashop.javashop.repository.*;
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
public class ProductWarehouseDataFetchers {
    @Autowired
    private ProductWarehouseRepository productWarehouseRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;

    public DataFetcher getProductWarehouseDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  productWarehouseRepository.findById(id);
        };
    }

    public DataFetcher getAllProductWarehousesDataFetcher() {
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
                    return  productWarehouseRepository.findByIdIn(idsInt, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
            }
            Page<ProductWarehouse> productWarehousePage = productWarehouseRepository.findAll(PageRequest.of(page,perPage, Sort.by(order,sortField)));
            return productWarehousePage;
        };
    }

    public DataFetcher getAllProductWarehousesMetaDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> filter = dataFetchingEnvironment.getArgument("filter");

            if(filter!=null){
                if(filter.containsKey("ids")){
                    final List<String> ids = (List<String>) filter.get("ids");
                    List<Integer> idsInt = new ArrayList<>();
                    for(String s : ids) idsInt.add(Integer.valueOf(s));
                    return new Metadata(productWarehouseRepository.countByIdIn(idsInt));
                }
            }
            Metadata metadata = new Metadata(productWarehouseRepository.count());
            return metadata;
        };
    }

    public DataFetcher createProductWarehouseDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer quantity = (Integer) l.get("quantity");
            Integer productID = Integer.parseInt((String) l.get("productID"));
            Integer warehouseID = Integer.parseInt((String) l.get("warehouseID"));
            ProductWarehouse productWarehouse = new ProductWarehouse(quantity);
            productWarehouse.setProduct(productRepository.getOne(productID));
            productRepository.getOne(productID).getWarehouses().add(productWarehouse);
            productWarehouse.setWarehouse(warehouseRepository.getOne(warehouseID));
            warehouseRepository.getOne(warehouseID).getProducts().add(productWarehouse);

            return productWarehouseRepository.save(productWarehouse);
        };
    }

    public DataFetcher updateProductWarehouseDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer id =Integer.parseInt((String) l.get("id"));
            ProductWarehouse productWarehouse = productWarehouseRepository.getOne(id);
            if(l.containsKey("quantity")){
                Integer quantity = (Integer) l.get("quantity");
                productWarehouse.setQuantity(quantity);
            }
            if(l.containsKey("productID")){
                Integer productID = Integer.parseInt((String) l.get("productID"));
                productWarehouse.setProduct(productRepository.getOne(productID));
                productRepository.getOne(productID).getWarehouses().add(productWarehouse);
            }
            if(l.containsKey("warehouseID")){
                Integer warehouseID = Integer.parseInt((String) l.get("warehouseID"));
                productWarehouse.setWarehouse(warehouseRepository.getOne(warehouseID));
                warehouseRepository.getOne(warehouseID).getProducts().add(productWarehouse);
            }

            return productWarehouseRepository.save(productWarehouse);
        };
    }

    public DataFetcher deleteProductWarehouseDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id =Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            ProductWarehouse productWarehouse = productWarehouseRepository.getOne(id);
            productWarehouse.setDeleteDate(LocalDate.now());

            return productWarehouseRepository.save(productWarehouse);
        };
    }

}
