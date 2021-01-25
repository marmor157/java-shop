package com.javashop.javashop.model;

import com.javashop.javashop.repository.TaxCategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class TaxCategoryTest {
    @Autowired
    private TaxCategoryRepository taxCategoryRepository;

    @Test
    void testCreateTaxCategory(){
        TaxCategory taxCategory = new TaxCategory("VAT23", 23);
        TaxCategory taxCategorySaved = taxCategoryRepository.save(taxCategory);
        assertNotNull(taxCategorySaved);
    }

    @Test
    void testFindTaxCategoryById(){
        Integer id = 2;
        taxCategoryRepository.findById(2);
    }

    @Test
    void testFindTaxCategoryByIdIn(){
        Integer page = 1;
        Integer perPage = 10;
        Sort.Direction order = Sort.Direction.DESC;;
        String sortField = "id";
        List<Integer> idsInt = new ArrayList<>();
        idsInt.add(1);
        idsInt.add(2);
        taxCategoryRepository.findByIdIn(idsInt, PageRequest.of(page,perPage, Sort.by(order,sortField)));
    }
}