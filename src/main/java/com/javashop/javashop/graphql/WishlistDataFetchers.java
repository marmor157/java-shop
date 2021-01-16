package com.javashop.javashop.graphql;

import com.javashop.javashop.model.*;
import com.javashop.javashop.repository.UserRepository;
import com.javashop.javashop.repository.WarehouseRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class WishlistDataFetchers {
    @Autowired
    private UserRepository userRepository;

    public DataFetcher getUserWishlist() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  userRepository.getOne(id).getWishlist();
        };
    }

    public DataFetcher deleteProductFromWishlist() {
        return  dataFetchingEnvironment -> {
            Integer user_id = Integer.parseInt(dataFetchingEnvironment.getArgument("user_id"));
            Integer product_id = Integer.parseInt(dataFetchingEnvironment.getArgument("product_id"));
            User user = userRepository.getOne(user_id);
            user.getWishlist().removeIf(product->
                    product.getId().equals(product_id)
            );
            return userRepository.save(user);
        };
    }
}
