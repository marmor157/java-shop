package com.javashop.javashop.graphql;

import com.javashop.javashop.model.Metadata;
import com.javashop.javashop.model.Supplier;
import com.javashop.javashop.repository.SupplierRepository;
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
public class SupplierDataFetchers {
    @Autowired
    private SupplierRepository supplierRepository;

    public DataFetcher getSupplierDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  supplierRepository.findById(id);
        };
    }

    public DataFetcher getAllSuppliersDataFetcher() {
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
                    return  supplierRepository.findByIdIn(idsInt, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
                else if(filter.containsKey("productID")){
                    final Integer productID = Integer.parseInt((String) filter.get("productID"));
                    return supplierRepository.findByProducts_Id(productID, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
            }
            Page<Supplier> supplierPage = supplierRepository.findAll(PageRequest.of(page,perPage, Sort.by(order,sortField)));
            return supplierPage;
        };
    }

    public DataFetcher getAllSuppliersMetaDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> filter = dataFetchingEnvironment.getArgument("filter");

            if(filter!=null){
                if(filter.containsKey("ids")){
                    final List<String> ids = (List<String>) filter.get("ids");
                    List<Integer> idsInt = new ArrayList<>();
                    for(String s : ids) idsInt.add(Integer.valueOf(s));
                    return new Metadata(supplierRepository.countByIdIn(idsInt));
                }
                else if(filter.containsKey("productID")){
                    final Integer productID = Integer.parseInt((String) filter.get("productID"));
                    return new Metadata(supplierRepository.countByProducts_Id(productID));
                }
            }
            Metadata metadata = new Metadata(supplierRepository.count());
            return metadata;
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
            supplier.setDeleteDate(LocalDate.now());

            return supplierRepository.save(supplier);
        };
    }
}
