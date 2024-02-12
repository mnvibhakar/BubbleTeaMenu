package model;

import model.drinks.BrownSugarBlack;
import model.drinks.Classic;
import model.drinks.MangoFruitTea;
import model.drinks.MatchaLatte;

import java.util.ArrayList;
import java.util.Collections;

public class Menu {

    private ArrayList<Drink> drinks;
    private Drink drink1;
    private Drink drink2;
    private Drink drink3;
    private Drink drink4;

    // Effects: Creates a new menu with a list of all the current drinks to be sold
    public Menu() {
        drink1 = new Classic();
        drink2 = new BrownSugarBlack();
        drink3 = new MatchaLatte();
        drink4 = new MangoFruitTea();
        drinks = new ArrayList<Drink>();
        Collections.addAll(drinks, drink1, drink2, drink3, drink4);
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

    // Effects: Sets the drinks to either special or not special based on the given specials
    // Modifies: this.drinks
    public void setSpecials(String special1, String special2) {
        for (Drink drink : drinks) {
            drink.setSpecial(false);
            if (drink.getName().equals(special1) || drink.getName().equals(special2)) {
                drink.setSpecial(true);
            }
        }
    }

    public ArrayList<Drink> getDrinksOrdered() {
        return drinks;
    }
}
