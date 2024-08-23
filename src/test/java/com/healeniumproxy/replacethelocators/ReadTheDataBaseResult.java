package com.healeniumproxy.replacethelocators;

import com.healeniumproxy.database.PostgresSQLConnection;
import org.json.JSONArray;
import org.json.JSONObject;

public class ReadTheDataBaseResult {

    public void readTheDataFromDB() {
        PostgresSQLConnection sqlConnection = new PostgresSQLConnection();
        String jsonString = sqlConnection.dataBaseConnection();
        System.out.println(jsonString);

        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray records = jsonObject.getJSONArray("records");
        if(!records.isEmpty()) {
            for(Object obj : records) {
                System.out.println("Locators getting from the database...................");
                JSONObject record = (JSONObject) obj;
                JSONObject failedLocator = record.getJSONObject("failedLocator");
                JSONObject healedLocator = record.getJSONObject("healedLocator");

                String failedLocatorValue = failedLocator.getString("value");
                String failedLocatorType = failedLocator.getString("type");

                String healedLocatorValue = healedLocator.getString("value");
                String healedLocatorType = healedLocator.getString("type");

                System.out.println("failed locator value :" + failedLocatorValue);
                System.out.println(("healed locator value :" + healedLocatorValue));
                LocatorUpdateInClassFiles update = new LocatorUpdateInClassFiles();
                update.updateLocatorsInDirectory("src/test/java/com/healeniumproxy/test",failedLocatorValue,healedLocatorValue);
            }
        }

    }

//    public static void main(String[] args) {
//        ReadTheDataBaseResult dataBaseResult = new ReadTheDataBaseResult();
//        dataBaseResult.readTheDataFromDB();
//    }

}
