package model.drinks;

import model.Drink;
import model.Ingredient;

import java.util.Collections;

/*
Represents a Brown sugar milk tea. Lists the ingredients used in the brown sugar milk tea
 */
public class BrownSugarBlack extends Drink {


    private Ingredient ingredient1 = new Ingredient("black tea", 200); // ingredient for black tea
    private Ingredient ingredient2 = new Ingredient("specialty", 40);  // ingredient for specialty milk

    // Effects: Creates a new Brown sugar Black Milk Tea Drink with Pearls
    public BrownSugarBlack() {
        super();
        this.name = "brown sugar black milk tea";
        this.price = 6.0;
        this.toppings.add("pearls");
    }

    // Effects: Updates the amount of each ingredient based on drink specifications
    // Modifies: this, ingredient1, ingredient2
    @Override
    public void updateIngredients() {
        if (this.size == 'l') {
            ingredient1.addAmount(100);
            ingredient2.addAmount(20);
        }

        ingredient1.addAmount((1 - this.ice) * 50);
        ingredient1.addAmount(-25 * (this.toppings.size() - 1));

        Collections.addAll(this.ingredients, ingredient1, ingredient2);
    }
}
