package com.javashop.javashop.repository;

import com.javashop.javashop.model.Complaint;
import com.javashop.javashop.model.Order;
import com.javashop.javashop.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("SELECT o FROM Order o WHERE (:delivery_address_id is null or o.deliveryAddress.id = :delivery_address_id) and (:shipment_method_id is null"
            + " or o.shipmentMethod.id = :shipment_method_id) and (:user_id is null or o.user.id = :user_id) and (COALESCE(:o_id,NULL) IS NULL or o.id IN (:o_id))")
    List<Order> findByDeliveryAddressIdAndShipmentMethodIdAndUserIdAndIdIn(@Param("delivery_address_id") Integer delivery_address_id,
                                                                           @Param("shipment_method_id") Integer shipment_method_id,
                                                                           @Param("user_id") Integer user_id,
                                                                           @Param("o_id") List<Integer> id,
                                                                           Pageable pageable);
    List<Order> findByIdIn(List<Integer> id, Pageable pageable);
    Long countByIdIn(List<Integer> id);
}
