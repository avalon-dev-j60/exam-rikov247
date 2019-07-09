<%-- 
    Тег, описывающий регистрационную форму
--%>

<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib prefix="core" tagdir="/WEB-INF/tags/core/" %>

<form action="${pageContext.servletContext.contextPath}/register"
      method="post">

    <p class="row gap-bottom"> <%-- E-mail --%>
        <input type="email" <%-- input = форма ввода данных --%>
               name="email"
               value="${param.email}" <%-- Чтобы при ошибке в вводе пароля, введенный ранее e-mail оставался в поле e-mail --%>
               placeholder="E-mail"
               required>
    </p>

    <p class="row gap-bottom"> <%-- Пароль --%>
        <input type="password"
               name="password"
               placeholder="Password"
               required>
    </p>

    <p class="row gap-bottom"> <%-- Подтверждение введенного пароля --%>
        <input type="password"
               name="confirmation"
               placeholder="Password confirmation"
               required>
    </p>

    <p class="row"> <%-- Кнопка регистрации --%>
        <button class="pull-right">
            Register
        </button> 
    </p>

</form>
