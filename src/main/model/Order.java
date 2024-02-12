package model;

import java.util.ArrayList;

public class Order {

    private double totalPrice = 0;           // The total of price of all drinks in the order
    private ArrayList<Drink> drinksOrdered;  // A list of all drinks in the order

    // Effects: Creates new order
    public Order() {
    }

    // Effects: adds the given drink to list of drinks ordered and adds its price to the order price
    // Modifies: This
    public void addDrink(Drink d) {
        this.drinksOrdered.add(d);
        this.totalPrice += d.getPrice();
    }

    // Effects: applies the given specifications to the drink
    //          updates the amount of ingredients based on the given specifications
    //          adds the drink to the order
    // Modifies: This, the given drink
    public void orderDrink(char size, ArrayList<String> exTop, double ice, double sugar, Drink drink) {
        drink.updateDrink(size, exTop, ice, sugar);
        drink.updateIngredients();
        this.addDrink(drink);
    }


    public ArrayList<Drink> getDrinksOrdered() {
        return this.drinksOrdered;
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }
}
