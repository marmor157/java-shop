package com.javashop.javashop.graphql;

import com.javashop.javashop.model.DeliveryAddress;
import com.javashop.javashop.repository.DeliveryAddressRepository;
import com.javashop.javashop.repository.UserRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class DeliveryAddressDataFetchers {
    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;

    public DataFetcher getDeliveryAddressDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  deliveryAddressRepository.findById(id);
        };
    }

    public DataFetcher createDeliveryAddressDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            String name = (String) l.get("name");
            String surname = (String) l.get("surname");
            String city = (String) l.get("city");
            String street = (String) l.get("street");
            String buildingNumber = (String) l.get("buildingNumber");
            String postCode = (String) l.get("postCode");

            return deliveryAddressRepository.save(new DeliveryAddress(name,surname,city,street,buildingNumber,postCode));
        };
    }

    public DataFetcher updateDeliveryAddressDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer id =Integer.parseInt((String) l.get("id"));
            DeliveryAddress deliveryAddress = deliveryAddressRepository.getOne(id);
            if(l.containsKey("name")){
                String name = (String) l.get("name");
                deliveryAddress.setName(name);
            }
            if(l.containsKey("surname")){
                String surname = (String) l.get("surname");
                deliveryAddress.setSurname(surname);
            }
            if(l.containsKey("city")){
                String city = (String) l.get("city");
                deliveryAddress.setCity(city);
            }
            if(l.containsKey("street")){
                String street = (String) l.get("street");
                deliveryAddress.setStreet(street);
            }
            if(l.containsKey("buildingNumber")){
                String buildingNumber = (String) l.get("buildingNumber");
                deliveryAddress.setBuildingNumber(buildingNumber);
            }
            if(l.containsKey("postCode")){
                String postCode = (String) l.get("postCode");
                deliveryAddress.setPostCode(postCode);
            }

            return deliveryAddressRepository.save(deliveryAddress);
        };
    }
}
