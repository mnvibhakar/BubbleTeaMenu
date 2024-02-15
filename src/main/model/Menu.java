package model;

import model.drinks.BrownSugarBlack;
import model.drinks.Classic;
import model.drinks.MangoFruitTea;
import model.drinks.MatchaLatte;

import java.util.ArrayList;
import java.util.Collections;

/*
Represents the menu, with a list of all current drinks
 */
public class Menu {

    private ArrayList<Drink> drinks; //List of drinks on the menu
    private Drink drink1;
    private Drink drink2;
    private Drink drink3;
    private Drink drink4;

    // Effects: Creates a new menu with a list of all the current drinks to be sold
    // sets the current specials as specified
    public Menu(String special1, String special2) {
        drink1 = new Classic();
        drink2 = new BrownSugarBlack();
        drink3 = new MatchaLatte();
        drink4 = new MangoFruitTea();
        drinks = new ArrayList<>();
        Collections.addAll(drinks, drink1, drink2, drink3, drink4);
        for (Drink drink : drinks) {
            drink.setSpecial(false);
            if (drink.getName().equals(special1) || drink.getName().equals(special2)) {
                drink.setSpecial(true);
            }
        }
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
}
