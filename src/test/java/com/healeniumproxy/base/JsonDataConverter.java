package com.healeniumproxy.base;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;

public class JsonDataConverter {

    JsonObject jsonObject;

    public JsonObject getJsonObject(String filePath) {
        try {
            FileReader fileReader = new FileReader(System.getProperty("user.dir") + filePath);
            jsonObject = JsonParser.parseReader(fileReader).getAsJsonObject();
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
