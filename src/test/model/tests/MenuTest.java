package model.tests;

import model.Menu;
import model.drinks.BrownSugarBlack;
import model.drinks.Classic;
import model.drinks.MatchaLatte;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MenuTest {

    private Menu menu;
    private Classic drink1;
    private MatchaLatte drink2;
    private BrownSugarBlack drink3;

    @BeforeEach
    void setup() {
        menu = new Menu("a", "b");
        drink1 = new Classic();
        drink2 = new MatchaLatte();
        drink3 = new BrownSugarBlack();
    }

    @Test
    void testFindDrink() {
        assertEquals(drink1.getName(), menu.findDrink("classic milk tea").getName());
        assertEquals(null, menu.findDrink("not on menu"));
    }

    @Test
    void testSetSpecials() {
        assertFalse(menu.findDrink("classic milk tea").getIsSpecial());
        assertFalse(menu.findDrink("matcha latte").getIsSpecial());
        assertFalse(menu.findDrink("brown sugar black milk tea").getIsSpecial());
        menu = new Menu("classic milk tea", "matcha latte");
        assertTrue(menu.findDrink("classic milk tea").getIsSpecial());
        assertTrue(menu.findDrink("matcha latte").getIsSpecial());
        assertFalse(menu.findDrink("brown sugar black milk tea").getIsSpecial());
    }

}
