package com.healeniumproxy.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PostgresSQLConnection {

        public String dataBaseConnection() {
            // Database credentials
            String url = "jdbc:postgresql://localhost:5432/healenium";
            String user = "postgres";
            String password = "saiteja";
            String result = null;

            // SQL query to fetch data from a table
            String query = "SELECT elements FROM healenium.report ORDER BY create_date DESC LIMIT 1";

            try {
                // Establish a connection
                Connection connection = DriverManager.getConnection(url, user, password);
                System.out.println("Connected to the PostgreSQL server successfully.");

                // Create a statement
                Statement statement = connection.createStatement();

                // Execute the query
                ResultSet resultSet = statement.executeQuery(query);

                // Process the result set
                while (resultSet.next()) {
                    result = resultSet.getString("elements");
                }

                // Close the connection
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return  result;
        }
}
