package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
        execute(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        execute(req, resp);
    }

    private void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("==> Call front controller");
        var session = req.getSession();
        if (isAuthed(session)) {
            req.getServletContext().getRequestDispatcher("/catalog").forward(req, resp);
        } else {
            req.getServletContext().getRequestDispatcher("/auth").forward(req, resp);
        }
    }

    private boolean isAuthed(HttpSession session) {
        var userName = (String) session.getAttribute("username");
        var password = (String) session.getAttribute("password");
        return !session.isNew() && Objects.nonNull(userName) && Objects.nonNull(password);
    }
}
