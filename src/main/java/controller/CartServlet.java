package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import model.Product;

@Slf4j
@WebServlet(name = "cart", value = {"/cart", "/deleteProduct"})
public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        var cartProducts = (List<Product>) req.getSession().getAttribute("productsByCart");

        if (req.getServletPath().equals("/deleteProduct")) {
            var productId = req.getParameter("productId");
            log.info("delete product from cart: ID {}", productId);
            cartProducts.removeIf(product -> product.getId().equals(productId));
            req.getSession().setAttribute("productsByCart", cartProducts);
        }

        req.getRequestDispatcher("cart.jsp").forward(req, resp);
    }
}

