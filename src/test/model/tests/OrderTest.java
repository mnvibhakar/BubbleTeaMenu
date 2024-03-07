package model.tests;

import model.Drink;
import model.Ingredient;
import model.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;



public class OrderTest {

    private Order order;
    private Drink drink1;
    private Drink drink2;
    private Ingredient ingredient1;
    private ArrayList<String> noToppings;
    private ArrayList<String> toppings;
    private ArrayList<Ingredient> ingredients;

    @BeforeEach
    void setup() {
        order = new Order();
        noToppings = new ArrayList<>();
        toppings = new ArrayList<>();
        toppings.add("");
        ingredient1 = new Ingredient("tea base", "i", 500);
        ingredients = new ArrayList<>();
        ingredients.add(ingredient1);
        drink1 = new Drink("a", 5.0, ingredients, false);
        drink2 = new Drink("b", 6.0, ingredients, false);
    }

    @Test
    void testAddDrink() {
        order.addDrink(drink1);
        assertEquals(5.0, order.getTotalPrice());
        assertEquals(1, order.getDrinksOrdered().size());
    }

    @Test
    void testOrderNormalDrink() {
        order.orderDrink("s", noToppings, 1, 1, drink1);
        assertEquals(5.0, order.getTotalPrice());
        assertEquals(1, order.getDrinksOrdered().size());
    }

    @Test
    void testOrderLargeDrink() {
        order.orderDrink("l", noToppings, 1, 1, drink1);
        assertEquals(5.5, order.getTotalPrice());
        assertEquals(600, order.getIngredientAmount("i"));
    }

    @Test
    void testOrderDrinkWithToppings() {
        order.orderDrink("s", toppings, 1, 1, drink1);
        assertEquals(5.5, order.getTotalPrice());
        assertEquals(450, order.getIngredientAmount("i"));
    }

    @Test
    void testOrderMultipleDrinks() {
        order.orderDrink("s", noToppings, 1, 1, drink1);
        order.orderDrink("s", noToppings, 1, 1, drink2);
        assertEquals(11.0, order.getTotalPrice());
        assertEquals(2, order.getDrinksOrdered().size());
        assertEquals(1000, order.getIngredientAmount("i"));
        order.applyTax();
        assertEquals(11.88, order.getTotalPrice());
    }

    @Test
    void testSetPrice() {
        order.setTotalPrice(3.5);
        assertEquals(3.5, order.getTotalPrice());

    }

}
