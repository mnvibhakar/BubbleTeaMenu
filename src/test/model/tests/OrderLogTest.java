package model.tests;

import model.Drink;
import model.Order;
import model.OrderLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderLogTest {

    private OrderLog orderLog;
    private Order order;

    @BeforeEach
    void setup() {
        orderLog = new OrderLog("a");
        order = new Order();
    }

    @Test
    void testAddOrder() {
        orderLog.addOrder(order);
        assertEquals(1, orderLog.getOrders().size());
        assertEquals("a", orderLog.getName());
    }

    @Test
    void testGetTotalPrice() {
        assertEquals(0, orderLog.getTotalPrice());
        orderLog.addOrder(order);
        orderLog.addOrder(order);
        assertEquals(0, orderLog.getTotalPrice());

    }

}
