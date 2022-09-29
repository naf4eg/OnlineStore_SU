package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = AuthServlet.SERVLET_NAME, value = AuthServlet.SERVLET_URL)
public class AuthServlet extends HttpServlet {

    public static final String SERVLET_NAME = "auth";
    public static final String SERVLET_URL = "/auth";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //req.setAttribute("productsByCart", RepositoryService.getProductsByCarts());
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var userNameParam = req.getParameter("username");
        var passwordParam = req.getParameter("password");
        req.setAttribute("username", userNameParam);
        req.setAttribute("password", passwordParam);
        req.getRequestDispatcher("/").forward(req, resp);
    }
}
