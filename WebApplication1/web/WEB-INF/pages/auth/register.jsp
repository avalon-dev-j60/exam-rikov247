<%-- 
Страница регистрации
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="core" tagdir="/WEB-INF/tags/core/" %>
<%@taglib prefix="auth" tagdir="/WEB-INF/tags/auth/" %>

<core:layout title="Registration">

    <div class="one half centered">
        <h2>Registration</h2>
        <auth:register/>
    </div>

</core:layout>