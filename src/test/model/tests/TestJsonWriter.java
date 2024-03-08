package model.tests;

import model.*;
import model.exceptions.DuplicateNameException;
import model.persistence.JsonReader;
import model.persistence.JsonWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestJsonWriter {

    private Drink drink;
    private ArrayList<String> toppings;
    private ArrayList<Ingredient> ingredients;
    private Ingredient ingredient;
    private Order order;
    private OrderLog orderLog;
    private OrderLogList orderLogList;
    private Menu menu;

    @BeforeEach
    void setup() {
        toppings = new ArrayList<>();
        toppings.add("pearls");
        ingredients = new ArrayList<>();
        ingredient = new Ingredient("", "", 100);
        ingredients.add(ingredient);
        drink = new Drink("a", 1.0, ingredients, false);
        drink.updateDrink("s", toppings, 1, 1);
        order = new Order();
        order.addDrink(drink);
        orderLog = new OrderLog("a");
        orderLogList = new OrderLogList();
        menu = new Menu();
    }

    @Test
    void testWriteInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriteEmptyMenu() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriteEmptyMenu.json");
            writer.open();
            writer.writeMenu(menu);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriteEmptyMenu.json");
            menu = reader.readMenu();
            assertEquals(0, menu.getDrinks().size());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void testWriteNormalMenu() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriteMenu.json");
            menu.addDrink(drink);
            writer.open();
            writer.writeMenu(menu);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriteMenu.json");
            menu = reader.readMenu();
            assertEquals(1, menu.getDrinks().size());
        } catch (IOException e) {
            fail();
        } catch (DuplicateNameException e) {
            fail();
        }
    }

    @Test
    void testWriteEmptyOrderLog() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriteEmptyOrderLog.json");
            writer.open();
            writer.writeOrderLog(orderLog);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriteEmptyOrderLog.json");
            orderLog = reader.readOrderLog();
            assertEquals("a", orderLog.getName());
            assertEquals(0, orderLog.getOrders().size());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void testWriteNormalOrderLog() {
        try {
            orderLog.addOrder(order);
            JsonWriter writer = new JsonWriter("./data/testWriteOrderLog.json");
            writer.open();
            writer.writeOrderLog(orderLog);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriteOrderLog.json");
            orderLog = reader.readOrderLog();
            assertEquals("a", orderLog.getName());
            assertEquals(1, orderLog.getOrders().size());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void testWriteEmptyOrderLogList() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriteEmptyOrderLoglist.json");
            writer.open();
            writer.writeOrderLogList(orderLogList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriteEmptyOrderLoglist.json");
            orderLogList = reader.readOrderLogList();
            assertEquals(0, orderLogList.getOrderLogList().size());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void testWriteNormalOrderLogList() {
        try {
            orderLogList.addOrderLog(orderLog);
            JsonWriter writer = new JsonWriter("./data/testWriteOrderLoglist.json");
            writer.open();
            writer.writeOrderLogList(orderLogList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriteOrderLoglist.json");
            orderLogList = reader.readOrderLogList();
            assertEquals(1, orderLogList.getOrderLogList().size());
        } catch (IOException e) {
            fail();
        }
    }
}
