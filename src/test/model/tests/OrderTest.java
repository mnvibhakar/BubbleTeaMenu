package model.tests;

import model.Order;
import model.drinks.BrownSugarBlack;
import model.drinks.MatchaLatte;
import java.util.ArrayList;
import model.Drink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class OrderTest {

    private Order order;
    private MatchaLatte matchaLatte;
    private BrownSugarBlack brownSugarBlack;
    private ArrayList<String> toppings;

    @BeforeEach
    void setup() {
        matchaLatte = new MatchaLatte();
        brownSugarBlack = new BrownSugarBlack();
        order = new Order();
        toppings = new ArrayList<>();
    }

    @Test
    public void testAddDrink() {
        order.addDrink(matchaLatte);
        assertEquals(matchaLatte, order.getDrinksOrdered().get(0));
        assertEquals(6.0, order.getTotalPrice());
        order.addDrink(brownSugarBlack);
        assertEquals(brownSugarBlack, order.getDrinksOrdered().get(1));
        assertEquals(12.0, order.getTotalPrice());
    }

    @Test
    public void testOrderDrink() {
        order.orderDrink("l", toppings, 1, 0, brownSugarBlack);
        assertEquals(1, order.getDrinksOrdered().size());
        assertEquals(6.5, order.getDrinksOrdered().get(0).getPrice());
        assertEquals(300, order.getDrinksOrdered().get(0).getIngredientAmount("black tea"));
        brownSugarBlack = new BrownSugarBlack();
        order.orderDrink("s", toppings, 0, 0, brownSugarBlack);
        assertEquals(2, order.getDrinksOrdered().size());
        assertEquals(6.0, order.getDrinksOrdered().get(1).getPrice());
        assertEquals(250, brownSugarBlack.getIngredientAmount("black tea"));
    }

    @Test
    public void testApplyTax() {
        order.applyTax();
        assertEquals(0, order.getTotalPrice());
        order.addDrink(matchaLatte);
        order.applyTax();
        assertEquals(6 * 1.08, order.getTotalPrice());
    }

    @Test
    public void testGetIngredient() {
        brownSugarBlack.updateDrink("s", toppings, 1, 1);
        brownSugarBlack.updateIngredients();
        assertEquals(0, order.getIngredientAmount("black tea"));
        order.addDrink(brownSugarBlack);
        assertEquals(200, order.getIngredientAmount("black tea"));
        order.addDrink(brownSugarBlack);
        assertEquals(400, order.getIngredientAmount("black tea"));
        assertEquals(0, order.getIngredientAmount("green tea"));
    }
}

