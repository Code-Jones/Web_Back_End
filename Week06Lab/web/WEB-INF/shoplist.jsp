<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Check out my lists</title>
    </head>
    <body>
        <h1>Shopping List</h1>
        <p>Hello, ${username} <a href="ShoppingList?action=logout">Logout</a></p>

        <form action="ShoppingList" method="POST">
            <h2>List</h2> Add Item:
            <input type="text" name="item">
            <input type="submit" value="Add">
            <input type="hidden" name="action" value="add">
        </form>

        <form action="ShoppingList" method="POST">
            <ul>
                <c:forEach items="${items}" var="item">
                    <li><input type="radio" name="item" value="${item}">${item}</li>
                </c:forEach>                
            </ul>

            <input type="submit" value="Delete">
            <input type="hidden" name="action" value="delete">
        </form>
    </body>
</html>
