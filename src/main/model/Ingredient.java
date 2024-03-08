package model;

import model.persistence.JsonReader;
import model.persistence.Writable;
import org.json.JSONObject;

/*
Represents an ingredient, with a name and an amount
 */
public class Ingredient implements Writable {

    private String type;
    private String name;  // Name of the ingredient
    private int amount;   // Amount of the ingredient, uses various measurements based on the ingredient type

    // Effects: sets the characteristics of the ingredient as desired
    public Ingredient(String t, String n, int a) {
        type = t;
        name = n;
        amount = a;
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

    //Effects: converts the ingredient to a jsonObject and returns it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("type", type);
        json.put("name", name);
        json.put("amount", amount);
        return json;
    }

    public String getName() {
        return this.name;
    }

    public int getAmount() {
        return  this.amount;
    }

    public String getType() {
        return type;
    }
}
