package model.tests;

import model.Drink;
import model.Ingredient;
import model.Menu;
import model.exceptions.DuplicateNameException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MenuTest {

    private Menu menu;
    private Drink drink1;
    private Drink drink2;
    private ArrayList<Ingredient> ingredients;

    @BeforeEach
    void setup() {
        menu = new Menu();
        ingredients = new ArrayList<>();
        drink1 = new Drink("a", 5.0, ingredients, false);
        drink2 = new Drink("b", 4.0, ingredients, false);
    }

    @Test
    void testAddDrink() {
        try {
            menu.addDrink(drink1);
            assertEquals(1, menu.getDrinks().size());
        } catch (DuplicateNameException e) {
            fail();
        }

    }

    @Test
    void testAddMultipleDrinks() {
        try {
            menu.addDrink(drink1);
            menu.addDrink(drink2);
            assertEquals(2, menu.getDrinks().size());
        } catch (DuplicateNameException e) {
            fail();
        }

    }

    @Test
    void testAddDuplicateDrink() {
        try {
            menu.addDrink(drink2);
            menu.addDrink(drink2);
            fail();
        } catch (DuplicateNameException e) {
            assertEquals(1, menu.getDrinks().size());
        }
    }

    @Test
    void testFindDrink() {
        try {
            menu.addDrink(drink1);
            assertEquals(drink1, menu.findDrink("a"));
            assertEquals(null, menu.findDrink("wrong name"));
        } catch (DuplicateNameException e) {
            fail();
        }

    }

    @Test
    void testSetSpecials() {
        try {
            menu.addDrink(drink1);
            menu.addDrink(drink2);
            menu.setSpecials("a", "b");
            assertTrue(drink1.getIsSpecial());
            assertTrue(drink2.getIsSpecial());
            menu.setSpecials("c", "d");
            assertFalse(drink1.getIsSpecial());
            assertFalse(drink2.getIsSpecial());
        } catch (DuplicateNameException e) {
            fail();
        }

    }
}
