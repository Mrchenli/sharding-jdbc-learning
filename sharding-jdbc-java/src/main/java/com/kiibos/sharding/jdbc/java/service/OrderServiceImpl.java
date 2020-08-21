package com.kiibos.sharding.jdbc.java.service;

import com.kiibos.sharding.jdbc.java.entity.Address;
import com.kiibos.sharding.jdbc.java.entity.Order;
import com.kiibos.sharding.jdbc.java.entity.OrderItem;
import com.kiibos.sharding.jdbc.java.repository.*;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName OrderServiceImpl
 * @Description TODO
 * @Author cl
 * @Date 2020/8/21 下午5:40
 **/
public class OrderServiceImpl implements ExampleService {

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final AddressRepository addressRepository;


    public OrderServiceImpl(final DataSource dataSource) {
        orderRepository = new OrderRepositoryImpl(dataSource);
        orderItemRepository = new OrderItemRepositoryImpl(dataSource);
        addressRepository = new AddressRepositoryImpl(dataSource);
    }

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, AddressRepository addressRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.addressRepository = addressRepository;
    }

    public void initEnvironment() throws SQLException {
        orderRepository.createTableIfNotExists();
        orderItemRepository.createTableIfNotExists();
        orderRepository.truncateTable();
        orderItemRepository.truncateTable();
        initAddressTable();
    }

    private void initAddressTable() throws SQLException {
        addressRepository.createTableIfNotExists();
        addressRepository.truncateTable();
        initAddressData();
    }
    private void initAddressData() throws SQLException {
        for (int i = 0; i < 10; i++) {
            insertAddress(i);
        }
    }

    private void insertAddress(final int i) throws SQLException {
        Address address = new Address();
        address.setAddressId((long) i);
        address.setAddressName("address_" + i);
        addressRepository.insert(address);
    }



    public void cleanEnvironment() throws SQLException{
        orderRepository.dropTable();
        orderItemRepository.dropTable();
        addressRepository.dropTable();
    }

    public void processSuccess() throws SQLException{
        System.out.println("-------------- Process Success Begin ---------------");
        List<Long> orderIds = insertData();
        printData();
        deleteData(orderIds);
        printData();
        System.out.println("-------------- Process Success Finish --------------");
    }

    public void processFailure() throws SQLException {
        System.out.println("-------------- Process Failure Begin ---------------");
        insertData();
        System.out.println("-------------- Process Failure Finish --------------");
        throw new RuntimeException("Exception occur for transaction test.");
    }

    private List<Long> insertData() throws SQLException {
        System.out.println("---------------------------- Insert Data ----------------------------");
        List<Long> result = new ArrayList<>(10);
        for (int i = 1; i <= 10; i++) {
            Order order = insertOrder(i);
            insertOrderItem(i, order);
            result.add(order.getOrderId());
        }
        return result;
    }

    private Order insertOrder(final int i) throws SQLException {
        Order order = new Order();
        order.setUserId(i);
        order.setAddressId((long) i);
        order.setStatus("INSERT_TEST");
        orderRepository.insert(order);
        return order;
    }

    private void insertOrderItem(final int i, final Order order) throws SQLException {
        OrderItem item = new OrderItem();
        item.setOrderId(order.getOrderId());
        item.setUserId(i);
        item.setStatus("INSERT_TEST");
        orderItemRepository.insert(item);
    }

    private void deleteData(final List<Long> orderIds) throws SQLException {
        System.out.println("---------------------------- Delete Data ----------------------------");
        for (Long each : orderIds) {
            orderRepository.delete(each);
            orderItemRepository.delete(each);
        }
    }



    public void printData() throws SQLException {
        System.out.println("---------------------------- Print Order Data -----------------------");
        for (Object each : orderRepository.selectAll()) {
            System.out.println(each);
        }
        System.out.println("---------------------------- Print OrderItem Data -------------------");
        for (Object each : orderItemRepository.selectAll()) {
            System.out.println(each);
        }
    }
}
