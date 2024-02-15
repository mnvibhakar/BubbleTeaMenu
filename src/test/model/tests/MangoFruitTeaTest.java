package model.tests;

import model.drinks.MangoFruitTea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MangoFruitTeaTest {

    private MangoFruitTea drink1;
    private ArrayList<String> toppings1;
    private ArrayList<String> toppings2;

    @BeforeEach
    void setup() {
        drink1 = new MangoFruitTea();
        toppings1 = new ArrayList<>();
        toppings2 = new ArrayList<>();
        toppings2.add("pearls");
    }

    @Test
    void testUpdateDrinkSize() {
        drink1.updateDrink("l", toppings1, 1, 1);
        assertEquals(7.0, drink1.getPrice());
    }

    @Test
    void testUpdateDrinkTopping() {
        drink1.updateDrink("s", toppings2, 1, 1);
        assertEquals(7.0, drink1.getPrice());
    }

    @Test
    void testUpdateDrinkSpecial() {
        drink1.setSpecial(true);
        drink1.updateDrink("s", toppings1, 1, 1);
        assertEquals(5.2, drink1.getPrice());
    }

    @Test
    void testUpdateIngredientsSize() {
        drink1.updateDrink("l", toppings1, 1, 1);
        drink1.updateIngredients();
        assertEquals(400, drink1.getIngredientAmount("green tea"));
        assertEquals(75, drink1.getIngredientAmount("mango syrup"));
        assertEquals(2, drink1.getIngredientAmount("lemon"));
        assertEquals(4, drink1.getIngredientAmount("lime"));
        assertEquals(3, drink1.getIngredientAmount("mango"));
    }

    @Test
    void testUpdateIngredientsToppings() {
        drink1.updateDrink("s", toppings2, 1, 1);
        drink1.updateIngredients();
        assertEquals(250, drink1.getIngredientAmount("green tea"));
    }

    @Test
    void testUpdateIngredientsIce() {
        drink1.updateDrink("s", toppings1, 0.5, 1);
        drink1.updateIngredients();
        assertEquals(350, drink1.getIngredientAmount("green tea"));
    }

    @Test
    void testGetIngredientAmount() {
        drink1.updateDrink("s", toppings1, 1, 1);
        drink1.updateIngredients();
        assertEquals(300, drink1.getIngredientAmount("green tea"));
        assertEquals(0, drink1.getIngredientAmount("incorrect ingredient"));
    }
}
