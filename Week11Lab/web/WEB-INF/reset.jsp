<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset password</title>
    </head>
    <body>
        <h1>Reset password</h1>
        <p>Please enter your email address to reset your password.</p>
        <form method="post" action="reset">
            Email Address: <input type="text" name="email"><br>
            <input type="hidden" name="action" value="requestreset">
            <input type="submit" value="Submit">
        </form>
        <c:if test="${message != null}">
            <p>${message}</p>
            <a href="login">Login</a>
        </c:if>
    </body>
</html>