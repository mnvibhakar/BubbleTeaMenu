package model.drinks;

import model.Drink;
import model.Ingredient;

import java.util.ArrayList;
import java.util.Collections;

public class MatchaLatte extends Drink {

    private Ingredient ingredient1 = new Ingredient("matcha", 400);

    // Effects: Creates a new Matcha Latte Drink
    public MatchaLatte() {
        super();
        this.name = "matcha latte";
        this.price = 6.0;
    }

    // Effects: adds ingredients to the drink based on the current specifications
    // Modifies this.ingredients, ingredient1
    @Override
    public void updateIngredients() {
        if (super.size == 'l') {
            ingredient1.addAmount(100);
        }
        ingredient1.addAmount((1 - this.ice) * 50);
        ingredient1.addAmount(-50 * this.toppings.size());

        this.ingredients.add(ingredient1);
    }
}
