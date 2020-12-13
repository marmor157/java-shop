package com.javashop.javashop.details;

import com.javashop.javashop.*;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;

@Component
public class GraphQLDataFetchers {

    public DataFetcher getRoleByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            ResultSet result = QueryExecutor.executeSelect("SELECT * FROM Roles");
            result.next();
            Role retVal = new Role(result.getInt("ID"), result.getString("Name"));
            return retVal;
        };
    }

    public DataFetcher getTaxCategoryByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            ResultSet result = QueryExecutor.executeSelect("SELECT * FROM TaxCategory");
            result.next();
            TaxCategory retVal = new TaxCategory(result.getInt("ID"), result.getString("Name"),result.getInt("taxRate"));
            return retVal;
        };
    }

    public DataFetcher getDeliveryMethodByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            ResultSet result = QueryExecutor.executeSelect("SELECT * FROM DeliveryMethod");
            result.next();
            DeliveryMethod retVal = new DeliveryMethod(result.getInt("ID"), result.getString("Name"),
                                                        result.getInt("Price"),result.getInt("FreeThreshold"));
            return retVal;
        };
    }

    public DataFetcher getSubcategoryByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            ResultSet result = QueryExecutor.executeSelect("SELECT * FROM Subcategory");
            result.next();
            Subcategory retVal = new Subcategory(result.getInt("ID"), result.getString("Name"),result.getInt("categoryId"));
            return retVal;
        };
    }

    public DataFetcher getCategoryByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            ResultSet result = QueryExecutor.executeSelect("SELECT * FROM Category");
            result.next();
            Category retVal = new Category(result.getInt("ID"), result.getString("Name"));
            return retVal;
        };
    }

    public DataFetcher getProductByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            ResultSet result = QueryExecutor.executeSelect("SELECT * FROM Product");
            result.next();
            Product retVal = new Product(result.getInt("ID"), result.getString("Name"), result.getInt("Price"),
                                        result.getInt("numberAvailable"),result.getString("Description"), result.getInt("salePrice"),
                                        result.getString("ImageSrc"), result.getInt("TaxCategoryID"));
            return retVal;
        };
    }

    public DataFetcher getWarehouseByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            ResultSet result = QueryExecutor.executeSelect("SELECT * FROM Warehouse");
            result.next();
            Warehouse retVal = new Warehouse(result.getInt("ID"), result.getString("Name"), result.getString("Address"));
            return retVal;
        };
    }
}