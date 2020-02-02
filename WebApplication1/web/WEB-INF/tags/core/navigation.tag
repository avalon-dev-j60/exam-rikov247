<%-- 
    Навигация
--%>

<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib prefix="core" tagdir="/WEB-INF/tags/core/" %>

<nav> <%-- Симантический тег, чтобы стилями затем отменить поведение вывода нижеописанных тегов (чтобы не выглядело списком) --%>
    <ul>
        <li>
            <core:link location="/" title="Home" /> <%-- Обращаемся к тегу link в библиотеке core --%>
        </li>
        <li>
            <core:link location="/about" title="About" />
        </li>
        <li>
            <core:link location="/projects" title="Projects" />
        </li>
        <li class="pull-right">
            <core:link location="/sign-in" title="Sign in" />
        </li>
    </ul>
</nav>