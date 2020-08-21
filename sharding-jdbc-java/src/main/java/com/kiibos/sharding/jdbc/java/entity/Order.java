package com.kiibos.sharding.jdbc.java.entity;

import lombok.Data;

/**
 * @ClassName Order
 * @Description TODO
 * @Author cl
 * @Date 2020/8/21 下午5:45
 **/
@Data
public class Order {

    private Long orderId;
    private Integer userId;
    private Long addressId;
    private String status;

    @Override
    public String toString() {
        return String.format("order_id: %s, user_id: %s, address_id: %s, status: %s", orderId, userId, addressId, status);
    }
}
