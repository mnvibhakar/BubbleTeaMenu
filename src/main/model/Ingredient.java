package model;

public class Ingredient {

    private String name;  // Name of the ingredient
    private int amount;   // Amount of the ingredient, in ml or grams

    // Effects: sets the characteristics of the ingredient as desired
    public Ingredient(String n, int a) {
        this.name = n;
        this.amount = a;
    }

    public void addAmount(double newAmount) {
        this.amount += newAmount;
    }

    public String getName() {
        return this.name;
    }

    public int getAmount() {
        return  this.amount;
    }
}
