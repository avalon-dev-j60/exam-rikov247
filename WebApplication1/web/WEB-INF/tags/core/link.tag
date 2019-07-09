<%-- 
Серверный тег для вывода ссылок 
--%>

<%@tag description="put the tag description here" pageEncoding="UTF-8"%>

<%@attribute name="location" required="true"%> <%-- Обязательный тег --%>

<%@attribute name="title" required="true"%> <%-- Обязательный тег --%>

<a href="${pageContext.servletContext.contextPath}${location}">
    ${title}
</a>