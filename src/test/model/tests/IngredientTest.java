package model.tests;

import model.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IngredientTest {

    private Ingredient ingredient;

    @BeforeEach
    void setup() {
        ingredient = new Ingredient("", "", 400);
    }

    @Test
    void testAddAmount() {
        ingredient.addAmount(0);
        assertEquals(400, ingredient.getAmount());
        ingredient.addAmount(100);
        assertEquals(500, ingredient.getAmount());
        ingredient.addAmount(-100);
        assertEquals(400, ingredient.getAmount());
        ingredient.addAmount(-400);
        assertEquals(0, ingredient.getAmount());
        ingredient.addAmount(-500);
        assertEquals(0, ingredient.getAmount());
    }
}
