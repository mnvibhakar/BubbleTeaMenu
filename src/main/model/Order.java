package model;

import java.util.ArrayList;

public class Order {

    private static final double TAX_RATE = 0.08; //The sales tax rate applied to final orders
    private double totalPrice;               // The total of price of all drinks in the order
    private ArrayList<Drink> drinksOrdered;      // A list of all drinks in the order

    // Effects: Creates new order
    public Order() {
        this.totalPrice = 0;
        drinksOrdered = new ArrayList<>();
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
    public void orderDrink(String size, ArrayList<String> exTop, double ice, double sugar, Drink drink) {
        drink.updateDrink(size, exTop, ice, sugar);
        drink.updateIngredients();
        addDrink(drink);
    }


    public ArrayList<Drink> getDrinksOrdered() {
        return this.drinksOrdered;
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }

    //Effects: Changes the price of the order by applying tax to the order
    //Modifies: This
    public void applyTax() {
        this.totalPrice += TAX_RATE * totalPrice;
    }

    //Effects: returns the amount of the given ingredient used between all drinks in the order
    public int getIngredientAmount(String name) {
        int ingredientAmount = 0;
        for (Drink drink : drinksOrdered) {
            ingredientAmount += drink.getIngredientAmount(name);
        }
        return ingredientAmount;
    }
}
