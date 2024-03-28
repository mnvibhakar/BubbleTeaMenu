package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/*
represents a list of all orderLogs
 */
public class OrderLogList {

    private ArrayList<OrderLog> orderLogList;

    public OrderLogList() {
        orderLogList = new ArrayList<>();
    }

    //Effects: adds an orderlog to orderLogList
    //Modifies: orderLogList
    public void addOrderLog(OrderLog orderLog) {
        orderLogList.add(orderLog);
    }

    public ArrayList<OrderLog> getOrderLogList() {
        return orderLogList;
    }

    //Effects: returns the orderLog from the orderLogList with the given name
    public OrderLog findOrderLog(String n) {
        for (OrderLog o : orderLogList) {
            if (o.getName().equals(n)) {
                return o;
            }
        }
        return null;
    }

    //Effects: converts the OrderLogList to a JSONObject and returns it
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("order logs", orderLogsToJson());
        return json;
    }

    //Effects: converts the orderLogList to a JSONArray and returns it
    private JSONArray orderLogsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (OrderLog o : orderLogList) {
            jsonArray.put(o.toJson());
        }
        return jsonArray;
    }
}
