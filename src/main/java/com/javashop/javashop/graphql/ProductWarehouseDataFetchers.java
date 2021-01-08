package com.javashop.javashop.graphql;

import com.javashop.javashop.model.ProductSupplier;
import com.javashop.javashop.model.ProductWarehouse;
import com.javashop.javashop.model.Warehouse;
import com.javashop.javashop.repository.*;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

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
            productWarehouseRepository.delete(productWarehouse);

            return productWarehouse;
        };
    }

}
