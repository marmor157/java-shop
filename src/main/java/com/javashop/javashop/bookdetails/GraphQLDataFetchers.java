package com.javashop.javashop.bookdetails;

import com.google.common.collect.ImmutableMap;
import com.javashop.javashop.QueryExecutor;
import com.javashop.javashop.Role;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class GraphQLDataFetchers {

    public DataFetcher getRoleByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            String roleId = dataFetchingEnvironment.getArgument("id");
            ResultSet result = QueryExecutor.executeSelect("SELECT * FROM Roles");
            result.next();
            Role retVal = new Role(result.getInt("ID"), result.getString("Name"));
            return retVal;
        };
    }


}