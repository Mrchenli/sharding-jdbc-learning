package com.kiibos.sharding.jdbc.java.entity;

import lombok.Data;

/**
 * @ClassName OrderItem
 * @Description TODO
 * @Author cl
 * @Date 2020/8/21 下午6:03
 **/
@Data
public class OrderItem {

    private Long orderItemId;

    private Long orderId;

    private Integer userId;

    private String status;

    @Override
    public String toString() {
        return String.format("order_item_id:%s, order_id: %s, user_id: %s, status: %s", orderItemId, orderId, userId, status);
    }

}
