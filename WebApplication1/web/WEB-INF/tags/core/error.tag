<%-- 
    Тег для обработки ошибок при регистрации и авторизации
--%>

<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="exeption" %>

<c:if test="${not empty exception}">
    <div class="box error gap-bottom gap-top">
        ${exception.message} <%-- Достает метод getMessage из любого exception (так как он там точно есть) --%>
    </div>
</c:if>