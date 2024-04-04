package model;

import model.persistence.Writable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/*
Represents an order, with a list of drinks that have been ordered, and records the total price
 */
public class Order implements Writable {

    private static final double TAX_RATE = 0.08; //The sales tax rate applied to final orders
    private double totalPrice;                   // The total of price of all drinks in the order
    private ArrayList<Drink> drinksOrdered;      // A list of all drinks in the order
    private int orderNumber;

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
        Event event = new Event("Added drink to order, name: " + drink.getName());
        EventLog.getInstance().logEvent(event);
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

    public void setTotalPrice(double p) {
        totalPrice = p;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int n) {
        orderNumber = n;
    }

    //Effects: converts the order to a JSONObject and returns it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("drinks", drinksToJson());
        json.put("number", orderNumber);
        return json;
    }

    //Effects: converts drinks to a JSONArray and returns it
    public JSONArray drinksToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Drink d : drinksOrdered) {
            jsonArray.put(d.toJson());
        }
        return jsonArray;
    }
}
