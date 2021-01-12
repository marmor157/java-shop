package com.javashop.javashop.graphql;

import com.javashop.javashop.model.Product;
import com.javashop.javashop.model.Role;
import com.javashop.javashop.model.User;
import com.javashop.javashop.repository.RoleRepository;
import com.javashop.javashop.repository.UserRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public DataFetcher getAllRoleDataFetcher() {
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

            Page<Role> rolePage = roleRepository.findAll(PageRequest.of(page,perPage, Sort.by(order,sortField)));
            return rolePage;
        };
    }

    public DataFetcher getAllRoleMetaDataFetcher() {
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
            Page<Role> productPage = roleRepository.findAll(PageRequest.of(page,perPage, Sort.by(order,sortField)));
            Metadata metadata = new Metadata(productPage.stream().count());
            return metadata;
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
