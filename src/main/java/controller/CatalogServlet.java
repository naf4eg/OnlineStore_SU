package controller;

import dao.RepositoryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import model.Product;

@Slf4j
@WebServlet(name = "catalog", value = {"/catalog", "/addProduct"})
public class CatalogServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        execute(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        execute(req, resp);
    }

    private void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession();
        initCatalogBySession(session);
        if (req.getServletPath().equals("/addProduct")) {
            var productId = req.getParameter("productId");
            addProductToCart(productId, session);
        }
        req.getRequestDispatcher("catalog.jsp").forward(req, resp);
    }

    private void initCatalogBySession(HttpSession session) {
        var hasNotProductsInSession = Objects.isNull(session.getAttribute("products"));

        if (hasNotProductsInSession) {
            RepositoryService
                    .findProducts()
                    .ifPresent(products -> session.setAttribute("products", products));
        }
    }

    private void addProductToCart(String productId, HttpSession session) {
        var sessionProducts = findProductsFromSession(session);
        var cartProducts = findCartProductsBySession(session);
        var product = findProductByProductId(sessionProducts, productId);
        cartProducts.add(product);
        session.setAttribute("productsByCart", cartProducts);
    }

    private Product findProductByProductId(List<Product> products, String productId) {
        return products.stream().filter(value -> productId.equals(value.getId())).findAny().get();
    }

    private List<Product> findProductsFromSession(HttpSession session) {
        var sessionProducts = (List<Product>) session.getAttribute("products");
        if (Objects.isNull(sessionProducts)) sessionProducts = new ArrayList<Product>();
        return sessionProducts;
    }

    private List<Product> findCartProductsBySession(HttpSession session) {
        var cartProducts = (List<Product>) session.getAttribute("productsByCart");
        if (Objects.isNull(cartProducts)) cartProducts = new ArrayList<Product>();
        return cartProducts;
    }
}
