/**
 * Сюда встроим все, что нужно - из сервлета вызываем методы этого класса
 */
package ru.avalon.java.trafficClicker.services;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.ValidationException;
import ru.avalon.java.blog.entities.User;
import ru.avalon.java.blog.service.UserService;
import static ru.avalon.java.trafficClicker.helpers.Validation.*;

@Stateless
public class AuthService {

    @EJB
    UserService userService;

    @Inject
    HttpSession session;

    // авторизация пользователя
    public void signIn(String email, String password) throws ValidationException {
        require(email, "Email is required!"); // email не пустой
        require(password, "Password is required!"); // password не пустой
        User user = userService.findByEmail(email); // ищем пользователя - нашли
        requireNonNull(user, "User does not exist!"); // проверяем пользователя
        requireEquals(user.getPassword(), password, "Invalid password!");

        session.setAttribute("user", email); // сохраняем email пользователя в сессии
    }

    // Отключение
    public void signOut() {
        session.invalidate(); // стираем все данные пользователя
    }

    // авторизовался ли пользователь?
    public boolean isSignedIn() {
        return session.getAttribute("user") != null;
    }

    // регистрация пользователя
    public void register(String email, String password, String passwordConfirmation) throws ValidationException {
        require(email, "Email is required!");
        require(password, "Password is required!");
        require(passwordConfirmation, "Password Confirmation is required!");

        requireEquals(password, passwordConfirmation, "Password does not match confirmation!"); // пароль не совпадает с паролем, введенным повторно

        User user = userService.findByEmail(email); // проверка на то, что пользователь уже есть
        requireNull(user, "User already exist!");

        user = new User(email, password); // создаем Usera
        userService.create(user);
    }

}
