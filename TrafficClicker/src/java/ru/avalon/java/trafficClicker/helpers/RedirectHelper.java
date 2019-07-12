/**
 * Сущностный класс для переадресации
 */
package ru.avalon.java.trafficClicker.helpers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;

public class RedirectHelper {

    private RedirectHelper() {
    } // Чтобы предотвратить создание экземпляров данного класса

    // Переадресация на другие сайты (заданные location)
    public static void redirect(HttpServletRequest request, HttpServletResponse response, String location) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_FOUND); // устанавливаем код ответа в состояние = нашел
        response.setHeader(HttpHeaders.LOCATION, location); // задаем Header
    }

    // Переадресация в пределах создаваемого сайта
    public static void localRedirect(HttpServletRequest request, HttpServletResponse response, String location) throws ServletException, IOException {
        location = request.getContextPath() + location; // добавляем контекстный путь к location
        redirect(request, response, location);
    }

    // Переадресация обратно (от куда пришли)
    public static void redirectBack(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String referer = request.getHeader("Referer"); // получаем место, где мы были раньше (предыдущая страница)
        String currentLocation = request.getRequestURI().toString();

        if (referer == null || referer.equals(currentLocation)) {
            localRedirect(request, response, "/"); // перенаправляем на домашнюю страницу
        } else {
            redirect(request, response, referer); // перенаправляем на страницу, откуда пришли
        }
    }
}
