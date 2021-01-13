package com.javashop.javashop.graphql;

import com.javashop.javashop.model.Product;
import com.javashop.javashop.model.User;
import com.javashop.javashop.repository.ProductRepository;
import com.javashop.javashop.repository.RoleRepository;
import com.javashop.javashop.repository.UserRepository;
import com.javashop.javashop.service.MailService;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class UserDataFetchers {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MailService mailService;


    public DataFetcher getUserDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  userRepository.findById(id);
        };
    }

    public DataFetcher getAllUserDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer page = dataFetchingEnvironment.getArgument("page");
            Integer perPage = dataFetchingEnvironment.getArgument("perPage");
            String sortField = dataFetchingEnvironment.getArgument("sortField");
            String sortOrder = dataFetchingEnvironment.getArgument("sortOrder");
            LinkedHashMap<String, Object> filter = dataFetchingEnvironment.getArgument("filter");

            Sort.Direction order = null;
            if(sortOrder.toUpperCase().equals("DESC")){
                order = Sort.Direction.DESC;
            }
            else{
                order = Sort.Direction.ASC;
            }

            if(sortField.equals("")){
                sortField = "id";
            }

            Page<User> userPage = userRepository.findAll(PageRequest.of(page,perPage, Sort.by(order,sortField)));
            return userPage;
        };
    }

    public DataFetcher getAllUserMetaDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer page = dataFetchingEnvironment.getArgument("page");
            Integer perPage = dataFetchingEnvironment.getArgument("perPage");
            String sortField = dataFetchingEnvironment.getArgument("sortField");
            String sortOrder = dataFetchingEnvironment.getArgument("sortOrder");
            LinkedHashMap<String, Object> filter = dataFetchingEnvironment.getArgument("filter");
            Sort.Direction order = null;
            if(sortOrder.toUpperCase().equals("DESC")){
                order = Sort.Direction.DESC;
            }
            else{
                order = Sort.Direction.ASC;
            }

            if(sortField.equals("")){
                sortField = "id";
            }
            Page<User> productPage = userRepository.findAll(PageRequest.of(page,perPage, Sort.by(order,sortField)));
            Metadata metadata = new Metadata(productPage.stream().count());
            return metadata;
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
            LocalDate birthDate = LocalDate.parse(birthDateStr);
            String telephone = (String) l.get("telephone");
            Integer roleID = Integer.parseInt((String) l.get("roleID"));

            User user = new User(login, password, email, name, surname, address, birthDate, telephone);
            user.setRole(roleRepository.getOne(roleID));
            roleRepository.getOne(roleID).getUsers().add(user);

            return userRepository.save(user);
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
                LocalDate birthDate = LocalDate.parse(birthDateStr);
                user.setBirthDate(birthDate);
            }
            if(l.containsKey("telephone")){
                String telephone = (String) l.get("telephone");
                user.setTelephone(telephone);
            }
            if(l.containsKey("roleID")){
                Integer roleID = Integer.parseInt((String) l.get("roleID"));
                user.setRole(roleRepository.getOne(roleID));
                roleRepository.getOne(roleID).getUsers().add(user);
            }
            if(l.containsKey("wishlistProductIDs")){
                List<String> productIDs = (List<String>) l.get("wishlistProductIDs");
                for (String prodID: productIDs){
                    user.getWishlistUser().add(productRepository.getOne(Integer.parseInt(prodID)));
                    productRepository.getOne(Integer.parseInt(prodID)).getWishlistProduct().add(user);
                }
            }

            return userRepository.save(user);
        };
    }
    public DataFetcher deleteUserDataFetcher() {
        return  dataFetchingEnvironment -> {

            Integer id =Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            User user = userRepository.getOne(id);
            userRepository.delete(user);

            return user;
        };
    }
}
