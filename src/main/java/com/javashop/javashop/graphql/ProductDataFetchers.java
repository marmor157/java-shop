package com.javashop.javashop.graphql;

import com.javashop.javashop.model.*;
import com.javashop.javashop.repository.*;
import com.javashop.javashop.service.MailService;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class ProductDataFetchers {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TaxCategoryRepository taxCategoryRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private SubcategoryRepository subcategoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailService mailService;


    public DataFetcher getProductDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  productRepository.findById(id);
        };
    }

    public DataFetcher getAllProductsDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer page = dataFetchingEnvironment.getArgument("page");
            page = page == null ? 0: page;
            Integer perPage = dataFetchingEnvironment.getArgument("perPage");
            perPage = perPage == null ? Integer.MAX_VALUE: perPage;
            String sortField = dataFetchingEnvironment.getArgument("sortField");
            String sortOrder = dataFetchingEnvironment.getArgument("sortOrder");
            LinkedHashMap<String, Object> filter = dataFetchingEnvironment.getArgument("filter");

            Sort.Direction order = Sort.Direction.DESC;;
            if(sortOrder!=null && sortOrder.toUpperCase().equals("DESC")){
                order = Sort.Direction.DESC;
            }
            else{
                order = Sort.Direction.ASC;
            }

            if(sortField==null) sortField = "";
            if(sortField!=null && sortField.equals("")){
                sortField = "id";
            }
            if(filter!=null){
                if(filter.containsKey("ids") && filter.containsKey("taxCategoryID")){
                    final List<String> ids = (List<String>) filter.get("ids");
                    Integer taxCategoryID = Integer.parseInt((String) filter.get("taxCategoryID"));
                    List<Integer> idsInt = new ArrayList<>();
                    for(String s : ids) idsInt.add(Integer.valueOf(s));
                    return  productRepository.findByTaxCategoryIdAndIdIn(taxCategoryID,idsInt, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
                else if(filter.containsKey("ids")){
                    final List<String> ids = (List<String>) filter.get("ids");
                    List<Integer> idsInt = new ArrayList<>();
                    for(String s : ids) idsInt.add(Integer.valueOf(s));
                    return  productRepository.findByIdIn(idsInt, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
                else if(filter.containsKey("taxCategoryID")){
                    Integer taxCategoryID = Integer.parseInt((String) filter.get("taxCategoryID"));
                    return  productRepository.findByTaxCategoryId(taxCategoryID, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
                else if(filter.containsKey("subcategoryID")){
                    final Integer subcategoryID = Integer.parseInt((String) filter.get("subcategoryID"));

                    return productRepository.findBySubcategories_Id(subcategoryID,PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
                else if(filter.containsKey("categoryID")){
                    final Integer categoryID = Integer.parseInt((String) filter.get("categoryID"));
                    return productRepository.findByCategories_Id(categoryID, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
                else if(filter.containsKey("orderID")){
                    final Integer orderID = Integer.parseInt((String) filter.get("orderID"));
                    return productRepository.findByOrders_Id(orderID, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
                else if(filter.containsKey("opinionID")){
                    final Integer opinionID = Integer.parseInt((String) filter.get("opinionID"));
                    return productRepository.findByOpinions_Id(opinionID, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
                else if(filter.containsKey("visitedID")){
                    final Integer visitedID = Integer.parseInt((String) filter.get("visitedID"));
                    return productRepository.findByVisited_Id(visitedID, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
                else if(filter.containsKey("wishlistID")){
                    final Integer wishlistID = Integer.parseInt((String) filter.get("wishlistID"));
                    return productRepository.findByWishlist_Id(wishlistID, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
                else if(filter.containsKey("warehouseID")){
                    final Integer warehouseID = Integer.parseInt((String) filter.get("warehouseID"));
                    return productRepository.findByWarehouses_Id(warehouseID, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
                else if(filter.containsKey("supplierID")){
                    final Integer supplierID = Integer.parseInt((String) filter.get("supplierID"));
                    return productRepository.findBySuppliers_Id(supplierID, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }

            }
            return productRepository.findAll(PageRequest.of(page,perPage, Sort.by(order,sortField)));
        };
    }

    public DataFetcher getAllProductsMetaDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> filter = dataFetchingEnvironment.getArgument("filter");

            if(filter!=null){
                if(filter.containsKey("ids")){
                    final List<String> ids = (List<String>) filter.get("ids");
                    List<Integer> idsInt = new ArrayList<>();
                    for(String s : ids) idsInt.add(Integer.valueOf(s));
                    return new Metadata(productRepository.countByIdIn(idsInt));
                }
                else if(filter.containsKey("orderID")){
                    final Integer orderID = Integer.parseInt((String) filter.get("orderID"));
                    return new Metadata(productRepository.countByOrders_Id(orderID));
                }
                else if(filter.containsKey("opinionID")){
                    final Integer opinionID = Integer.parseInt((String) filter.get("opinionID"));
                    return new Metadata(productRepository.countByOpinions_Id(opinionID));
                }
                else if(filter.containsKey("visitedID")){
                    final Integer visitedID = Integer.parseInt((String) filter.get("visitedID"));
                    return new Metadata(productRepository.countByVisited_Id(visitedID));
                }
                else if(filter.containsKey("wishlistID")){
                    final Integer wishlistID = Integer.parseInt((String) filter.get("wishlistID"));
                    return new Metadata(productRepository.countByWishlist_Id(wishlistID));
                }
                else if(filter.containsKey("warehouseID")){
                    final Integer warehouseID = Integer.parseInt((String) filter.get("warehouseID"));
                    return new Metadata(productRepository.countByWarehouses_Id(warehouseID));
                }
                else if(filter.containsKey("supplierID")){
                    final Integer supplierID = Integer.parseInt((String) filter.get("supplierID"));
                    return new Metadata(productRepository.countBySuppliers_Id(supplierID));
                }
            }
            Metadata metadata = new Metadata(productRepository.count());
            return metadata;
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
            Integer taxCategoryID = Integer.parseInt((String) l.get("taxCategoryID"));
            List<String> categoryIDs = (List<String>) l.get("categoriesIDs");
            List<String> subcategoryIDs = (List<String>) l.get("subcategoriesIDs");
            TaxCategory taxCategory = taxCategoryRepository.getOne(taxCategoryID);
            Product product = new Product(name, price, discountPrice, noAvailable, description, imagePath, taxCategory);
            for (String catID: categoryIDs){
                product.getCategories().add(categoryRepository.getOne(Integer.parseInt(catID)));
                categoryRepository.getOne(Integer.parseInt(catID)).getProducts().add(product);
            }
            for (String subcatID: subcategoryIDs){
                product.getSubcategories().add(subcategoryRepository.getOne(Integer.parseInt(subcatID)));
                subcategoryRepository.getOne(Integer.parseInt(subcatID)).getProducts().add(product);
            }

            return productRepository.save(product);
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
                if(discountPrice < product.getPrice()){
                    List<User> users = userRepository.findAll();
                    for(User u : users){
                        if(u.getWishlist().contains(product)) {
                            String text = u.getName() + "!\nProdukt z twojej list życzeń "+ product.getName()+" jest na promcji! Sprawdź sam!\n Stara cena:" + product.getPrice() +"\nNowa cena: "+ discountPrice;
                            mailService.sendMail(u.getEmail(), "Promocja w sklepie komputerowym!", text, false);
                        }
                    }
                }
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
            if(l.containsKey("categoriesIDs")){
                List<String> categoryIDs = (List<String>) l.get("categoriesIDs");
                for (String catID: categoryIDs){
                    product.getCategories().add(categoryRepository.getOne(Integer.parseInt(catID)));
                    categoryRepository.getOne(Integer.parseInt(catID)).getProducts().add(product);
                }
            }
            if(l.containsKey("subcategoriesIDs")){
                List<String> subcategoryIDs = (List<String>) l.get("subcategoriesIDs");
                for (String subcatID: subcategoryIDs){
                    product.getSubcategories().add(subcategoryRepository.getOne(Integer.parseInt(subcatID)));
                    subcategoryRepository.getOne(Integer.parseInt(subcatID)).getProducts().add(product);
                }
            }
            return productRepository.save(product);
        };
    }

    public DataFetcher deleteProductDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id =Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            Product product = productRepository.getOne(id);
            product.setDeleteDate(LocalDate.now());

            return productRepository.save(product);
        };
    }
}
