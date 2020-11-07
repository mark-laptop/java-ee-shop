package ru.ndg.controller;

import ru.ndg.persistence.repository.OrderRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "orderServlet", urlPatterns = "/order")
public class OrderServlet extends HttpServlet {

    private OrderRepository orderRepository;

    @Override
    public void init() throws ServletException {
        this.orderRepository = (OrderRepository) getServletContext().getAttribute("orderRepository");
        if (this.orderRepository == null) {
            throw new RuntimeException("Product repository is not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/templates/order.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {}

    @Override
    public void destroy() {}
}