package model.tests;

import model.Order;
import model.drinks.BrownSugarBlack;
import model.drinks.MatchaLatte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class OrderTest {

    private Order order;
    private MatchaLatte matchaLatte;
    private BrownSugarBlack brownSugarBlack;

    @BeforeEach
    void setup() {
        matchaLatte = new MatchaLatte();
        brownSugarBlack = new BrownSugarBlack();
        order = new Order();
    }

    @Test
    public void testAddDrink() {
        order.addDrink(matchaLatte);
        //assertEquals(ArrayList<Drink>(matchaLatte), order.getDrinksOrdered());
        //assertEquals(6.0, order.getTotalPrice())
    }

    @Test
    public void testOrderDrink() {

    }
}
