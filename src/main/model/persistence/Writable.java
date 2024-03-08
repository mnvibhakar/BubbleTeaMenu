package model.persistence;

import org.json.JSONObject;

/*
represents an interface for use by classes that can be saved as JSONObjects in json files
 */
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
