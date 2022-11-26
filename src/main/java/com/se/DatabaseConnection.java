package com.se;

import java.sql.Connection;
import java.sql.DriverManager;

//singleton class is a class that can have only one object (an instance of the class) at a time.
public class DatabaseConnection {
    static Connection connection = null;

    public static Connection getConnection() {
        if(connection != null ) return connection;

        String user = "sql6580886";
        String pass = "1xt2y6rjlZ";
        String db = "sql6580886";

        return getConnection(user, pass, db);
    }

    private static Connection getConnection(String user, String pass, String db) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://sql6.freesqldatabase.com/"+db+"?user="+user+"&password="+pass);
        }
        catch (Exception exception) {
            exception.getStackTrace();
        }

        return connection;
    }
}
