package com.javashop.javashop.graphql;

import com.javashop.javashop.model.Product;
import com.javashop.javashop.repository.*;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GraphQLDataFetchers {


    @Autowired
    private ComplaintTypeRepository complaintTypeRepository;
    @Autowired
    private ComplaintRepository complaintRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TaxCategoryRepository taxCategoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SubcategoryRepository subcategoryRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private OpinionRepository opinionRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private ShipmentMethodRepository shipmentMethodRepository;

    public DataFetcher getComplaintDataFetcher() {
        return dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return complaintRepository.findById(id);
        };
    }


    public DataFetcher getComplaintTypeDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  complaintTypeRepository.findById(id);
        };
    }

    public DataFetcher getRoleDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  roleRepository.findById(id);
        };
    }

    public DataFetcher getProductDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            Optional<Product> tak = productRepository.findById(id);
            return  productRepository.findById(id);
        };
    }

    public DataFetcher getTaxCategoryDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  taxCategoryRepository.findById(id);
        };
    }

    public DataFetcher getUserDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  userRepository.findById(id);
        };
    }

    public DataFetcher getOrderDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  orderRepository.findById(id);
        };
    }

    public DataFetcher getCategoryDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  categoryRepository.findById(id);
        };
    }

    public DataFetcher getSubcategoryDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  subcategoryRepository.findById(id);
        };
    }

    public DataFetcher getSupplierDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  supplierRepository.findById(id);
        };
    }

    public DataFetcher getOpinionDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  opinionRepository.findById(id);
        };
    }

    public DataFetcher getWarehouseDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  warehouseRepository.findById(id);
        };
    }

    public DataFetcher getShipmentMethodDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  shipmentMethodRepository.findById(id);
        };
    }
}