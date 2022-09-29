package controller;

import dao.RepositoryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import model.Product;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

@Slf4j
@WebServlet(name = "catalog", value = {"/catalog", "/addProduct"})
public class CatalogServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<Product> products1 = (List<Product>) req.getSession().getAttribute("products");
        if (Objects.isNull(products1)) {
            RepositoryService
                    .findProducts()
                    .ifPresent(products2 -> req.getSession().setAttribute("products", products2));
        }

        if (req.getServletPath().equals("/addProduct")) {
            List<Product> products2 = (List<Product>) req.getSession().getAttribute("products");
            List<Product> cartProducts = (List<Product>) req.getSession().getAttribute("productsByCart");
            if (Objects.isNull(cartProducts)) cartProducts = new ArrayList<Product>();

            String productId = req.getParameter("productId");
            Product product = products2.stream().filter(value -> productId.equals(value.getId())).findAny().get();
            cartProducts.add(product);
            req.getSession().setAttribute("productsByCart", cartProducts);
            log.info("add product from catalog: ID {}", productId);
        }

        req.getRequestDispatcher("catalog.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("catalog.jsp").forward(req, resp);
    }
}
