package model;

/*
Represents an ingredient, with a name and an amount
 */
public class Ingredient {

    private String name;  // Name of the ingredient
    private int amount;   // Amount of the ingredient, uses various measurements based on the ingredient type

    // Effects: sets the characteristics of the ingredient as desired
    public Ingredient(String n, int a) {
        this.name = n;
        this.amount = a;
    }

    //Effects: changes the amount of the ingredient by the given amount, sets it to 0 if this results in a negative
    //Modifies: This
    public void addAmount(double newAmount) {
        if (this.amount + newAmount > 0) {
            this.amount += newAmount;
        } else {
            this.amount = 0;
        }
    }

    public String getName() {
        return this.name;
    }

    public int getAmount() {
        return  this.amount;
    }
}
