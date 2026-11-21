
package com.profile;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDBConnection {

    public static void main(String[] args) {

        Connection conn = DBConnection.getConnection();

        if (conn != null) {
            System.out.println("Connection successful!");

            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Connection unsuccessful!");
        }
    }
}
