
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
@WebFilter(
        filterName = "mainFilter",
        urlPatterns = {"/*"}
)
public class MainFilter implements Filter {

    private final static String SESSION_ID= "SESSIONID";
    private final static String J_SESSION_ID = "JSESSIONID";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            initSession(request);
            chain.doFilter(request, response);
        } catch (IOException | ServletException e) {
            log.error("Error", e);
            MDC.remove(SESSION_ID);
            throw new RuntimeException("Ошибка сервиса");
        }
    }

    private void initSession(ServletRequest request) {
        var session = ((HttpServletRequest) request).getSession(true);
        if (session.isNew()) {
            session.setMaxInactiveInterval(60);
            setSessionIdInMDCLoggerBySessionId(session.getId());
            log.info("Session is new");
        } else {
            var cookies = Arrays.asList(((HttpServletRequest) request).getCookies());
            setSessionIdInMDCLoggerByCookies(cookies);
            log.info("Session is old");
        }
    }

    private void setSessionIdInMDCLoggerByCookies(List<Cookie> cookies) {
        cookies.stream()
                .filter(cookie -> J_SESSION_ID.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findAny()
                .ifPresent(s -> MDC.put(SESSION_ID, s));
    }

    private void setSessionIdInMDCLoggerBySessionId(String sessionId) {
        MDC.put(SESSION_ID, sessionId);
    }
}
