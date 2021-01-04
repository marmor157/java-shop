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
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");
            String login = (String) l.get("login");
            String password = (String) l.get("password");
            String email = (String) l.get("email");
            String name = (String) l.get("name");
            String surname = (String) l.get("surname");
            String address = (String) l.get("address");
            String birthDateStr = (String) l.get("birthDate");
            Date birthDate = new SimpleDateFormat("dd/MM/yyyy").parse(birthDateStr);
            String telephone = (String) l.get("telephone");

            return userRepository.save(new User(login, password, email, name, surname, address, birthDate, telephone));
        };
    }
    public DataFetcher updateUserDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");
            Integer id =Integer.parseInt((String) l.get("id"));
            User user = userRepository.getOne(id);
            if(l.containsKey("name")){
                String name = (String) l.get("name");
                user.setName(name);
            }
            if(l.containsKey("surname")){
                String surname = (String) l.get("surname");
               user.setSurname(surname);
            }
            if(l.containsKey("login")){
                String login = (String) l.get("login");
                user.setLogin(login);
            }
            if(l.containsKey("password")){
                String password = (String) l.get("password");
                user.setPassword(password);
            }
            if(l.containsKey("email")){
                String email = (String) l.get("email");
                user.setEmail(email);
            }
            if(l.containsKey("address")){
                String address = (String) l.get("address");
                user.setAddress(address);
            }
            if(l.containsKey("birthDate")){
                String birthDateStr = (String) l.get("birthDate");
                Date birthDate = new SimpleDateFormat("dd/MM/yyyy").parse(birthDateStr);
                user.setBirthDate(birthDate);
            }
            if(l.containsKey("telephone")){
                String telephone = (String) l.get("telephone");
                user.setTelephone(telephone);
            }
            return userRepository.save(user);
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


    public DataFetcher updateProductDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");


            Integer id =Integer.parseInt((String) l.get("id"));
            Product product = productRepository.getOne(id);
            if(l.containsKey("name")){
                String name = (String) l.get("name");
                product.setName(name);
            }
            if(l.containsKey("price")){
                Integer price = (Integer) l.get("price");
                product.setPrice(price);
            }
            if(l.containsKey("discountPrice")){
                Integer discountPrice = (Integer)  l.get("discountPrice");
                product.setDiscountPrice(discountPrice);
            }
            if(l.containsKey("noAvailable")){
                Integer noAvailable =(Integer)  l.get("noAvailable");
                product.setNoAvailable(noAvailable);
            }
            if(l.containsKey("description")){
                String description = (String) l.get("description");
                product.setDescription(description);
            }
            if(l.containsKey("imagePath")){
                String imagePath = (String) l.get("imagePath");
                product.setImagePath(imagePath);
            }
            if(l.containsKey("taxCategoryID")){
                String taxCategoryID = (String) l.get("taxCategoryID");
                TaxCategory taxCategory = taxCategoryRepository.getOne(Integer.parseInt(taxCategoryID));
                product.setTaxCategory(taxCategory);
            }

            return productRepository.save(product);
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

    public DataFetcher updateOrderDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer id =Integer.parseInt((String) l.get("id"));
            Order order = orderRepository.getOne(id);
            if(l.containsKey("price")){
                Integer price = (Integer) l.get("price");
                order.setPrice(price);
            }
            if(l.containsKey("date")){
                String dateStr = (String) l.get("date");
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
                order.setDate(date);
            }

            return orderRepository.save(order);
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

    public DataFetcher updateOpinionDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer id =Integer.parseInt((String) l.get("id"));
            Opinion opinion = opinionRepository.getOne(id);
            if(l.containsKey("mark")){
                Integer mark = (Integer) l.get("mark");
                opinion.setMark(mark);
            }
            if(l.containsKey("text")){
                String text = (String) l.get("text");
                opinion.setText(text);
            }
            if(l.containsKey("text")){
                String text = (String) l.get("text");
                opinion.setText(text);
            }
            if(l.containsKey("date")){
                String dateStr = (String) l.get("date");
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
                opinion.setDate(date);
            }

            return opinionRepository.save(opinion);
        };
    }


    public DataFetcher createCategoryDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            String name = (String) l.get("name");

            return categoryRepository.save(new Category(name));
        };
    }

    public DataFetcher updateCategoryDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer id =Integer.parseInt((String) l.get("id"));
            Category category = categoryRepository.getOne(id);
            if(l.containsKey("name")){
                String name = (String) l.get("name");
                category.setName(name);
            }

            return categoryRepository.save(category);
        };
    }

    public DataFetcher createSubcategoryDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            String name = (String) l.get("name");

            return subcategoryRepository.save(new Subcategory(name));
        };
    }

    public DataFetcher updateSubcategoryDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer id =Integer.parseInt((String) l.get("id"));
            Subcategory subcategory = subcategoryRepository.getOne(id);
            if(l.containsKey("name")){
                String name = (String) l.get("name");
                subcategory.setName(name);
            }

            return subcategoryRepository.save(subcategory);
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

    public DataFetcher updateComplaintDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer id =Integer.parseInt((String) l.get("id"));
            Complaint complaint = complaintRepository.getOne(id);
            if(l.containsKey("date")){
                String dateStr = (String) l.get("date");
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
                complaint.setDate(date);
            }

            return complaintRepository.save(complaint);
        };
    }

    public DataFetcher createComplaintTypeDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            String name = (String) l.get("name");

            return complaintTypeRepository.save(new ComplaintType(name));
        };
    }

    public DataFetcher updateComplaintTypeDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer id =Integer.parseInt((String) l.get("id"));
            ComplaintType complaintType = complaintTypeRepository.getOne(id);
            if(l.containsKey("name")){
                String name = (String) l.get("name");
                complaintType.setName(name);
            }

            return complaintTypeRepository.save(complaintType);
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

    public DataFetcher updateDeliveryAddressDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer id =Integer.parseInt((String) l.get("id"));
            DeliveryAddress deliveryAddress = deliveryAddressRepository.getOne(id);
            if(l.containsKey("name")){
                String name = (String) l.get("name");
                deliveryAddress.setName(name);
            }
            if(l.containsKey("surname")){
                String surname = (String) l.get("surname");
                deliveryAddress.setSurname(surname);
            }
            if(l.containsKey("city")){
                String city = (String) l.get("city");
                deliveryAddress.setCity(city);
            }
            if(l.containsKey("street")){
                String street = (String) l.get("street");
                deliveryAddress.setStreet(street);
            }
            if(l.containsKey("buildingNumber")){
                String buildingNumber = (String) l.get("buildingNumber");
                deliveryAddress.setBuildingNumber(buildingNumber);
            }
            if(l.containsKey("postCode")){
                String postCode = (String) l.get("postCode");
                deliveryAddress.setPostCode(postCode);
            }

            return deliveryAddressRepository.save(deliveryAddress);
        };
    }

    public DataFetcher createRoleDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            String name = (String) l.get("name");

            return roleRepository.save(new Role(name));
        };
    }

    public DataFetcher updateRoleDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer id =Integer.parseInt((String) l.get("id"));
            Role role = roleRepository.getOne(id);
            if(l.containsKey("name")){
                String name = (String) l.get("name");
                role.setName(name);
            }

            return roleRepository.save(role);
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

    public DataFetcher updateShipmentMethodDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");


            Integer id =Integer.parseInt((String) l.get("id"));
            ShipmentMethod shipmentMethod = shipmentMethodRepository.getOne(id);
            if(l.containsKey("name")){
                String name = (String) l.get("name");
                shipmentMethod.setName(name);
            }
            if(l.containsKey("price")){
                Integer price = (Integer) l.get("price");
                shipmentMethod.setPrice(price);
            }
            if(l.containsKey("freeThreshold")){
                Integer freeThreshold = (Integer) l.get("freeThreshold");
                shipmentMethod.setFreeThreshold(freeThreshold);
            }

            return shipmentMethodRepository.save(shipmentMethod);
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

    public DataFetcher updateTaxCategoryDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer id =Integer.parseInt((String) l.get("id"));
            TaxCategory taxCategory = taxCategoryRepository.getOne(id);
            if(l.containsKey("name")){
                String name = (String) l.get("name");
                taxCategory.setName(name);
            }
            if(l.containsKey("taxRate")){
                Integer taxRate = (Integer) l.get("taxRate");
                taxCategory.setTaxRate(taxRate);
            }

            return taxCategoryRepository.save(taxCategory);
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

    public DataFetcher updateSupplierDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer id =Integer.parseInt((String) l.get("id"));
            Supplier supplier = supplierRepository.getOne(id);
            if(l.containsKey("name")){
                String name = (String) l.get("name");
                supplier.setName(name);
            }
            if(l.containsKey("telephone")){
                String telephone = (String) l.get("telephone");
                supplier.setTelephone(telephone);
            }

            return supplierRepository.save(supplier);
        };
    }
}