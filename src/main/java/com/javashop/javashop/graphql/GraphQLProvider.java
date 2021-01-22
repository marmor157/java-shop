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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;


import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Component
public class GraphQLProvider {

    private GraphQL graphQL;
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
    @Autowired
    LoginDataFetcher loginDataFetcher;
    @Autowired
    WishlistDataFetchers wishlistDataFetchers;

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
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
                        .dataFetcher("allComplaints", complaintDataFetchers.getAllComplaintsDataFetcher())
                        .dataFetcher("_allComplaintsMeta", complaintDataFetchers.getAllComplaintsMetaDataFetcher())
                        .dataFetcher("ComplaintType", complaintTypeDataFetchers.getComplaintTypeDataFetcher())
                        .dataFetcher("allComplaintTypes", complaintTypeDataFetchers.getAllComplaintTypesDataFetcher())
                        .dataFetcher("_allComplaintTypesMeta", complaintTypeDataFetchers.getAllComplaintTypesMetaDataFetcher())
                        .dataFetcher("Role", roleDataFetchers.getRoleDataFetcher())
                        .dataFetcher("allRoles", roleDataFetchers.getAllRolesDataFetcher())
                        .dataFetcher("_allRolesMeta", roleDataFetchers.getAllRolesMetaDataFetcher())
                        .dataFetcher("Product", productDataFetchers.getProductDataFetcher())
                        .dataFetcher("allProducts", productDataFetchers.getAllProductsDataFetcher())
                        .dataFetcher("_allProductsMeta", productDataFetchers.getAllProductsMetaDataFetcher())
                        .dataFetcher("TaxCategory", taxCategoryDataFetchers.getTaxCategoryDataFetcher())
                        .dataFetcher("allTaxCategories", taxCategoryDataFetchers.getAllTaxCategoriesDataFetcher())
                        .dataFetcher("_allTaxCategoriesMeta", taxCategoryDataFetchers.getAllTaxCategoriesMetaDataFetcher())
                        .dataFetcher("User", userDataFetchers.getUserDataFetcher())
                        .dataFetcher("allUsers", userDataFetchers.getAllUsersDataFetcher())
                        .dataFetcher("_allUsersMeta", userDataFetchers.getAllUsersMetaDataFetcher())
                        .dataFetcher("Order", orderDataFetchers.getOrderDataFetcher())
                        .dataFetcher("allOrders", orderDataFetchers.getAllOrdersDataFetcher())
                        .dataFetcher("_allOrdersMeta", orderDataFetchers.getAllOrdersMetaDataFetcher())
                        .dataFetcher("Category", categoryDataFetchers.getCategoryDataFetcher())
                        .dataFetcher("allCategories", categoryDataFetchers.getAllCategoriesDataFetcher())
                        .dataFetcher("_allCategoriesMeta", categoryDataFetchers.getAllCategoriesMetaDataFetcher())
                        .dataFetcher("Subcategory", subcategoryDataFetchers.getSubcategoryDataFetcher())
                        .dataFetcher("allSubcategories", subcategoryDataFetchers.getAllSubcategoriesDataFetcher())
                        .dataFetcher("_allSubcategoriesMeta", subcategoryDataFetchers.getAllSubcategoriesMetaDataFetcher())
                        .dataFetcher("Supplier", supplierDataFetchers.getSupplierDataFetcher())
                        .dataFetcher("allSuppliers", supplierDataFetchers.getAllSuppliersDataFetcher())
                        .dataFetcher("_allSuppliersMeta", supplierDataFetchers.getAllSuppliersMetaDataFetcher())
                        .dataFetcher("ProductSupplier", productSupplierDataFetchers.getProductSupplierDataFetcher())
                        .dataFetcher("allProductSuppliers", productSupplierDataFetchers.getAllProductSuppliersDataFetcher())
                        .dataFetcher("_allProductSuppliersMeta", productSupplierDataFetchers.getAllProductSuppliersMetaDataFetcher())
                        .dataFetcher("Opinion", opinionDataFetchers.getOpinionDataFetcher())
                        .dataFetcher("allOpinions", opinionDataFetchers.getAllOpinionsDataFetcher())
                        .dataFetcher("_allOpinionsMeta", opinionDataFetchers.getAllOpinionsMetaDataFetcher())
                        .dataFetcher("Warehouse", warehouseDataFetchers.getWarehouseDataFetcher())
                        .dataFetcher("allWarehouses", warehouseDataFetchers.getAllWarehousesDataFetcher())
                        .dataFetcher("_allWarehousesMeta", warehouseDataFetchers.getAllWarehousesMetaDataFetcher())
                        .dataFetcher("Visited", visitedDataFetchers.getVisitedDataFetcher())
                        .dataFetcher("allVisiteds", visitedDataFetchers.getAllVisitedDataFetcher())
                        .dataFetcher("_allVisitedsMeta", visitedDataFetchers.getAllVisitedMetaDataFetcher())
                        .dataFetcher("ProductWarehouse", productWarehouseDataFetchers.getProductWarehouseDataFetcher())
                        .dataFetcher("allProductWarehouses", productWarehouseDataFetchers.getAllProductWarehousesDataFetcher())
                        .dataFetcher("_allProductWarehousesMeta", productWarehouseDataFetchers.getAllProductWarehousesMetaDataFetcher())
                        .dataFetcher("DeliveryAddress", deliveryAddressDataFetchers.getDeliveryAddressDataFetcher())
                        .dataFetcher("allDeliveryAddresses", deliveryAddressDataFetchers.getAllDeliveryAddressesDataFetcher())
                        .dataFetcher("_allDeliveryAddressesMeta", deliveryAddressDataFetchers.getAllDeliveryAddressesMetaDataFetcher())
                        .dataFetcher("ShipmentMethod", shipmentMethodDataFetchers.getShipmentMethodDataFetcher())
                        .dataFetcher("allShipmentMethods", shipmentMethodDataFetchers.getAllShipmentMethodsDataFetcher())
                        .dataFetcher("_allShipmentMethodsMeta", shipmentMethodDataFetchers.getAllShipmentMethodsMetaDataFetcher())
                        .dataFetcher("getUserWishlist", wishlistDataFetchers.getUserWishlist())
                )
                .type(newTypeWiring("Mutation")
                        .dataFetcher("login", loginDataFetcher.getLoginDataFetcher())
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
                        .dataFetcher("deleteProductWarehouse", productWarehouseDataFetchers.deleteProductWarehouseDataFetcher())
                        .dataFetcher("deleteProductFromWishlist", wishlistDataFetchers.deleteProductFromWishlist())
                )
                .build();
    }

}