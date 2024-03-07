package model.tests;

import model.Drink;
import model.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;

public class DrinkTest {

    private Ingredient ingredient1 = new Ingredient("tea base", "a", 400);
    private Ingredient ingredient2 = new Ingredient("fruit", "b", 1);
    private Ingredient ingredient3 = new Ingredient("milk", "c", 40);
    private ArrayList<Ingredient> ingredients;
    private ArrayList<String> toppings;
    private  ArrayList<String> noToppings;
    private Drink drink;

    @BeforeEach
    void setup() {
        ingredients = new ArrayList<>();
        Collections.addAll(ingredients, ingredient1, ingredient2, ingredient3);
        drink = new Drink("d", 5.0, ingredients, false);
        toppings = new ArrayList<>();
        toppings.add("");
        noToppings = new ArrayList<>();
    }

    @Test
    void testUpdateDrinkNormal() {
        drink.updateDrink("s", noToppings, 1, 1);
        assertEquals('s', drink.getSize());
        assertEquals(5.0, drink.getPrice());
        assertEquals(0, drink.getToppings().size());
    }

    @Test
    void testUpdateDrinkSize() {
        drink.updateDrink("l", noToppings, 1, 1);
        assertEquals('l', drink.getSize());
        assertEquals(5.5, drink.getPrice());
    }

    @Test
    void testUpdateDrinkSpecialSmall() {
        drink.setSpecial(true);
        drink.updateDrink("s", noToppings, 1, 1);
        assertEquals(4.0, drink.getPrice());
    }

    @Test
    void testUpdateDrinkSpecialLarge() {
        drink.setSpecial(true);
        drink.updateDrink("l", noToppings, 1, 1);
        assertEquals(4.4, drink.getPrice());
    }

    @Test
    void testUpdateDrinkToppings() {
        drink.updateDrink("s", toppings, 1, 1);
        assertEquals(5.5, drink.getPrice());
        assertEquals(1, drink.getToppings().size());
    }

    @Test
    void testUpdateIngredientsNormal() {
        drink.updateDrink("s", noToppings, 1, 1);
        drink.updateIngredients();
        assertEquals(400, drink.getIngredientAmount("a"));
        assertEquals(1, drink.getIngredientAmount("b"));
        assertEquals(40, drink.getIngredientAmount("c"));
    }

    @Test
    void testUpdateIngredientsLarge() {
        drink.updateDrink("l", noToppings, 1, 1);
        drink.updateIngredients();
        assertEquals(500, drink.getIngredientAmount("a"));
        assertEquals(2, drink.getIngredientAmount("b"));
        assertEquals(60, drink.getIngredientAmount("c"));
    }

    @Test
    void testUpdateIngredientsToppings() {
        drink.updateDrink("s", toppings, 1, 1);
        drink.updateIngredients();
        assertEquals(350, drink.getIngredientAmount("a"));
        assertEquals(1, drink.getIngredientAmount("b"));
        assertEquals(40, drink.getIngredientAmount("c"));
    }

    @Test
    void testUpdateIngredientsIce() {
        drink.updateDrink("s", noToppings, 0.5, 1);
        drink.updateIngredients();
        assertEquals(450, drink.getIngredientAmount("a"));
        assertEquals(1, drink.getIngredientAmount("b"));
        assertEquals(40, drink.getIngredientAmount("c"));
    }
}
