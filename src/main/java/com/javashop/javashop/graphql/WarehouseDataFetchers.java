package com.javashop.javashop.graphql;

import com.javashop.javashop.repository.UserRepository;
import com.javashop.javashop.repository.WarehouseRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
