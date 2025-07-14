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

			Class.forName(Constants.DB_DRIVER);

			connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
        return connection;
	}

}
