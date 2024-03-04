package model.persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.ArrayList;

import model.*;
import org.json.*;

public class JsonReader {

    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    public Menu readMenu() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMenu(jsonObject);
    }

    public OrderLog readOrderLog() throws  IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseOrderLog(jsonObject);
    }

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

    private OrderLogList parseOrderLogList(JSONObject jsonObject) {
        OrderLogList orderLogList = new OrderLogList();
        addOrderLogsToOrderLogList(orderLogList, jsonObject);
        return orderLogList;
    }

    private void addOrderLogsToOrderLogList(OrderLogList orderLogList, JSONObject jsonObject) {
        JSONArray jsonArray = new JSONArray();
        for (Object json : jsonArray) {
            JSONObject nextOrderLog = (JSONObject) json;
            OrderLog orderLog = parseOrderLog(nextOrderLog);
            orderLogList.addOrderLog(orderLog);
        }
    }

    private Menu parseMenu(JSONObject jsonObject) {
        String special1 = jsonObject.getString("special1");
        String special2 = jsonObject.getString("special2");
        Menu menu = new Menu(special1, special2);
        addDrinksToMenu(menu, jsonObject);
        return menu;
    }

    //Modifies: menu
    //Effects:
    private void addDrinksToMenu(Menu menu, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("drinks");
        for (Object json : jsonArray) {
            JSONObject nextDrink = (JSONObject) json;
            Drink drink = getDrink(nextDrink);
            menu.addDrink(drink);
        }
    }

    private OrderLog parseOrderLog(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        OrderLog orderLog = new OrderLog(name);
        addOrdersToOrderLog(orderLog, jsonObject);
        return orderLog;
    }

    private void addOrdersToOrderLog(OrderLog orderLog, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray(("orders"));
        for (Object json : jsonArray) {
            JSONObject nextOrder = (JSONObject) json;
            Order order = getOrder(nextOrder);
            orderLog.addOrder(order);
        }
    }

    private Order getOrder(JSONObject nextOrder) {
        ArrayList<Drink> drinks = getDrinks(nextOrder.getJSONArray("drinks"));
        Order order = new Order();
        for (Drink d : drinks) {
            order.addDrink(d);
        }
        return order;
    }

    private ArrayList<Drink> getDrinks(JSONArray nextDrinks) {
        ArrayList<Drink> drinks = new ArrayList<>();
        for (Object json : nextDrinks) {
            JSONObject nextDrink = (JSONObject) json;
            Drink drink = getDrink(nextDrink);
            drinks.add(drink);
        }
        return drinks;
    }

    private void addDrinksToOrderLog(OrderLog orderLog, JSONObject jsonObject) {

    }

    private Drink getDrink(JSONObject drinkJson) {
        String name = drinkJson.getString("name");
        double price = drinkJson.getInt("price");
        ArrayList<Ingredient> ingredients = getIngredients(drinkJson.getJSONArray("ingredients"));
        boolean special = drinkJson.getBoolean("special");
        Drink drink = new Drink(name, price, ingredients, special);
        return drink;
    }

    private ArrayList<String> getToppings(JSONArray jsonArray) {
        ArrayList<String> toppings = new ArrayList<>();
        for (Object j : jsonArray) {
            JSONObject json = (JSONObject) j;
            String topping = json.getString("name");
            toppings.add(topping);
        }
        return toppings;
    }

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
