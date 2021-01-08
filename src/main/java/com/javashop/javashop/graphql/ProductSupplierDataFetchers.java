package com.javashop.javashop.graphql;

import com.javashop.javashop.model.Opinion;
import com.javashop.javashop.model.Product;
import com.javashop.javashop.model.ProductSupplier;
import com.javashop.javashop.model.Supplier;
import com.javashop.javashop.repository.*;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

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
