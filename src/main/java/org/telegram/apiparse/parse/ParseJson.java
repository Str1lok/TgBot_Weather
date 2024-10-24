package org.telegram.apiparse.parse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ParseJson {
    public JsonObject parseJson(String jsonResponse) {
        if (jsonResponse != null) {
            Gson gson = new Gson();
            return gson.fromJson(jsonResponse, JsonObject.class);
        }
        return null;
    }
}
