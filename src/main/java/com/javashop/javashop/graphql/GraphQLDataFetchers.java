package com.javashop.javashop.graphql;

import com.javashop.javashop.model.*;
import com.javashop.javashop.repository.*;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;

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

    public DataFetcher getDeliveryAddressDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  deliveryAddressRepository.findById(id);
        };
    }

    public DataFetcher createUserDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, String> l = dataFetchingEnvironment.getArgument("input");
            String login = l.get("login");
            String password = l.get("password");
            String email = l.get("email");
            String name = l.get("name");
            String surname = l.get("surname");
            String address = l.get("address");
            String birthDateStr = l.get("birthDate");
            Date birthDate = new SimpleDateFormat("dd/MM/yyyy").parse(birthDateStr);
            String telephone = l.get("telephone");

            return userRepository.save(new User(login, password, email, name, surname, address, birthDate, telephone));
        };
    }

    public DataFetcher createProductDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");


            String name = (String) l.get("name");
            Integer price = (Integer) l.get("price");
            Integer discountPrice = (Integer)  l.get("discountPrice");
            Integer noAvailable =(Integer)  l.get("noAvailable");
            String description = (String) l.get("description");
            String imagePath = (String) l.get("imagePath");
            String taxCategoryID = (String) l.get("taxCategoryID");
            TaxCategory taxCategory = taxCategoryRepository.getOne(Integer.parseInt(taxCategoryID));
            //Product product = productRepository.getOne(1);
            //product.setCategories(product.getCategories().add(categoryRepository.getOne(1)));
            return productRepository.save(new Product(name, price, discountPrice, noAvailable, description, imagePath, taxCategory));
        };
    }

    public DataFetcher createOrderDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer price = (Integer) l.get("price");
            String dateStr = (String) l.get("date");
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);

            return orderRepository.save(new Order(date, price));
        };
    }

    public DataFetcher createOpinionDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer mark = (Integer) l.get("mark");
            String text = (String) l.get("text");
            String dateStr = (String) l.get("date");
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);

            return opinionRepository.save(new Opinion(mark, text, date));
        };
    }


    public DataFetcher createCategoryDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            String name = (String) l.get("name");

            return categoryRepository.save(new Category(name));
        };
    }

    public DataFetcher createSubcategoryDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            String name = (String) l.get("name");

            return subcategoryRepository.save(new Subcategory(name));
        };
    }

    public DataFetcher createComplaintDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            String dateStr = (String) l.get("date");
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);

            return complaintRepository.save(new Complaint(date));
        };
    }

    public DataFetcher createComplaintTypeDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            String name = (String) l.get("name");

            return complaintTypeRepository.save(new ComplaintType(name));
        };
    }

    public DataFetcher createDeliveryAddressDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            String name = (String) l.get("name");
            String surname = (String) l.get("surname");
            String city = (String) l.get("city");
            String street = (String) l.get("street");
            String buildingNumber = (String) l.get("buildingNumber");
            String postCode = (String) l.get("postCode");

            return deliveryAddressRepository.save(new DeliveryAddress(name,surname,city,street,buildingNumber,postCode));
        };
    }

    public DataFetcher createRoleDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            String name = (String) l.get("name");

            return roleRepository.save(new Role(name));
        };
    }

    public DataFetcher createShipmentMethodDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            String name = (String) l.get("name");
            Integer price = (Integer) l.get("price");
            Integer freeThreshold = (Integer) l.get("freeThreshold");
            return shipmentMethodRepository.save(new ShipmentMethod(name,price,freeThreshold));
        };
    }

    public DataFetcher createTaxCategoryDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer taxRate = (Integer) l.get("taxRate");
            String name = (String) l.get("name");
            return taxCategoryRepository.save(new TaxCategory(name, taxRate));
        };
    }

    public DataFetcher createSupplierDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            String name = (String) l.get("name");
            String telephone = (String) l.get("telephone");

            return supplierRepository.save(new Supplier(name, telephone));
        };
    }


}