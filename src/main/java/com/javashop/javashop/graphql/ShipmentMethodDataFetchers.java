package com.javashop.javashop.graphql;

import com.javashop.javashop.model.Metadata;
import com.javashop.javashop.model.ShipmentMethod;
import com.javashop.javashop.repository.ShipmentMethodRepository;
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
public class ShipmentMethodDataFetchers {
    @Autowired
    private ShipmentMethodRepository shipmentMethodRepository;

    public DataFetcher getShipmentMethodDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  shipmentMethodRepository.findById(id);
        };
    }

    public DataFetcher getAllShipmentMethodsDataFetcher() {
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
                    return  shipmentMethodRepository.findByIdIn(idsInt, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
            }
            Page<ShipmentMethod> shipmentMethodPage = shipmentMethodRepository.findAll(PageRequest.of(page,perPage, Sort.by(order,sortField)));
            return shipmentMethodPage;
        };
    }

    public DataFetcher getAllShipmentMethodsMetaDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> filter = dataFetchingEnvironment.getArgument("filter");

            if(filter!=null){
                if(filter.containsKey("ids")){
                    final List<String> ids = (List<String>) filter.get("ids");
                    List<Integer> idsInt = new ArrayList<>();
                    for(String s : ids) idsInt.add(Integer.valueOf(s));
                    return new Metadata(shipmentMethodRepository.countByIdIn(idsInt));
                }
            }
            Metadata metadata = new Metadata(shipmentMethodRepository.count());
            return metadata;
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
