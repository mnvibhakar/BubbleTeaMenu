package model.tests;

import model.Order;
import model.OrderLog;
import model.OrderLogList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderLogListTest {

    private OrderLogList orderLogList;
    private OrderLog orderLog1;
    private OrderLog orderLog2;
    private Order order1;
    private Order order2;

    @BeforeEach
    void setup() {
        orderLogList = new OrderLogList();
        orderLog1 = new OrderLog("a");
        orderLog2 = new OrderLog("b");
    }

    @Test
    void testAddOrderLog() {
        orderLogList.addOrderLog(orderLog1);
        assertEquals(1, orderLogList.getOrderLogList().size());
        orderLogList.addOrderLog(orderLog2);
        assertEquals(2, orderLogList.getOrderLogList().size());
    }

    @Test
    void testFindOrderLog() {
        orderLogList.addOrderLog(orderLog1);
        orderLogList.addOrderLog(orderLog2);
        assertEquals(orderLog1, orderLogList.findOrderLog("a"));
        assertEquals(null, orderLogList.findOrderLog("wrong name"));
    }
}
