<%-- 
 Форма для входа.
Нужен класс, который будет работать с пользователем.
Нужен сервлет, который будет работать отдельно
--%>

<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib prefix="core" tagdir="/WEB-INF/tags/core/" %>

<form action="${pageContext.servletContext.contextPath}/sign-in"
      method="post">

    <code:error/> <%-- Вывод возможных ошибок --%>

    <%-- ЛОГИН.Ряд с элементами и отступом вниз --%>
    <p class="row gap-bottom">
        <input type="email" 
               name="email" 
               value="${param.email}" <%-- Если ошибка - то ранее написанный email автоматически добавляется --%>
               placeholder="Email address" <%-- Отображается то, что будет до того, как что то написать --%>
               required> <%-- Обязательно --%>
    </p>
    <%-- ПАРОЛЬ.Ряд с элементами и отступом вниз --%>
    <p class="row gap-bottom">
        <input type="password"
               name="password"
               placeholder="Password"
               required>
    </p>     
    <%-- ССЫЛКА НА РЕГИСТРАЦИЮ.Ряд с элементами и отступом вниз --%>   
    <p class="row gap-bottom">
        <a href="${pageContext.servletContext.contextPath}/register"> 
            Registration
        </a>
        <button class="pull-right">
            Sign in
        </button>
    </p>

</form>      