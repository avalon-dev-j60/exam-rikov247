<%-- 
    По сути это интерфейс. Тег, определяющий разметку.
--%>

<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib prefix="core" tagdir="/WEB-INF/tags/core/" %>

<%-- Атрибуты --%>
<%@attribute name="title"%>

<!DOCTYPE>
<html>
    <head>
        <title>${title}</title> <%-- Встроить сюда --%>
        <meta charset="UTF-8"> <%-- Кодировка --%>
        <link href="${pageContext.servletContext.contextPath}/css/groundwork-setup.css" <%-- Подключаем файл css. ${} - встроится сюда наш веб адрес --%>
              type="text/css" rel="stylesheet">  <%-- rel - описывает как использовать --%>
        <link href="${pageContext.servletContext.contextPath}/css/layout.css"
              type="text/css" rel="stylesheet">
    </head>
    <body>
        <%-- Верхняя часть сайта --%>
        <header>
            <section class="two thirds centered padded">
                <core:navigation/>
            </section>
        </header>
        <%-- Контент (содержимое) здесь МЕНЯЕТСЯ. С помощью doBody получаем тело откуда-то --%>
        <article class="justify">
            <section class="two thirds centered padded">
                <jsp:doBody/>    
            </section>
        </article>
        <%-- Нижняя часть сайта --%>
        <footer>
            <%-- Контент (содержимое) --%>
            <section class="two thirds centered padded">
                <span class="pull-right">
                    Traffic Clicker 2019 &#169;  <%-- &() - не опираясь на кодировку - получаем символы специальные --%>
                </span>
            </section>
        </footer>
    </body>
</html>