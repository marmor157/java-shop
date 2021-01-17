package com.javashop.javashop.graphql;

import com.javashop.javashop.model.DeliveryAddress;
import com.javashop.javashop.model.Metadata;
import com.javashop.javashop.repository.DeliveryAddressRepository;
import com.javashop.javashop.repository.UserRepository;
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
public class DeliveryAddressDataFetchers {
    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;
    @Autowired
    private UserRepository userRepository;

    public DataFetcher getDeliveryAddressDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  deliveryAddressRepository.findById(id);
        };
    }

    public DataFetcher getAllDeliveryAddressesDataFetcher() {
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
                if(filter.containsKey("ids") && filter.containsKey("userID")){
                    final List<String> ids = (List<String>) filter.get("ids");
                    Integer userID = Integer.parseInt((String) filter.get("userID"));
                    List<Integer> idsInt = new ArrayList<>();
                    for(String s : ids) idsInt.add(Integer.valueOf(s));
                    return  deliveryAddressRepository.findByUserIdAndIdIn(userID,idsInt, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
                else if(filter.containsKey("ids")){
                    final List<String> ids = (List<String>) filter.get("ids");
                    List<Integer> idsInt = new ArrayList<>();
                    for(String s : ids) idsInt.add(Integer.valueOf(s));
                    return  deliveryAddressRepository.findByIdIn(idsInt, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
                else if(filter.containsKey("userID")){
                    Integer userID = Integer.parseInt((String) filter.get("userID"));
                    return  deliveryAddressRepository.findByUserId(userID, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
            }
            Page<DeliveryAddress> deliveryAddressPage = deliveryAddressRepository.findAll(PageRequest.of(page,perPage, Sort.by(order,sortField)));
            return deliveryAddressPage;
        };
    }

    public DataFetcher getAllDeliveryAddressesMetaDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> filter = dataFetchingEnvironment.getArgument("filter");

            if(filter!=null){
                if(filter.containsKey("ids")){
                    final List<String> ids = (List<String>) filter.get("ids");
                    List<Integer> idsInt = new ArrayList<>();
                    for(String s : ids) idsInt.add(Integer.valueOf(s));
                    return new Metadata(deliveryAddressRepository.countByIdIn(idsInt));
                }
            }
            Metadata metadata = new Metadata(deliveryAddressRepository.count());
            return metadata;
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
            Integer userID = Integer.parseInt((String) l.get("userID"));
            DeliveryAddress deliveryAddress = new DeliveryAddress(name,surname,city,street,buildingNumber,postCode);
            deliveryAddress.setUser(userRepository.getOne(userID));
            userRepository.getOne(userID).getDeliveryAddresses().add(deliveryAddress);
            return deliveryAddressRepository.save(deliveryAddress);
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
            if(l.containsKey("userID")){
                Integer userID = Integer.parseInt((String) l.get("userID"));
                deliveryAddress.setUser(userRepository.getOne(userID));
                userRepository.getOne(userID).getDeliveryAddresses().add(deliveryAddress);
            }

            return deliveryAddressRepository.save(deliveryAddress);
        };
    }

    public DataFetcher deleteDeliveryAddressDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id =Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            DeliveryAddress deliveryAddress = deliveryAddressRepository.getOne(id);
            deliveryAddressRepository.delete(deliveryAddress);

            return deliveryAddress;
        };
    }
}
