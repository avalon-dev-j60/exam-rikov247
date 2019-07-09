/*
 * Будет выводить страничку Регистрации
 */
package ru.avalon.java.trafficClicker.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegiterServlet extends HttpServlet {

    private static final String JSP = "/WEB-INF/pages/auth/register.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(JSP).forward(request, response);
    }

}
