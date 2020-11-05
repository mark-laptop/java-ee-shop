package ru.ndg.db;

import lombok.extern.log4j.Log4j2;

import java.sql.*;

/**
 * Класс singleton для соединения, отправки и получения данных из базы данных SQLite
 *
 * @author Nikulin D
 **/
@Log4j2
public class DBUtils {
    private static Connection connection;

    private DBUtils() {
    }

    /**
     * Получения коннекта к базе данных
     * @return Connection возвращает коннект к базе данных
     */
    public static Connection getConnection(String url, String username, String password, String driverClassName) {
        Connection localConnection = connection;
        if (localConnection == null) {
            synchronized (DBUtils.class) {
                localConnection = connection;
                if (localConnection == null) {
                    try {
                        Class.forName(driverClassName);
                        connection = DriverManager.getConnection(url, username, password);
                    } catch (Exception e) {
                        log.error(e);
                    }
                }
            }
        }
        return connection;
    }
}