package model.tests;

import model.drinks.MatchaLatte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MatchaLatteTest {

    private MatchaLatte drink1;
    private ArrayList<String> toppings1;
    private ArrayList<String> toppings2;

    @BeforeEach
    void setup() {
        drink1 = new MatchaLatte();
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
        assertEquals(6 * 0.8, drink1.getPrice());
    }

    @Test
    void testUpdateIngredientsSize() {
        drink1.updateDrink("l", toppings1, 1, 1);
        drink1.updateIngredients();
        assertEquals(500, drink1.getIngredientAmount("matcha"));
    }

    @Test
    void testUpdateIngredientsToppings() {
        drink1.updateDrink("s", toppings2, 1, 1);
        drink1.updateIngredients();
        assertEquals(350, drink1.getIngredientAmount("matcha"));
    }

    @Test
    void testUpdateIngredientsIce() {
        drink1.updateDrink("s", toppings1, 0.5, 1);
        drink1.updateIngredients();
        assertEquals(425, drink1.getIngredientAmount("matcha"));
    }

    @Test
    void testGetIngredientAmount() {
        drink1.updateDrink("s", toppings1, 1, 1);
        drink1.updateIngredients();
        assertEquals(400, drink1.getIngredientAmount("matcha"));
        assertEquals(0, drink1.getIngredientAmount("incorrect ingredient"));
    }
}
