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
                        .dataFetcher("DeliveryAddress", graphQLDataFetchers.getDeliveryAddressDataFetcher())
                        .dataFetcher("ShipmentMethod", graphQLDataFetchers.getShipmentMethodDataFetcher()))
                .type(newTypeWiring("Mutation")
                        .dataFetcher("createUser", graphQLDataFetchers.createUserDataFetcher())
                        .dataFetcher("updateUser", graphQLDataFetchers.updateUserDataFetcher())
                        .dataFetcher("createProduct", graphQLDataFetchers.createProductDataFetcher())
                        .dataFetcher("updateProduct", graphQLDataFetchers.updateProductDataFetcher())
                        .dataFetcher("createOrder", graphQLDataFetchers.createOrderDataFetcher())
                        .dataFetcher("updateOrder", graphQLDataFetchers.updateOrderDataFetcher())
                        .dataFetcher("createOpinion", graphQLDataFetchers.createOpinionDataFetcher())
                        .dataFetcher("updateOpinion", graphQLDataFetchers.updateOpinionDataFetcher())
                        .dataFetcher("createCategory", graphQLDataFetchers.createCategoryDataFetcher())
                        .dataFetcher("updateCategory", graphQLDataFetchers.updateCategoryDataFetcher())
                        .dataFetcher("createSubcategory", graphQLDataFetchers.createSubcategoryDataFetcher())
                        .dataFetcher("updateSubcategory", graphQLDataFetchers.updateSubcategoryDataFetcher())
                        .dataFetcher("createComplaint", graphQLDataFetchers.createComplaintDataFetcher())
                        .dataFetcher("updateComplaint", graphQLDataFetchers.updateComplaintDataFetcher())
                        .dataFetcher("createComplaintType", graphQLDataFetchers.createComplaintTypeDataFetcher())
                        .dataFetcher("updateComplaintType", graphQLDataFetchers.updateComplaintTypeDataFetcher())
                        .dataFetcher("createDeliveryAddress", graphQLDataFetchers.createDeliveryAddressDataFetcher())
                        .dataFetcher("updateDeliveryAddress", graphQLDataFetchers.updateDeliveryAddressDataFetcher())
                        .dataFetcher("createRole", graphQLDataFetchers.createRoleDataFetcher())
                        .dataFetcher("updateRole", graphQLDataFetchers.updateRoleDataFetcher())
                        .dataFetcher("createShipmentMethod", graphQLDataFetchers.createShipmentMethodDataFetcher())
                        .dataFetcher("updateShipmentMethod", graphQLDataFetchers.updateShipmentMethodDataFetcher())
                        .dataFetcher("createTaxCategory", graphQLDataFetchers.createTaxCategoryDataFetcher())
                        .dataFetcher("updateTaxCategory", graphQLDataFetchers.updateTaxCategoryDataFetcher())
                        .dataFetcher("createSupplier", graphQLDataFetchers.createSupplierDataFetcher())
                        .dataFetcher("updateSupplier", graphQLDataFetchers.updateSupplierDataFetcher()))
                .build();
    }

}