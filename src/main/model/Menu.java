package model;

import model.exceptions.DuplicateNameException;
import model.persistence.Writable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/*
Represents the menu, with a list of all current drinks
 */
public class Menu implements Writable {

    private ArrayList<Drink> drinks; //List of drinks on the menu

    // Effects: Creates a new menu with a list of all the current drinks to be sold
    // sets the current specials as specified
    public Menu() {
        drinks = new ArrayList<>();
    }

    //Effects: adds a drink to the menu
    //Requires: drink does not have same name as another drink
    public void addDrink(Drink d) throws DuplicateNameException {
        for (Drink drink : drinks) {
            if (drink.getName().equals(d.getName())) {
                throw new DuplicateNameException("Drink with that name already exists");
            }
        }
        drinks.add(d);
    }

    // Effects: returns the drink on the menu with the given name,
    //          returns null if a drink with that name is not on the menu
    public Drink findDrink(String name) {
        for (Drink drink : drinks) {
            if (drink.name.equals(name)) {
                return drink;
            }
        }
        return null;
    }

    //Modifies: drinks
    //Effects: changes the special status of the drinks based on the given drink names
    public void setSpecials(String s1, String s2) {
        for (Drink drink : drinks) {
            if (drink.getName().equals(s1) || drink.getName().equals(s2)) {
                drink.setSpecial(true);
            } else {
                drink.setSpecial(false);
            }
        }
    }

    public ArrayList<Drink> getDrinks() {
        return drinks;
    }

    //Effects: converts the menu to a JSONObject and returns it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("drinks", drinksToJson());
        return json;
    }

    //Effects: converts drinks to a JSONArray and returns it
    private JSONArray drinksToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Drink d : drinks) {
            jsonArray.put(d.toJson());
        }
        return jsonArray;
    }
}
