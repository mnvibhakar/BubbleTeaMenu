package model.persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.ArrayList;

import model.Drink;
import model.Ingredient;
import model.Menu;
import model.OrderLog;
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

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
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
        // TODO
        return null;
    }

    private Drink getDrink(JSONObject drinkJson) {
        String name = drinkJson.getString("name");
        int price = drinkJson.getInt("price");
        ArrayList<Ingredient> ingredients = getIngredients(drinkJson.getJSONArray("ingredients"));
        ArrayList<String> toppings = getToppings(drinkJson.getJSONArray("toppings"));
        boolean special = drinkJson.getBoolean("special");
        Drink drink = new Drink();
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
