package model.drinks;

import model.Drink;
import model.Ingredient;

import java.util.ArrayList;

public class Classic extends Drink {

    private Ingredient ingredient1 = new Ingredient("classic", 300);

    // Effects: Creates a new Classic Milt tea Drink with Pearls
    public Classic() {
        super();
        this.name = "classic milk tea";
        this.price = 5.5;
        this.toppings.add("Pearls");
    }

    // Effects: adds ingredients to the drink based on the specifications of the drink
    // Modifies: this.ingredients, ingredient1
    @Override
    public void updateIngredients() {
        if (this.size == 'l') {
            ingredient1.addAmount(100);
        }
        ingredient1.addAmount((1 - this.ice) * 150);
        ingredient1.addAmount(-50 * this.toppings.size());
        this.ingredients.add(ingredient1);
    }
}
