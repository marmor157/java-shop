package com.javashop.javashop.graphql;

import com.javashop.javashop.model.Role;
import com.javashop.javashop.repository.RoleRepository;
import com.javashop.javashop.repository.UserRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class RoleDataFetchers {
    @Autowired
    private RoleRepository roleRepository;

    public DataFetcher getRoleDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return  roleRepository.findById(id);
        };
    }

    public DataFetcher createRoleDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            String name = (String) l.get("name");

            return roleRepository.save(new Role(name));
        };
    }

    public DataFetcher updateRoleDataFetcher() {
        return  dataFetchingEnvironment -> {
            LinkedHashMap<String, Object> l = dataFetchingEnvironment.getArgument("input");

            Integer id =Integer.parseInt((String) l.get("id"));
            Role role = roleRepository.getOne(id);
            if(l.containsKey("name")){
                String name = (String) l.get("name");
                role.setName(name);
            }

            return roleRepository.save(role);
        };
    }

    public DataFetcher deleteRoleDataFetcher() {
        return  dataFetchingEnvironment -> {
            Integer id =Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            Role role = roleRepository.getOne(id);
            roleRepository.delete(role);

            return role;
        };
    }
}
