package ru.ndg.persistence.repository;

import java.sql.Connection;

public class OrderRepository {

    private final Connection connection;

    public OrderRepository(Connection connection) {
        this.connection = connection;
    }
}
