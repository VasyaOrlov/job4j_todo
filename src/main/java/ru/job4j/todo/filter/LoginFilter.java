package ru.job4j.todo.filter;

import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class LoginFilter implements Filter {

    private final Set<String> url = Set.of("login", "authorization",
            "registration", "addUser", "showMessage");

    /**
     * метод пропускает через себя запрос к сервлетам
     * если запрос по адресу из коллекции url - пропускаем
     * если запрос по адресу /addSession не от пользователя Admin то отправляем на страницу авторизации
     * если пользователь не авторизован (null) отправляем на страницу авторизации
     * @param servletRequest - запрос
     * @param servletResponse - ответ
     * @param filterChain - следующий фильтр
     */
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String uri = req.getRequestURI();
        if (check(uri)) {
            filterChain.doFilter(req, res);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/users/authorization");
            return;
        }
        filterChain.doFilter(req, res);
    }

    private boolean check(String uri) {
        return url.stream().anyMatch(uri::endsWith);
    }
}
