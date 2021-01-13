package com.javashop.javashop.graphql;

import com.javashop.javashop.model.Order;
import com.javashop.javashop.model.Supplier;
import com.javashop.javashop.model.Warehouse;
import com.javashop.javashop.repository.UserRepository;
import com.javashop.javashop.repository.WarehouseRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

@Component
public class WarehouseDataFetchers {
    @Autowired
    private WarehouseRepository warehouseRepository;

    public DataFetcher getWarehouseDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  warehouseRepository.findById(id);
        };
    }

    public DataFetcher getAllWarehouseDataFetcher() {
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

            Page<Warehouse> warehousePage= warehouseRepository.findAll(PageRequest.of(page,perPage, Sort.by(order,sortField)));
            return warehousePage;
        };
    }

    public DataFetcher getAllWarehouseMetaDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  warehouseRepository.findById(id);
        };
    }

    public DataFetcher createWarehouseDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            String name = (String) l.get("name");
            String address = (String) l.get("address");

            return warehouseRepository.save(new Warehouse(name,address));
        };
    }

    public DataFetcher updateWarehouseDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer id =Integer.parseInt((String) l.get("id"));
            Warehouse warehouse = warehouseRepository.getOne(id);
            if(l.containsKey("name")){
                String name = (String) l.get("name");
                warehouse.setName(name);
            }
            if(l.containsKey("address")){
                String address = (String) l.get("address");
                warehouse.setAddress(address);
            }

            return warehouseRepository.save(warehouse);
        };
    }

    public DataFetcher deleteWarehouseDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id =Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            Warehouse warehouse = warehouseRepository.getOne(id);
            warehouseRepository.delete(warehouse);

            return warehouse;
        };
    }
}
