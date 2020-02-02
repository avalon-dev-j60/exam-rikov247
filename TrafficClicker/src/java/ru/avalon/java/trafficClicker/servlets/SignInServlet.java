/*
 * Наследуем http-сервлет, чтобы он стал Сервлетом
 */
package ru.avalon.java.trafficClicker.servlets;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import ru.avalon.java.trafficClicker.helpers.RedirectHelper;
import ru.avalon.java.trafficClicker.services.AuthService;

@WebServlet("/sign-in") // Связываем с адресом sign-in
public class SignInServlet extends HttpServlet {

    private static final String JSP = "/WEB-INF/pages/auth/login.jsp";

    @Inject
    AuthService authService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (authService.isSignedIn()) {
            RedirectHelper.redirectBack(request, response);
        } else {
            request.getRequestDispatcher(JSP).forward(request, response); // перенаправляем на страничку sign-in
        }
    }

    // должен взять email и password и попытаться авторизовать пользователя. Не поулчается - ошибка
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        try {
            authService.signIn(email, password); // пытаемся авторизоваться
            RedirectHelper.localRedirect(request, response, "/"); // отправляем на домашнюю страницу
        } catch (ValidationException e) { // чтобы вывести ошибку
            request.setAttribute("exception", e);
            doGet(request, response);
        }
    }

}
