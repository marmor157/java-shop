package com.javashop.javashop.graphql;

import com.javashop.javashop.model.*;
import com.javashop.javashop.repository.*;
import com.sun.xml.bind.v2.util.QNameMap;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

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

    public DataFetcher getProductDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            Product product = productRepository.getOne(id);
            //List<Category> category = product.getCategories();
            return  product;
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
