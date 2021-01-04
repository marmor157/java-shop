package com.javashop.javashop.graphql;

import com.javashop.javashop.model.User;
import com.javashop.javashop.repository.UserRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

@Component
public class UserDataFetchers {
    @Autowired
    private UserRepository userRepository;

    public DataFetcher getUserDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  userRepository.findById(id);
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
    public DataFetcher deleteUserDataFetcher() {
        return  dataFetchingEnvironment -> {

            Integer id =Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            User user = userRepository.getOne(id);
            userRepository.delete(user);

            return user;
        };
    }
}
