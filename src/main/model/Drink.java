package model;

import model.Ingredient;
import java.util.ArrayList;
import java.util.Collections;

import model.persistence.Writable;
import org.json.JSONObject;
import org.json.JSONArray;


/*
Represents the base attributes of a drink, including name, ingredients, price, toppings, size, sugar, and ice
 */
public class Drink implements Writable {

    protected String name;                         // Name of the drink
    protected ArrayList<Ingredient> ingredients;   // Ingredients included in the drink
    protected double price;                        // price of the drink
    protected ArrayList<String> toppings;          // List of toppings in the drink
    protected char size;                           // size of the drink
    protected double sugar;                        // amount of sugar in the drink
    protected double ice;                          // amount of ice in the drink
    protected boolean isSpecial;                   // details if the drink is on special or not

    // Effects: Creates a drink with the specified characteristics
    //          Discounts drink by 20% if it's on special
    public Drink(String n, double p, ArrayList<Ingredient> i, boolean s) {
        toppings = new ArrayList<>();
        ingredients = new ArrayList<>();
        name = n;
        price = p;
        ingredients.addAll(i);
        isSpecial = s;
    }

    //Modifies: ingredients
    //Effects: updates the amounts of the given ingredients based on the size, ice, and toppings of the drink
    public void updateIngredients() {
        if (size == 'l') {
            for (Ingredient i : ingredients) {
                if (i.getType().equals("tea base")) {
                    i.addAmount(100);
                } else if (i.getType().equals("fruit")) {
                    i.addAmount(1);
                } else if (i.getType().equals("milk")) {
                    i.addAmount(20);
                }
            }
        }
        for (Ingredient i : ingredients) {
            if (i.getType().equals("tea base")) {
                i.addAmount(100 * (1 - ice));
                i.addAmount(-50 * toppings.size());
            }
        }
    }

    // Requires: 0 <= exTop <= 2, 0 <= ice <= 1, 0 <= sugar <= 1
    // Effects: Applies the given specifications to the drink
    //          updates the price of the drink based on the given specifications
    //Modifies: This
    public void updateDrink(String size, ArrayList<String> exTop, double ice, double sugar) {
        if (size.equals("l")) {
            this.size = 'l';
            price += 0.5;
        } else {
            this.size = 's';
        }
        if (isSpecial) {
            price *= 0.8;
        }
        price += 0.5 * exTop.size();
        this.ice = ice;
        this.sugar = sugar;
        toppings.addAll(exTop);
    }

    //Effects: converts the drink to a JSONObject and returns it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("ingredients", ingredientsToJson());
        json.put("price", price);
        json.put("toppings", toppingsToJson());
        json.put("size", size);
        json.put("ice", ice);
        json.put("sugar", sugar);
        json.put("special", isSpecial);
        return json;
    }

    //returns toppings in the drink as a JSONArray
    private JSONArray toppingsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (String t : toppings) {
            jsonArray.put(toppingToJson(t));
        }
        return jsonArray;
    }

    //Effects: returns a topping as a JSON object
    private JSONObject toppingToJson(String t) {
        JSONObject json = new JSONObject();
        json.put("name", t);
        return json;
    }

    //Effects: returns the ingredients in the drink as a JSONArray
    public JSONArray ingredientsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Ingredient i : ingredients) {
            jsonArray.put(i.toJson());
        }

        return jsonArray;
    }

    //Effects: Adds a new ingredient with the given specifications
    public void addIngredient(String t, String n, int a) {
        ingredients.add(new Ingredient(t,n,a));
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

    public boolean getIsSpecial() {
        return this.isSpecial;
    }

    public char getSize() {
        return this.size;
    }
}

