package com.javashop.javashop.graphql;

import com.javashop.javashop.model.Category;
import com.javashop.javashop.model.Complaint;
import com.javashop.javashop.model.Product;
import com.javashop.javashop.repository.ProductRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class SimilarProductDataFetchers {
    @Autowired
    private ProductRepository productRepository;

    public DataFetcher getSimilarProduct() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
//            Set<Product> products = new HashSet<Product>();
//            for(Category c : productRepository.getOne(id).getCategories()){
//                for(Product p : c.getProducts()){
//                    if(p.getId() != id){
//                        products.add(p);
//                    }
//                }
//            }
            return productRepository.findSimilarByProductId(id);
        };
    }
}
