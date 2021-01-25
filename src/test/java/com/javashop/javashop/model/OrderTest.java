package com.javashop.javashop.model;

import com.javashop.javashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
class OrderTest {
    @Autowired
    private OrderRepository orderRepository;

    private Order order;

    @Test
    void testCreateOrder(){
        order = new Order(LocalDate.now(), 100, "zamowione");
        Order savedOrder = orderRepository.save(order);
        assertNotNull(savedOrder);
        assertEquals(100, order.getPrice());
    }

    @Test
    void testFindOrderById(){
        Integer id = 2;
        orderRepository.findById(2);
    }

    @Test
    void testFindOrderByIdIn(){
        Integer page = 1;
        Integer perPage = 10;
        Sort.Direction order = Sort.Direction.DESC;;
        String sortField = "id";
        List<Integer> idsInt = new ArrayList<>();
        idsInt.add(1);
        idsInt.add(2);
        assertNotNull(orderRepository.findByIdIn(idsInt, PageRequest.of(page,perPage, Sort.by(order,sortField))));
    }

    @Test
    void testFindByDeliveryAddressIdAndShipmentMethodIdAndUserIdAndIdIn(){
        Integer page = 1;
        Integer perPage = 10;
        Sort.Direction order = Sort.Direction.DESC;;
        String sortField = "id";
        List<Integer> idsInt = new ArrayList<>();
        idsInt.add(1);
        idsInt.add(2);

        Integer deliveryAddressID = 1;
        Integer shipmentMethodID = 1;
        Integer productID = 2;
        Integer userID = 2;
        orderRepository.findByDeliveryAddressIdAndShipmentMethodIdAndUserIdAndIdIn(deliveryAddressID,shipmentMethodID, userID,
                idsInt,PageRequest.of(page,perPage, Sort.by(order,sortField)));
    }

    @Test
    void testDeleteOrder(){
        Integer id = 2;

        boolean isExistBeforeDelete = orderRepository.findById(id).isPresent();
        if(isExistBeforeDelete){
            orderRepository.deleteById(id);
        }
        boolean isExistAfterDelete = orderRepository.findById(id).isPresent();
        assertTrue(isExistBeforeDelete);
        assertFalse(isExistAfterDelete);
    }

//    @Test
//    void testUpdateOrder(){
//        Integer id = 2;
//
//        String status = "wyslane";
//        Integer price = 1000;
//        Order order = orderRepository.getOne(2);
//        orderRepository.
//    }
}