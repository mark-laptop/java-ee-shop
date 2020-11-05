package ru.ndg.persistence.repository;

import java.sql.Connection;

public class OrderItemRepository {

    private final Connection connection;

    public OrderItemRepository(Connection connection) {
        this.connection = connection;
    }
}
