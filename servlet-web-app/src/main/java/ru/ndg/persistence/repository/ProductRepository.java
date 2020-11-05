package ru.ndg.persistence.repository;

import lombok.extern.log4j.Log4j2;
import ru.ndg.persistence.model.Product;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ProductRepository {

    private static final String SQL_GET_ALL = "SELECT id, name, description, price FROM Product";
    private static final String SQL_GET_BY_ID = "SELECT id, name, description, price FROM Product WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM Product WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO Product (name, description, price) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE Product SET name = ?, description = ?, price = ? WHERE id = ?";
    private final Connection connection;

    public ProductRepository(Connection connection) {
        this.connection = connection;
        createTableProduct();
        fillTable();
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_GET_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getBigDecimal("price"));
                products.add(product);
            }
        } catch (SQLException e) {
            log.error("Error get all products {}", e);
            throw new RuntimeException(e);
        }
        return products;
    }

    public Product getProductById(Integer id) {
        Product product = new Product();
        if (id == null) {
            log.error("Id must not be null");
            return product;
        }
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_GET_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            product.setId(resultSet.getInt("id"));
            product.setName(resultSet.getString("name"));
            product.setDescription(resultSet.getString("description"));
            product.setPrice(resultSet.getBigDecimal("price"));
        } catch (SQLException e) {
            log.error("Error get product by id {}", e);
            throw new RuntimeException(e);
        }
        return product;
    }

    public void deleteProductById(Integer id) {
        if (id == null) {
            log.error("Id must not be null");
            return;
        }
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_DELETE_BY_ID)) {
            statement.setInt(1, id);
            int row = statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error delete product {}", e);
            throw new RuntimeException(e);
        }
    }

    public void insertProduct(Product product) {
        if (product == null) {
            log.error("Product must not be null");
            return;
        }
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_INSERT)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setBigDecimal(3, product.getPrice());
            int row = statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error insert product {}", e);
            throw new RuntimeException(e);
        }
    }

    public void updateProduct(Product product) {
        if (product == null) {
            log.error("Product must not be null");
            return;
        }
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_UPDATE_BY_ID)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setBigDecimal(3, product.getPrice());
            statement.setInt(4, product.getId());
            int row = statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error update product {}", e);
            throw new RuntimeException(e);
        }
    }

    private void createTableProduct() {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS Product\n" +
                        "(\n" +
                        "    id INTEGER PRIMARY KEY AUTO_INCREMENT,\n" +
                        "    name VARCHAR(150) NOT NULL,\n" +
                        "    description VARCHAR(255) NOT NULL,\n" +
                        "    price DECIMAL(15, 2) NOT NULL\n" +
                        ");\n" +
                        "\n" +
                        "CREATE INDEX IX_Product_name ON Product (name);")) {
            statement.execute();
        } catch (SQLException e) {
            log.error("Error create table product {}", e);
            throw new RuntimeException(e);
        }
    }

    private void fillTable() {
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_INSERT)) {
            for (int i = 1; i <= 10; i++) {
                statement.setString(1, "Product №" + i);
                statement.setString(2, "Product №" + i);
                statement.setBigDecimal(3, new BigDecimal(i * 100));
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            log.error("Error create table product {}", e);
            throw new RuntimeException(e);
        }
    }
}
