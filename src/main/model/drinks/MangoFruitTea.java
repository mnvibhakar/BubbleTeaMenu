package model.drinks;

import model.Drink;
import model.Ingredient;

import java.util.ArrayList;
import java.util.Collections;

public class MangoFruitTea extends Drink {

    private Ingredient ingredient1 = new Ingredient("green tea", 300);  //Amount of green tea, ml
    private Ingredient ingredient2 = new Ingredient("mango syrup", 50); //Amount of mango syrup, ml
    private Ingredient ingredient3 = new Ingredient("lemon", 1);        //Amount of Lemon, slices
    private Ingredient ingredient4 = new Ingredient("lime", 2);         //Amount of Lime, pieces
    private Ingredient ingredient5 = new Ingredient("mango", 2);        //Amount of Mango, scoops

    public MangoFruitTea() {
        super();
        super.name = "mango fruit tea";
        super.price = 6.5;
    }

    //Effects: updates the amount of each ingredient based on the size, ice level, and toppings
    //         Adds the ingredients to the list of ingredients
    //Modifies: This
    @Override
    public void updateIngredients() {
        if (this.size == 'l') {
            ingredient1.addAmount(100);
            ingredient2.addAmount(25);
            ingredient3.addAmount(1);
            ingredient4.addAmount(2);
            ingredient5.addAmount(1);
        }
        ingredient1.addAmount((1 - this.ice) * 100);
        ingredient1.addAmount(-50 * this.toppings.size());

        Collections.addAll(this.ingredients, ingredient1, ingredient2, ingredient3, ingredient4, ingredient5);
    }

}
