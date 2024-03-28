package model;

import model.persistence.Writable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/*
represents a list of all orders from a period, with a name describing the period
 */
public class OrderLog implements Writable {

    private String name;
    private ArrayList<Order> orders;

    public OrderLog(String n) {
        name = n;
        orders = new ArrayList<>();
    }

    //Effects: adds an order to the order log
    public void addOrder(Order o) {
        orders.add(o);
    }

    //Effects: converts the orderLog to a JSONObject and returns it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("orders", ordersToJson());
        return json;
    }

    //Effects: returns orders as a JSONArray
    private JSONArray ordersToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Order o : orders) {
            jsonArray.put(o.toJson());
        }
        return jsonArray;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public String getName() {
        return name;
    }

    //Effects: returns the total price of all orders in orders
    public double getTotalPrice() {
        double totalPrice = 0;
        for (Order o : orders) {
            totalPrice += o.getTotalPrice();
        }
        return totalPrice;
    }
}
