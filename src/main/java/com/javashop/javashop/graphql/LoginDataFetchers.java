package com.javashop.javashop.graphql;

import com.javashop.javashop.model.Metadata;
import com.javashop.javashop.model.Opinion;
import com.javashop.javashop.model.User;
import com.javashop.javashop.repository.OpinionRepository;
import com.javashop.javashop.repository.ProductRepository;
import com.javashop.javashop.repository.UserRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

//@Component
//public class LoginDataFetchers {
//    @Autowired
//    private UserRepository userReposiotry;
//    @Autowired
//    private HttpSession httpSession;
//
//    public DataFetcher loginDataFetcher() {
//        return  dataFetchingEnvironment -> {
//            String login = dataFetchingEnvironment.getArgument("login");
//            String password = dataFetchingEnvironment.getArgument("password");
//
//            User user = userReposiotry.getOne(1);
//
//            Authentication authentication = new UsernamePasswordAuthenticationToken(login, password, user.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            httpSession.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
//            return httpSession.getId();
//        };
//    }
//}
