package com.javashop.javashop.graphql;

import com.javashop.javashop.model.Supplier;
import com.javashop.javashop.repository.SupplierRepository;
import com.javashop.javashop.repository.UserRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class SupplierDataFetchers {
    @Autowired
    private SupplierRepository supplierRepository;

    public DataFetcher getSupplierDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  supplierRepository.findById(id);
        };
    }

    public DataFetcher createSupplierDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            String name = (String) l.get("name");
            String telephone = (String) l.get("telephone");

            return supplierRepository.save(new Supplier(name, telephone));
        };
    }

    public DataFetcher updateSupplierDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer id =Integer.parseInt((String) l.get("id"));
            Supplier supplier = supplierRepository.getOne(id);
            if(l.containsKey("name")){
                String name = (String) l.get("name");
                supplier.setName(name);
            }
            if(l.containsKey("telephone")){
                String telephone = (String) l.get("telephone");
                supplier.setTelephone(telephone);
            }

            return supplierRepository.save(supplier);
        };
    }

    public DataFetcher deleteSupplierDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id =Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            Supplier supplier = supplierRepository.getOne(id);
            supplierRepository.delete(supplier);

            return supplier;
        };
    }
}
