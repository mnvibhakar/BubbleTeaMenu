package model.drinks;

import model.Drink;
import model.Ingredient;

/*
Represents a matcha latte, lists the ingredients used in the drink
 */
public class MatchaLatte extends Drink {

    private Ingredient ingredient1 = new Ingredient("matcha", 400); //ingredient for matcha latte

    // Effects: Creates a new Matcha Latte Drink
    public MatchaLatte() {
        super();
        this.name = "matcha latte";
        this.price = 6.0;
    }

    // Effects: adds ingredients to the drink based on the current specifications
    // Modifies this, ingredient1
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
