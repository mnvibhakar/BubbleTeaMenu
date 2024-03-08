package model.persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.ArrayList;

import model.*;
import model.exceptions.DuplicateNameException;
import org.json.*;

/*
represents a class that converts json files to objects usable by the rest of the program
uses code from the CPSC 210 Json Serialization Demo project
 */
public class JsonReader {

    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    //Effects: reads a menu from the json file
    public Menu readMenu() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMenu(jsonObject);
    }

    //Effects: reads an orderLog from the json file
    public OrderLog readOrderLog() throws  IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseOrderLog(jsonObject);
    }

    //Effects: reads anOrderLogList from the json file
    public OrderLogList readOrderLogList() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseOrderLogList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    //Effects: returns the orderLogList corresponding to the given jsonObject
    private OrderLogList parseOrderLogList(JSONObject jsonObject) {
        OrderLogList orderLogList = new OrderLogList();
        addOrderLogsToOrderLogList(orderLogList, jsonObject);
        return orderLogList;
    }

    //Modifies: orderLogList
    //Effects: adds order logs from jsonObject to the orderLogList
    private void addOrderLogsToOrderLogList(OrderLogList orderLogList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("order logs");
        for (Object json : jsonArray) {
            JSONObject nextOrderLog = (JSONObject) json;
            OrderLog orderLog = parseOrderLog(nextOrderLog);
            orderLogList.addOrderLog(orderLog);
        }
    }

    //Effects: returns a menu corresponding to the given jsonObject
    private Menu parseMenu(JSONObject jsonObject) {
        Menu menu = new Menu();
        addDrinksToMenu(menu, jsonObject);
        return menu;
    }

    //Modifies: menu
    //Effects: adds drinks read from the jsonObject to the menu
    private void addDrinksToMenu(Menu menu, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("drinks");
        for (Object json : jsonArray) {
            JSONObject nextDrink = (JSONObject) json;
            Drink drink = getDrink(nextDrink);
            try {
                menu.addDrink(drink);
            } catch (DuplicateNameException e) {
                throw new Error("incorrect name duplication occurrence");
            }
        }
    }

    //Effects: returns an orderLog read from the json file
    private OrderLog parseOrderLog(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        OrderLog orderLog = new OrderLog(name);
        addOrdersToOrderLog(orderLog, jsonObject);
        return orderLog;
    }

    //Modifies: orderLog
    //Effects: adds orders read from the given jsonObject to the given order log
    private void addOrdersToOrderLog(OrderLog orderLog, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray(("orders"));
        for (Object json : jsonArray) {
            JSONObject nextOrder = (JSONObject) json;
            Order order = getOrder(nextOrder);
            orderLog.addOrder(order);
        }
    }

    //Effects: returns an order read from the given jsonObject
    private Order getOrder(JSONObject nextOrder) {
        ArrayList<Drink> drinks = getDrinks(nextOrder.getJSONArray("drinks"));
        Order order = new Order();
        for (Drink d : drinks) {
            order.addDrink(d);
        }
        return order;
    }

    //Effects: returns a list of drinks contained in the given JSONArray
    private ArrayList<Drink> getDrinks(JSONArray nextDrinks) {
        ArrayList<Drink> drinks = new ArrayList<>();
        for (Object json : nextDrinks) {
            JSONObject nextDrink = (JSONObject) json;
            Drink drink = getDrink(nextDrink);
            drinks.add(drink);
        }
        return drinks;
    }

    //Effects:  returns the drink contained in the given JSONObject
    private Drink getDrink(JSONObject drinkJson) {
        String name = drinkJson.getString("name");
        double price = drinkJson.getInt("price");
        ArrayList<Ingredient> ingredients = getIngredients(drinkJson.getJSONArray("ingredients"));
        boolean special = drinkJson.getBoolean("special");
        Drink drink = new Drink(name, price, ingredients, special);
        return drink;
    }

    //Effects: returns the list of ingredients contained in the given JSONArray
    private ArrayList<Ingredient> getIngredients(JSONArray jsonArray) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for (Object j : jsonArray) {
            JSONObject json = (JSONObject) j;
            String type = json.getString("type");
            String name = json.getString("name");
            int amount = json.getInt("amount");
            Ingredient ingredient = new Ingredient(type, name, amount);
            ingredients.add(ingredient);
        }
        return ingredients;
    }

}
