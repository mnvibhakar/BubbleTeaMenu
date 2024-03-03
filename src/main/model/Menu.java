package model;

import model.drinks.BrownSugarBlack;
import model.drinks.Classic;
import model.drinks.MangoFruitTea;
import model.drinks.MatchaLatte;
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
    private Drink drink1;
    private Drink drink2;
    private Drink drink3;
    private Drink drink4;
    private String special1;
    private String special2;

    // Effects: Creates a new menu with a list of all the current drinks to be sold
    // sets the current specials as specified
    public Menu(String s1, String s2) {
        drink1 = new Classic();
        drink2 = new BrownSugarBlack();
        drink3 = new MatchaLatte();
        drink4 = new MangoFruitTea();
        drinks = new ArrayList<>();
        Collections.addAll(drinks, drink1, drink2, drink3, drink4);
        special1 = s1;
        special2 = s2;
        for (Drink drink : drinks) {
            drink.setSpecial(false);
            if (drink.getName().equals(special1) || drink.getName().equals(special2)) {
                drink.setSpecial(true);
            }
        }
    }

    //Effects: adds a drink to the menu
    public void addDrink(Drink d) {
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

    public ArrayList<Drink> getDrinks() {
        return drinks;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("drinks", drinksToJson());
        json.put("special1", special1);
        json.put("special2", special2);
        return json;
    }

    private JSONArray drinksToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Drink d : drinks) {
            jsonArray.put(d.toJson());
        }
        return jsonArray;
    }
}
