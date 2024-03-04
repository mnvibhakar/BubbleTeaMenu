package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderLogList {

    private ArrayList<OrderLog> orderLogs;

    public OrderLogList() {
        orderLogs  = new ArrayList<>();
    }

    public void addOrderLog(OrderLog orderLog) {
        orderLogs.add(orderLog);
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("order logs", orderLogsToJson());
        return json;
    }

    private JSONArray orderLogsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (OrderLog o : orderLogs) {
            jsonArray.put(o.toJson());
        }
        return jsonArray;
    }
}
