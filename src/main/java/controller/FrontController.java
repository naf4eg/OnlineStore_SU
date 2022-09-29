package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet(
        name = "front",
        urlPatterns = {"/", "/front"}
)
public class FrontController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession(true);
        log.info("==> Call Front controller {}", session.isNew());

        if (session.isNew()) {
            req.getServletContext().getRequestDispatcher(AuthServlet.SERVLET_URL).forward(req, resp);
        } else {
            //req.getServletContext().getRequestDispatcher(CatalogServlet.SERVLET_URL).forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession(true);
        var userName = (String) req.getAttribute("username");
        var password = (String) req.getAttribute("password");
        var isAuthed = !session.isNew() && Objects.nonNull(userName) && Objects.nonNull(password);
        if (isAuthed) {
            //req.getServletContext().getRequestDispatcher(CatalogServlet.SERVLET_URL).forward(req, resp);
        } else {
            req.getServletContext().getRequestDispatcher(AuthServlet.SERVLET_URL).forward(req, resp);
        }
    }

}
