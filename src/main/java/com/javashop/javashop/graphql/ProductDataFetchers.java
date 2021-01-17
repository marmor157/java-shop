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
//            Integer id = (Integer) filter.get("id");
//            String name = (String) filter.get("name");
//            Integer price = (Integer) filter.get("price");
//            Integer price_lt = (Integer) filter.get("price_lt");
//            Integer price_lte = (Integer) filter.get("price_lte");
//            Integer price_gt = (Integer) filter.get("price_gt");
//            Integer price_gte = (Integer) filter.get("price_gte");
//            Integer discountPrice = (Integer) filter.get("discountPrice");
//            Integer discountPrice_lt = (Integer) filter.get("discountPrice_lt");
//            Integer discountPrice_lte = (Integer) filter.get("discountPrice_lte");
//            Integer discountPrice_gt = (Integer) filter.get("discountPrice_gt");
//            Integer discountPrice_gte = (Integer) filter.get("discountPrice_gte");
//            Integer noAvailable = (Integer) filter.get("noAvailable");
//            Integer noAvailable_lt = (Integer) filter.get("noAvailable_lt");
//            Integer noAvailable_lte = (Integer) filter.get("noAvailable_lte");
//            Integer noAvailable_gt = (Integer) filter.get("noAvailable_gt");
//            Integer noAvailable_gte = (Integer) filter.get("noAvailable_gte");
//            String description = (String) filter.get("description");
//            String imagePath = (String) filter.get("imagePath");
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
                    Predicate<Product> predicate = product -> product.getSubcategories().contains(subcategoryRepository.getOne(subcategoryID));
                    return productRepository.findAll(PageRequest.of(page,perPage, Sort.by(order,sortField))).filter(predicate).toList();
                }
                else if(filter.containsKey("categoryID")){
                    final Integer categoryID = Integer.parseInt((String) filter.get("categoryID"));
                    Predicate<Product> predicate = product -> product.getCategories().contains(categoryRepository.getOne(categoryID));
                    return productRepository.findAll(PageRequest.of(page,perPage, Sort.by(order,sortField))).toList();
                }
            }
            return productRepository.findAll(PageRequest.of(page,perPage, Sort.by(order,sortField))).toList();
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
            List<String> categoryIDs = (List<String>) l.get("categoryIDs");
            List<String> subcategoryIDs = (List<String>) l.get("subcategoryIDs");
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
            if(l.containsKey("categoryIDs")){
                List<String> categoryIDs = (List<String>) l.get("categoryIDs");
                for (String catID: categoryIDs){
                    product.getCategories().add(categoryRepository.getOne(Integer.parseInt(catID)));
                    categoryRepository.getOne(Integer.parseInt(catID)).getProducts().add(product);
                }
            }
            if(l.containsKey("subcategoryIDs")){
                List<String> subcategoryIDs = (List<String>) l.get("subcategoryIDs");
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
            productRepository.delete(product);

            return product;
        };
    }
}
