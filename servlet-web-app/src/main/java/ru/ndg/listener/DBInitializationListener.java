package ru.ndg.listener;

import lombok.extern.log4j.Log4j2;
import ru.ndg.db.DBUtils;
import ru.ndg.persistence.repository.OrderRepository;
import ru.ndg.persistence.repository.ProductRepository;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.SQLException;

@Log4j2
@WebListener(value = "DBInitializationListener")
public class DBInitializationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        String jdbcUrl = servletContext.getInitParameter("jdbcUrl");
        String jdbcDriverClassName = servletContext.getInitParameter("jdbcDriverClassName");
        String username = servletContext.getInitParameter("username");
        String password = servletContext.getInitParameter("password");
        if (jdbcUrl == null || jdbcDriverClassName == null || username == null || password == null) {
            log.error("No correct param from database connection");
            throw new IllegalArgumentException("No correct param from database connection");
        }
        Connection connection = DBUtils.getConnection(jdbcUrl, username, password, jdbcDriverClassName);
        sce.getServletContext().setAttribute("jdbcConnection", connection);
        log.info("Open connection from database");
        ProductRepository productRepository = new ProductRepository(connection);
        OrderRepository orderRepository = new OrderRepository(connection);
        sce.getServletContext().setAttribute("productRepository", productRepository);
        sce.getServletContext().setAttribute("orderRepository", orderRepository);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Connection connection = (Connection) sce.getServletContext().getAttribute("jdbcConnection");
        if (connection != null) {
            try {
                connection.close();
                log.info("Close connection database");
            } catch (SQLException e) {
                log.error(e);
            }
        }
    }
}
