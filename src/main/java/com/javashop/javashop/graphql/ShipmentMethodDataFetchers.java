package com.javashop.javashop.graphql;

import com.javashop.javashop.model.ShipmentMethod;
import com.javashop.javashop.model.User;
import com.javashop.javashop.repository.ShipmentMethodRepository;
import com.javashop.javashop.repository.UserRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class ShipmentMethodDataFetchers {
    @Autowired
    private ShipmentMethodRepository shipmentMethodRepository;

    public DataFetcher getShipmentMethodDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  shipmentMethodRepository.findById(id);
        };
    }

    public DataFetcher getAllShipmentMethodDataFetcher() {
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

            Page<ShipmentMethod> shipmentMethodPage = shipmentMethodRepository.findAll(PageRequest.of(page,perPage, Sort.by(order,sortField)));
            return shipmentMethodPage;
        };
    }

    public DataFetcher getAllShipmentMethodMetaDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  shipmentMethodRepository.findById(id);
        };
    }

    public DataFetcher createShipmentMethodDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            String name = (String) l.get("name");
            Integer price = (Integer) l.get("price");
            Integer freeThreshold = (Integer) l.get("freeThreshold");
            return shipmentMethodRepository.save(new ShipmentMethod(name,price,freeThreshold));
        };
    }

    public DataFetcher updateShipmentMethodDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");


            Integer id =Integer.parseInt((String) l.get("id"));
            ShipmentMethod shipmentMethod = shipmentMethodRepository.getOne(id);
            if(l.containsKey("name")){
                String name = (String) l.get("name");
                shipmentMethod.setName(name);
            }
            if(l.containsKey("price")){
                Integer price = (Integer) l.get("price");
                shipmentMethod.setPrice(price);
            }
            if(l.containsKey("freeThreshold")){
                Integer freeThreshold = (Integer) l.get("freeThreshold");
                shipmentMethod.setFreeThreshold(freeThreshold);
            }

            return shipmentMethodRepository.save(shipmentMethod);
        };
    }

    public DataFetcher deleteShipmentMethodDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id =Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            ShipmentMethod shipmentMethod = shipmentMethodRepository.getOne(id);
            shipmentMethodRepository.delete(shipmentMethod);

            return shipmentMethod;
        };
    }
}
