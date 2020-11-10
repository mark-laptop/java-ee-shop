package ru.ndg.controller;

import lombok.extern.log4j.Log4j2;
import ru.ndg.persistence.model.Product;
import ru.ndg.persistence.repository.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@Log4j2
@WebServlet(name = "catalogServlet", urlPatterns = {"/catalog", "/catalog/edit", "/catalog/delete", "/catalog/new", "/catalog/update", "/catalog/create"})
public class CatalogServlet extends HttpServlet {

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
        if ("/catalog".equals(req.getServletPath())) {
            showAllProducts(req, resp);
        } else if ("/catalog/edit".equals(req.getServletPath())) {
            showEditProductPage(req, resp);
        } else if ("/catalog/delete".equals(req.getServletPath())) {
            deleteProduct(req, resp);
        } else if ("/catalog/new".equals(req.getServletPath())) {
            showCreateProductPage(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("/catalog/update".equals(req.getServletPath())) {
            updateProduct(req, resp);
        } else if ("/catalog/create".equals(req.getServletPath())) {
            createProduct(req, resp);
        }
    }

    @Override
    public void destroy() {}

    private void showCreateProductPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Product product = new Product();
        req.setAttribute("product", product);
        req.setAttribute("action", "/catalog/create");
        getServletContext().getRequestDispatcher("/WEB-INF/templates/product.jsp").forward(req, resp);
    }

    private void showAllProducts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("products", this.productRepository.getAllProducts());
        log.info("Get all products");
        getServletContext().getRequestDispatcher("/WEB-INF/templates/catalog.jsp").forward(req, resp);
    }

    private void showEditProductPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = null;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException e) {
            log.error("Error parser param id {}", e);
            throw new RuntimeException(e);
        }
        Product product = this.productRepository.getProductById(id);
        req.setAttribute("product", product);
        req.setAttribute("action", "/catalog/update");
        getServletContext().getRequestDispatcher("/WEB-INF/templates/product.jsp").forward(req, resp);
    }

    private void createProduct(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Product product = new Product();
            product.setName(req.getParameter("name"));
            product.setDescription(req.getParameter("description"));
            String price = req.getParameter("price");
            if (price.isEmpty()) {
                product.setPrice(new BigDecimal("0.0"));
            } else {
                product.setPrice(new BigDecimal(price));}
            this.productRepository.insertProduct(product);
            resp.sendRedirect(getServletContext().getContextPath() + "/catalog");
        } catch (Exception e) {
            log.error("Error create product {}", e);
            throw new RuntimeException(e);
        }
    }

    private void updateProduct(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Product product = new Product();
            product.setId(Integer.parseInt(req.getParameter("id")));
            product.setName(req.getParameter("name"));
            product.setDescription(req.getParameter("description"));
            String price = req.getParameter("price");
            if (price.isEmpty()) {
                product.setPrice(new BigDecimal("0.0"));
            } else {
                product.setPrice(new BigDecimal(price));
            }
            this.productRepository.updateProduct(product);
            resp.sendRedirect(getServletContext().getContextPath() + "/catalog");
        } catch (Exception e) {
            log.error("Error update product {}", e);
            throw new RuntimeException(e);
        }
    }

    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = null;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException e) {
            log.error("Error parser param id {}", e);
            throw new RuntimeException(e);
        }
        this.productRepository.deleteProductById(id);
        resp.sendRedirect(getServletContext().getContextPath() + "/catalog");
    }
}