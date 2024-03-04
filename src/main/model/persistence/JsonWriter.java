package model.persistence;

import model.Menu;
import model.OrderLog;

import model.OrderLogList;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;

public class JsonWriter {

    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    //Modifies: this
    //Effects: Writes JSON representation of menu to file
    public void writeMenu(Menu menu) {
        JSONObject json = menu.toJson();
        saveToFile(json.toString(TAB));
    }

    //Modifies: this
    //Effects: Writes JSON representation of order log list to file
    public void writeOrderLogList(OrderLogList orderLogList) {
        JSONObject json = orderLogList.toJson();
        saveToFile(json.toString(TAB));
    }

    //Modifies: this
    //Effects: Writes JSON representation of order log to file
    public void writeOrderLog(OrderLog orderLog) {
        JSONObject json = orderLog.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
