package com.javashop.javashop.details;

import com.javashop.javashop.DeliveryMethod;
import com.javashop.javashop.QueryExecutor;
import com.javashop.javashop.Role;
import com.javashop.javashop.TaxCategory;
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
}