package model.tests;

import model.Menu;
import model.OrderLog;
import model.OrderLogList;
import model.persistence.JsonReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestJsonReader {

    @Test
    void testReadWrongMenuFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.readMenu();
            fail("IOException expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testReadWrongOrderLogFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.readOrderLog();
            fail("IOException expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testReadWrongOrderLogListFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.readOrderLogList();
            fail("IOException expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testReadEmptyMenu() {
        JsonReader reader = new JsonReader("./data/testReadEmptyMenu.json");
        try {
            Menu menu = reader.readMenu();
            assertEquals(0, menu.getDrinks().size());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void testReadEmptyOrderLog() {
        JsonReader reader = new JsonReader("./data/testReadEmptyOrderLog.json");
        try {
            OrderLog orderLog = reader.readOrderLog();
            assertEquals(0, orderLog.getOrders().size());
            assertEquals("", orderLog.getName());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void testReadEmptyOrderLogList() {
        JsonReader reader = new JsonReader("./data/testReadEmptyOrderLogList.json");
        try {
            OrderLogList orderLogList = reader.readOrderLogList();
            assertEquals(0, orderLogList.getOrderLogList().size());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void testReadNormalMenu() {
        JsonReader reader = new JsonReader("./data/testReadMenu.json");
        try {
            Menu menu = reader.readMenu();
            assertEquals(2, menu.getDrinks().size());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void testReadNormalOrderLog(){
        JsonReader reader = new JsonReader("./data/testReadOrderLog.json");
        try {
            OrderLog orderLog = reader.readOrderLog();
            assertEquals(2, orderLog.getOrders().size());
            assertEquals("a", orderLog.getName());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void testReadNormalOrderLogList() {
        JsonReader reader = new JsonReader("./data/testReadOrderLogList.json");
        try {
            OrderLogList orderLogList = reader.readOrderLogList();
            assertEquals(2, orderLogList.getOrderLogList().size());
        } catch (IOException e) {
            fail();
        }
    }
}
