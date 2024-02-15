package model.tests;

import model.drinks.BrownSugarBlack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BrownSugarBlackTest {

    private BrownSugarBlack drink1;
    private ArrayList<String> toppings1;
    private ArrayList<String> toppings2;

    @BeforeEach
    void setup() {
        drink1 = new BrownSugarBlack();
        toppings1 = new ArrayList<>();
        toppings2 = new ArrayList<>();
        toppings2.add("pearls");
    }

    @Test
    void testUpdateDrinkSize() {
        drink1.updateDrink("l", toppings1, 1, 1);
        assertEquals(6.5, drink1.getPrice());
    }

    @Test
    void testUpdateDrinkTopping() {
        drink1.updateDrink("s", toppings2, 1, 1);
        assertEquals(6.5, drink1.getPrice());
    }

    @Test
    void testUpdateDrinkSpecial() {
        drink1.setSpecial(true);
        drink1.updateDrink("s", toppings1, 1, 1);
        assertEquals(6.0 * 0.8, drink1.getPrice());
    }

    @Test
    void testUpdateIngredientsSize() {
        drink1.updateDrink("l", toppings1, 1, 1);
        drink1.updateIngredients();
        assertEquals(300, drink1.getIngredientAmount("black tea"));
        assertEquals(60, drink1.getIngredientAmount("specialty"));
    }

    @Test
    void testUpdateIngredientsToppings() {
        drink1.updateDrink("s", toppings2, 1, 1);
        drink1.updateIngredients();
        assertEquals(175, drink1.getIngredientAmount("black tea"));
    }

    @Test
    void testUpdateIngredientsIce() {
        drink1.updateDrink("s", toppings1, 0.5, 1);
        drink1.updateIngredients();
        assertEquals(225, drink1.getIngredientAmount("black tea"));
    }

    @Test
    void testGetIngredientAmount() {
        drink1.updateDrink("s", toppings1, 1, 1);
        drink1.updateIngredients();
        assertEquals(200, drink1.getIngredientAmount("black tea"));
        assertEquals(0, drink1.getIngredientAmount("incorrect ingredient"));
    }
}
