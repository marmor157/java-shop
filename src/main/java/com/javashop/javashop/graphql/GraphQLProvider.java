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
                        .dataFetcher("allComplaint", complaintDataFetchers.getAllComplaintDataFetcher())
                        .dataFetcher("_allComplaintMeta", complaintDataFetchers.getAllComplaintMetaDataFetcher())
                        .dataFetcher("ComplaintType", complaintTypeDataFetchers.getComplaintTypeDataFetcher())
                        .dataFetcher("allComplaintType", complaintTypeDataFetchers.getAllComplaintTypeDataFetcher())
                        .dataFetcher("_allComplaintTypeMeta", complaintTypeDataFetchers.getAllComplaintTypeMetaDataFetcher())
                        .dataFetcher("Role", roleDataFetchers.getRoleDataFetcher())
                        .dataFetcher("allRole", roleDataFetchers.getAllRoleDataFetcher())
                        .dataFetcher("_allRoleMeta", roleDataFetchers.getAllRoleMetaDataFetcher())
                        .dataFetcher("Product", productDataFetchers.getProductDataFetcher())
                        .dataFetcher("allProduct", productDataFetchers.getAllProductDataFetcher())
                        .dataFetcher("_allProductMeta", productDataFetchers.getAllProductMetaDataFetcher())
                        .dataFetcher("TaxCategory", taxCategoryDataFetchers.getTaxCategoryDataFetcher())
                        .dataFetcher("allTaxCategory", taxCategoryDataFetchers.getAllTaxCategoryDataFetcher())
                        .dataFetcher("_allTaxCategory", taxCategoryDataFetchers.getAllTaxCategoryMetaDataFetcher())
                        .dataFetcher("User", userDataFetchers.getUserDataFetcher())
                        .dataFetcher("allUser", userDataFetchers.getAllUserDataFetcher())
                        .dataFetcher("_allUserMeta", userDataFetchers.getAllUserMetaDataFetcher())
                        .dataFetcher("Order", orderDataFetchers.getOrderDataFetcher())
                        .dataFetcher("allOrder", orderDataFetchers.getAllOrderDataFetcher())
                        .dataFetcher("allOrderMeta", orderDataFetchers.getAllOrderMetaDataFetcher())
                        .dataFetcher("Category", categoryDataFetchers.getCategoryDataFetcher())
                        .dataFetcher("allCategory", categoryDataFetchers.getAllCategoryDataFetcher())
                        .dataFetcher("_allCategoryMeta", categoryDataFetchers.getAllCategoryMetaDataFetcher())
                        .dataFetcher("Subcategory", subcategoryDataFetchers.getSubcategoryDataFetcher())
                        .dataFetcher("allSubcategory", subcategoryDataFetchers.getAllSubcategoryDataFetcher())
                        .dataFetcher("_allSubcategoryMeta", subcategoryDataFetchers.getAllSubcategoryMetaDataFetcher())
                        .dataFetcher("Supplier", supplierDataFetchers.getSupplierDataFetcher())
                        .dataFetcher("allSupplier", supplierDataFetchers.getAllSupplierDataFetcher())
                        .dataFetcher("_allSupplierMeta", supplierDataFetchers.getAllSupplierMetaDataFetcher())
                        .dataFetcher("ProductSupplier", productSupplierDataFetchers.getProductSupplierDataFetcher())
                        .dataFetcher("allProductSupplier", productSupplierDataFetchers.getAllProductSupplierDataFetcher())
                        .dataFetcher("_allProductSupplierMeta", productSupplierDataFetchers.getAllProductSupplierMetaDataFetcher())
                        .dataFetcher("Opinion", opinionDataFetchers.getOpinionDataFetcher())
                        .dataFetcher("allOpinion", opinionDataFetchers.getAllOpinionDataFetcher())
                        .dataFetcher("_allOpinionMeta", opinionDataFetchers.getAllOpinionMetaDataFetcher())
                        .dataFetcher("Warehouse", warehouseDataFetchers.getWarehouseDataFetcher())
                        .dataFetcher("allWarehouse", warehouseDataFetchers.getAllWarehouseDataFetcher())
                        .dataFetcher("_allWarehouseMeta", warehouseDataFetchers.getAllWarehouseMetaDataFetcher())
                        .dataFetcher("Visited", visitedDataFetchers.getVisitedDataFetcher())
                        .dataFetcher("allVisited", visitedDataFetchers.getAllVisitedDataFetcher())
                        .dataFetcher("_allVisitedMeta", visitedDataFetchers.getAllVisitedMetaDataFetcher())
                        .dataFetcher("ProductWarehouse", productWarehouseDataFetchers.getProductWarehouseDataFetcher())
                        .dataFetcher("allProductWarehouse", productWarehouseDataFetchers.getAllProductWarehouseDataFetcher())
                        .dataFetcher("_allProductWarehouseMeta", productWarehouseDataFetchers.getAllProductWarehouseMetaDataFetcher())
                        .dataFetcher("DeliveryAddress", deliveryAddressDataFetchers.getDeliveryAddressDataFetcher())
                        .dataFetcher("allDeliveryAddress", deliveryAddressDataFetchers.getAllDeliveryAddressDataFetcher())
                        .dataFetcher("_allDeliveryAddressMeta", deliveryAddressDataFetchers.getAllDeliveryAddressMetaDataFetcher())
                        .dataFetcher("ShipmentMethod", shipmentMethodDataFetchers.getShipmentMethodDataFetcher())
                        .dataFetcher("allShipmentMethod", shipmentMethodDataFetchers.getAllShipmentMethodDataFetcher())
                        .dataFetcher("_allShipmentMethodMeta", shipmentMethodDataFetchers.getAllShipmentMethodMetaDataFetcher()))
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