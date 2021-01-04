package com.javashop.javashop.graphql;


import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;


import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Component
public class GraphQLProvider {

    private GraphQL graphQL;
    @Autowired
    GraphQLDataFetchers graphQLDataFetchers;

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    @PostConstruct
    public void init() throws IOException {
        URL url = Resources.getResource("schema.graphqls");
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                        .dataFetcher("Complaint", graphQLDataFetchers.getComplaintDataFetcher())
                        .dataFetcher("ComplaintType", graphQLDataFetchers.getComplaintTypeDataFetcher())
                        .dataFetcher("Role", graphQLDataFetchers.getRoleDataFetcher())
                        .dataFetcher("Product", graphQLDataFetchers.getProductDataFetcher())
                        .dataFetcher("TaxCategory", graphQLDataFetchers.getTaxCategoryDataFetcher())
                        .dataFetcher("User", graphQLDataFetchers.getUserDataFetcher())
                        .dataFetcher("Order", graphQLDataFetchers.getOrderDataFetcher())
                        .dataFetcher("Category", graphQLDataFetchers.getCategoryDataFetcher())
                        .dataFetcher("Subcategory", graphQLDataFetchers.getSubcategoryDataFetcher())
                        .dataFetcher("Supplier", graphQLDataFetchers.getSupplierDataFetcher())
                        .dataFetcher("Opinion", graphQLDataFetchers.getOpinionDataFetcher())
                        .dataFetcher("Warehouse", graphQLDataFetchers.getWarehouseDataFetcher())
                        .dataFetcher("Warehouse", graphQLDataFetchers.getDeliveryAddressDataFetcher())
                        .dataFetcher("ShipmentMethod", graphQLDataFetchers.getShipmentMethodDataFetcher()))
                .type(newTypeWiring("Mutation")
                        .dataFetcher("createUser", graphQLDataFetchers.createUserDataFetcher())
                        .dataFetcher("updateUser", graphQLDataFetchers.updateUserDataFetcher())
                        .dataFetcher("createProduct", graphQLDataFetchers.createProductDataFetcher())
                        .dataFetcher("createOrder", graphQLDataFetchers.createOrderDataFetcher())
                        .dataFetcher("createOpinion", graphQLDataFetchers.createOpinionDataFetcher())
                        .dataFetcher("createCategory", graphQLDataFetchers.createCategoryDataFetcher())
                        .dataFetcher("createSubcategory", graphQLDataFetchers.createSubcategoryDataFetcher())
                        .dataFetcher("createComplaint", graphQLDataFetchers.createComplaintDataFetcher())
                        .dataFetcher("createComplaintType", graphQLDataFetchers.createComplaintTypeDataFetcher())
                        .dataFetcher("createDeliveryAddress", graphQLDataFetchers.createDeliveryAddressDataFetcher())
                        .dataFetcher("createRole", graphQLDataFetchers.createRoleDataFetcher())
                        .dataFetcher("createShipmentMethod", graphQLDataFetchers.createShipmentMethodDataFetcher())
                        .dataFetcher("createTaxCategory", graphQLDataFetchers.createTaxCategoryDataFetcher())
                        .dataFetcher("createSupplier", graphQLDataFetchers.createSupplierDataFetcher()))
                .build();
    }

}