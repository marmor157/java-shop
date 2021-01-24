package com.javashop.javashop.graphql;

import com.javashop.javashop.model.Metadata;
import com.javashop.javashop.model.OrderOpinion;
import com.javashop.javashop.model.User;
import com.javashop.javashop.repository.OrderOpinionRepository;
import com.javashop.javashop.repository.ProductRepository;
import com.javashop.javashop.repository.RoleRepository;
import com.javashop.javashop.repository.UserRepository;
import com.javashop.javashop.service.MailService;
import graphql.schema.DataFetcher;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private OrderOpinionRepository orderOpinionRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public DataFetcher getUserDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  userRepository.findById(id);
        };
    }

    public DataFetcher getAllUsersDataFetcher() {
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
                if(filter.containsKey("ids") && filter.containsKey("roleID")){
                    final List<String> ids = (List<String>) filter.get("ids");
                    Integer roleID = Integer.parseInt((String) filter.get("roleID"));
                    List<Integer> idsInt = new ArrayList<>();
                    for(String s : ids) idsInt.add(Integer.valueOf(s));
                    return  userRepository.findByRoleIdAndIdIn(roleID,idsInt, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
                else if(filter.containsKey("ids")){
                    final List<String> ids = (List<String>) filter.get("ids");
                    List<Integer> idsInt = new ArrayList<>();
                    for(String s : ids) idsInt.add(Integer.valueOf(s));
                    return  userRepository.findByIdIn(idsInt, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
                else if(filter.containsKey("roleID")){
                    Integer roleID = Integer.parseInt((String) filter.get("roleID"));
                    return  userRepository.findByRoleId(roleID, PageRequest.of(page,perPage, Sort.by(order,sortField)));
                }
            }
            Page<User> userPage = userRepository.findAll(PageRequest.of(page,perPage, Sort.by(order,sortField)));
            return userPage;
        };
    }

    public DataFetcher getAllUsersMetaDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> filter = dataFetchingEnvironment.getArgument("filter");

            if(filter!=null){
                if(filter.containsKey("ids")){
                    final List<String> ids = (List<String>) filter.get("ids");
                    List<Integer> idsInt = new ArrayList<>();
                    for(String s : ids) idsInt.add(Integer.valueOf(s));
                    return new Metadata(userRepository.countByIdIn(idsInt));
                }
            }
            Metadata metadata = new Metadata(userRepository.count());
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
            Integer roleID;
            if(l.get("roleID")!=null)
                roleID = Integer.parseInt((String) l.get("roleID"));
            else roleID =roleRepository.findOneByName("user").getId();

            User user = new User(login, passwordEncoder.encode(password), email, name, surname, address, birthDate, telephone);
            if(roleID!=null){
                user.setRole(roleRepository.getOne(roleID));
                roleRepository.getOne(roleID).getUsers().add(user);
            }
            try{
                return userRepository.save(user);
            }catch (DataIntegrityViolationException e) {
               throw new Exception(e.getMostSpecificCause());
            }

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
                user.setPassword(passwordEncoder.encode(password));
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
                    user.getWishlist().add(productRepository.getOne(Integer.parseInt(prodID)));
                    productRepository.getOne(Integer.parseInt(prodID)).getWishlist().add(user);
                }
            }

            return userRepository.save(user);
        };
    }
    public DataFetcher deleteUserDataFetcher() {
        return  dataFetchingEnvironment -> {

            Integer id =Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            User user = userRepository.getOne(id);
            user.setDeleteDate(LocalDate.now());

            return userRepository.save(user);
        };
    }
}
