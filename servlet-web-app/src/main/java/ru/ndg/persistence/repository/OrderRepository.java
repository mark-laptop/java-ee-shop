package ru.ndg.persistence.repository;

import lombok.extern.log4j.Log4j2;
import java.sql.*;

@Log4j2
public class OrderRepository {

    private final Connection connection;

    public OrderRepository(Connection connection) {
        this.connection = connection;
    }
}
