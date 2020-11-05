package ru.ndg.controller;

import lombok.extern.log4j.Log4j2;
import ru.ndg.persistence.repository.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@Log4j2
@WebServlet(name = "productServlet", urlPatterns = "/product_view")
public class ProductServlet extends HttpServlet {

    private ProductRepository productRepository;

    @Override
    public void init() throws ServletException {
        this.productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
        if (this.productRepository == null) {
            throw new RuntimeException("Product repository is not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("/product_view".equals(req.getServletPath())) {
            showProductPage(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {}

    @Override
    public void destroy() {}

    private void showProductPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = null;
        try {
            id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("product", this.productRepository.getProductById(id));
            getServletContext().getRequestDispatcher("/WEB-INF/templates/product_view.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            log.error("Error parser param id {}", e);
            throw new RuntimeException(e);
        }
    }
}