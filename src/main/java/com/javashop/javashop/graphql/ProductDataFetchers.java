package com.javashop.javashop.graphql;

import com.javashop.javashop.model.Product;
import com.javashop.javashop.model.TaxCategory;
import com.javashop.javashop.repository.ProductRepository;
import com.javashop.javashop.repository.TaxCategoryRepository;
import com.javashop.javashop.repository.UserRepository;
import com.sun.xml.bind.v2.util.QNameMap;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Optional;

@Component
public class ProductDataFetchers {
    @Autowired
    private ProductRepository productRepository;
    private TaxCategoryRepository taxCategoryRepository;

    public DataFetcher getProductDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            Optional<Product> tak = productRepository.findById(id);
            return  productRepository.findById(id);
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
}
