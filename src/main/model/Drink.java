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

    // Requires: 0 <= exTop <= 2
    // Effects: Applies the given specifications to the drink
    //          updates the price of the drink based on the given specifications
    public void updateDrink(char size, ArrayList exTop, double ice, double sugar) {
        if ('l' == size) {
            this.size = 'l';
            this.price += 0.5;
        }
        if (true == this.isSpecial) {
            this.price *= 0.8;
        }
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
}

