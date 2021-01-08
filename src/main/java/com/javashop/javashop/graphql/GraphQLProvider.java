package com.javashop.javashop.graphql;


import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.javashop.javashop.model.Complaint;
import com.javashop.javashop.model.ComplaintType;
import com.javashop.javashop.model.Opinion;
import com.javashop.javashop.repository.OrderRepository;
import com.javashop.javashop.repository.ShipmentMethodRepository;
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
    @Autowired
    UserDataFetchers userDataFetchers;
    @Autowired
    ProductDataFetchers productDataFetchers;
    @Autowired
    OrderDataFetchers orderDataFetchers;
    @Autowired
    OpinionDataFetchers opinionDataFetchers;
    @Autowired
    CategoryDataFetchers categoryDataFetchers;
    @Autowired
    SubcategoryDataFetchers subcategoryDataFetchers;
    @Autowired
    ComplaintDataFetchers complaintDataFetchers;
    @Autowired
    ComplaintTypeDataFetchers complaintTypeDataFetchers;
    @Autowired
    DeliveryAddressDataFetchers deliveryAddressDataFetchers;
    @Autowired
    RoleDataFetchers roleDataFetchers;
    @Autowired
    ShipmentMethodDataFetchers shipmentMethodDataFetchers;
    @Autowired
    TaxCategoryDataFetchers taxCategoryDataFetchers;
    @Autowired
    SupplierDataFetchers supplierDataFetchers;
    @Autowired
    WarehouseDataFetchers warehouseDataFetchers;
    @Autowired
    ProductSupplierDataFetchers productSupplierDataFetchers;
    @Autowired
    ProductWarehouseDataFetchers productWarehouseDataFetchers;
    @Autowired
    VisitedDataFetchers visitedDataFetchers;

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
                        .dataFetcher("Complaint", complaintDataFetchers.getComplaintDataFetcher())
                        .dataFetcher("ComplaintType", complaintTypeDataFetchers.getComplaintTypeDataFetcher())
                        .dataFetcher("Role", roleDataFetchers.getRoleDataFetcher())
                        .dataFetcher("Product", productDataFetchers.getProductDataFetcher())
                        .dataFetcher("TaxCategory", taxCategoryDataFetchers.getTaxCategoryDataFetcher())
                        .dataFetcher("User", userDataFetchers.getUserDataFetcher())
                        .dataFetcher("Order", orderDataFetchers.getOrderDataFetcher())
                        .dataFetcher("Category", categoryDataFetchers.getCategoryDataFetcher())
                        .dataFetcher("Subcategory", subcategoryDataFetchers.getSubcategoryDataFetcher())
                        .dataFetcher("Supplier", supplierDataFetchers.getSupplierDataFetcher())
                        .dataFetcher("ProductSupplier", productSupplierDataFetchers.getProductSupplierDataFetcher())
                        .dataFetcher("Opinion", opinionDataFetchers.getOpinionDataFetcher())
                        .dataFetcher("Warehouse", warehouseDataFetchers.getWarehouseDataFetcher())
                        .dataFetcher("Visited", visitedDataFetchers.getVisitedDataFetcher())
                        .dataFetcher("ProductWarehouse", productWarehouseDataFetchers.getProductWarehouseDataFetcher())
                        .dataFetcher("DeliveryAddress", deliveryAddressDataFetchers.getDeliveryAddressDataFetcher())
                        .dataFetcher("ShipmentMethod", shipmentMethodDataFetchers.getShipmentMethodDataFetcher()))
                .type(newTypeWiring("Mutation")
                        .dataFetcher("createUser", userDataFetchers.createUserDataFetcher())
                        .dataFetcher("updateUser", userDataFetchers.updateUserDataFetcher())
                        .dataFetcher("deleteUser", userDataFetchers.deleteUserDataFetcher())
                        .dataFetcher("createProduct", productDataFetchers.createProductDataFetcher())
                        .dataFetcher("updateProduct", productDataFetchers.updateProductDataFetcher())
                        .dataFetcher("deleteProduct", productDataFetchers.deleteProductDataFetcher())
                        .dataFetcher("createOrder", orderDataFetchers.createOrderDataFetcher())
                        .dataFetcher("updateOrder", orderDataFetchers.updateOrderDataFetcher())
                        .dataFetcher("deleteOrder", orderDataFetchers.deleteOrderDataFetcher())
                        .dataFetcher("createOpinion", opinionDataFetchers.createOpinionDataFetcher())
                        .dataFetcher("updateOpinion", opinionDataFetchers.updateOpinionDataFetcher())
                        .dataFetcher("deleteOpinion", opinionDataFetchers.deleteOpinionDataFetcher())
                        .dataFetcher("createCategory", categoryDataFetchers.createCategoryDataFetcher())
                        .dataFetcher("updateCategory", categoryDataFetchers.updateCategoryDataFetcher())
                        .dataFetcher("deleteCategory", categoryDataFetchers.deleteCategoryDataFetcher())
                        .dataFetcher("createSubcategory", subcategoryDataFetchers.createSubcategoryDataFetcher())
                        .dataFetcher("updateSubcategory", subcategoryDataFetchers.updateSubcategoryDataFetcher())
                        .dataFetcher("deleteSubcategory", subcategoryDataFetchers.deleteSubcategoryDataFetcher())
                        .dataFetcher("createComplaint", complaintDataFetchers.createComplaintDataFetcher())
                        .dataFetcher("updateComplaint", complaintDataFetchers.updateComplaintDataFetcher())
                        .dataFetcher("deleteComplaint", complaintDataFetchers.deleteComplaintDataFetcher())
                        .dataFetcher("createComplaintType", complaintTypeDataFetchers.createComplaintTypeDataFetcher())
                        .dataFetcher("updateComplaintType", complaintTypeDataFetchers.updateComplaintTypeDataFetcher())
                        .dataFetcher("deleteComplaintType", complaintTypeDataFetchers.deleteComplaintTypeDataFetcher())
                        .dataFetcher("createDeliveryAddress", deliveryAddressDataFetchers.createDeliveryAddressDataFetcher())
                        .dataFetcher("updateDeliveryAddress", deliveryAddressDataFetchers.updateDeliveryAddressDataFetcher())
                        .dataFetcher("deleteDeliveryAddress", deliveryAddressDataFetchers.deleteDeliveryAddressDataFetcher())
                        .dataFetcher("createRole", roleDataFetchers.createRoleDataFetcher())
                        .dataFetcher("updateRole", roleDataFetchers.updateRoleDataFetcher())
                        .dataFetcher("deleteRole", roleDataFetchers.deleteRoleDataFetcher())
                        .dataFetcher("createShipmentMethod", shipmentMethodDataFetchers.createShipmentMethodDataFetcher())
                        .dataFetcher("updateShipmentMethod", shipmentMethodDataFetchers.updateShipmentMethodDataFetcher())
                        .dataFetcher("deleteShipmentMethod", shipmentMethodDataFetchers.deleteShipmentMethodDataFetcher())
                        .dataFetcher("createTaxCategory", taxCategoryDataFetchers.createTaxCategoryDataFetcher())
                        .dataFetcher("updateTaxCategory", taxCategoryDataFetchers.updateTaxCategoryDataFetcher())
                        .dataFetcher("deleteTaxCategory", taxCategoryDataFetchers.deleteTaxCategoryDataFetcher())
                        .dataFetcher("createVisited", visitedDataFetchers.createVisitedDataFetcher())
                        .dataFetcher("updateVisited", visitedDataFetchers.updateVisitedDataFetcher())
                        .dataFetcher("deleteVisited", visitedDataFetchers.deleteVisitedDataFetcher())
                        .dataFetcher("createSupplier", supplierDataFetchers.createSupplierDataFetcher())
                        .dataFetcher("updateSupplier", supplierDataFetchers.updateSupplierDataFetcher())
                        .dataFetcher("deleteSupplier", supplierDataFetchers.deleteSupplierDataFetcher())
                        .dataFetcher("createProductSupplier", productSupplierDataFetchers.createProductSupplierDataFetcher())
                        .dataFetcher("updateProductSupplier", productSupplierDataFetchers.updateProductSupplierDataFetcher())
                        .dataFetcher("deleteProductSupplier", productSupplierDataFetchers.deleteProductSupplierDataFetcher())
                        .dataFetcher("createWarehouse", warehouseDataFetchers.createWarehouseDataFetcher())
                        .dataFetcher("updateWarehouse", warehouseDataFetchers.updateWarehouseDataFetcher())
                        .dataFetcher("deleteWarehouse", warehouseDataFetchers.deleteWarehouseDataFetcher())
                        .dataFetcher("createProductWarehouse", productWarehouseDataFetchers.createProductWarehouseDataFetcher())
                        .dataFetcher("updateProductWarehouse", productWarehouseDataFetchers.updateProductWarehouseDataFetcher())
                        .dataFetcher("deleteProductWarehouse", productWarehouseDataFetchers.deleteProductWarehouseDataFetcher()))
                .build();
    }

}