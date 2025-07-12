package com.ddr.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {

	public static Connection connection;

	public static Connection getConnection() {
		try {
			if (connection != null && !connection.isClosed()) {
				return connection;
			}

			Class.forName("oracle.jdbc.OracleDriver");

			connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
        return connection;
	}

}
