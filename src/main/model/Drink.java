package model;

import model.Ingredient;
import java.util.ArrayList;



public abstract class Drink {

    protected String name;                         // Name of the drink
    protected ArrayList<Ingredient> ingredients;   // Ingredients included in the drink
    protected double price;                        // price of the drink
    protected ArrayList<String> toppings;          // List of toppings in the drink
    protected char size;                           // size of the drink
    protected double sugar;                        // amount of sugar in the drink
    protected double ice;                          // amount of ice in the drink
    protected boolean isSpecial;                   // details if the drink is on special or not


    // Requires: n longer than 0 characters, price >= 0
    // Effects: Creates a drink with the specified characteristics
    //          Discounts drink by 20% if it's on special
    public Drink() {
        this.toppings = new ArrayList<>();
        this.ingredients = new ArrayList<>();
    }

    public abstract void updateIngredients();

    // Requires: 0 <= exTop <= 2, 0 <= ice <= 1, 0 <= sugar <= 1
    // Effects: Applies the given specifications to the drink
    //          updates the price of the drink based on the given specifications
    //Modifies: This
    public void updateDrink(String size, ArrayList<String> exTop, double ice, double sugar) {
        if (size.equals("l")) {
            this.size = 'l';
            this.price += 0.5;
        }
        if (this.isSpecial) {
            this.price *= 0.8;
        }
        this.price += 0.5 * exTop.size();
        this.ice = ice;
        this.sugar = sugar;
        toppings.addAll(exTop);
    }

    public void setSpecial(boolean status) {
        this.isSpecial = status;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public double getPrice() {
        return this.price;
    }

    public ArrayList<String> getToppings() {
        return this.toppings;
    }

    public double getIce() {
        return this.ice;
    }

    public double getSugar() {
        return this.sugar;
    }

    public boolean getIsSpecial() {
        return this.isSpecial;
    }

    //Effects: Returns the amount of the given ingredient used in the drink
    public int getIngredientAmount(String n) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName().equals(n)) {
                return ingredient.getAmount();
            }
        }
        return 0;
    }

    public char getSize() {
        return this.size;
    }
}

