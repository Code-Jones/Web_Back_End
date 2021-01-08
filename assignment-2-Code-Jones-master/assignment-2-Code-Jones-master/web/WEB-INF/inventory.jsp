<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inventory</title>
    </head>
    <body>
        <h1>Inventory Page</h1>
        <h1>Welcome ${name}</h1>
        <h3>Menu</h3>
        <ul>
            <c:if test="{user.getRole().getRoleId() = 1 || user.getRole().getRoleId() = 3}">
                <li><a href="Admin">Admin</a></li>
                </c:if>
            <li><a href="login">Logout</a></li>
        </ul>
        <h2>Manage Users</h2>
        <table>
            <%--<c:if test="${items.size() gt 0 }">--%>
            <tr>
                <th>Category</th>
                <th>Name</th>
                <th>Price</th>
                <th>Active Status</th>
                <th>Delete</th>
            </tr>
            
            <c:forEach items="${items}" var="items">
                <tr>
                    <td>${items.category.categoryName}</td>
                    <td>${items.itemName}</td>
                    <td>${items.price}</td>
                    <td>
                        <form action="inventory" method="get">
                            <input type="submit" value="Edit">
                            <input type="hidden" name="action" value="editItem">
                            <input type="hidden" name="thisItem" value="${items.itemId}">
                        </form>
                    </td>
                    <td>
                        <form action="inventory" method="post">
                            <input type="submit" value="Delete">
                            <input type="hidden" name="action" value="deleteItem">
                            <input type="hidden" name="thisItem" value="${items.itemId}">
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <%--</c:if>--%>
            <%--<c:if test="${items.size() eq 0}">--%>
                <!--<p>You have no items, add some...</p>-->
            <%--</c:if>--%>
        </table>
        <h2>${heading}</h2>
        <c:if test="${thisItem != null}">
            <form action="inventory" method="post">
                Category: <select name="category">
                    <c:forEach items="${categories}" var="categories">
                        <option value="${categories.categoryId}" ${categories.categoryId == categoiries.categoryId ? 'selected' : ''}>${categories.categoryName}</option>
                    </c:forEach>
                </select><br>
                Name: <input type="text" name="itemName" value="${thisItem.itemName}"><br>
                Price:<input type="number" name="price" value="${thisItem.price}"><br>
                <input type="submit" value="Save">
                <input type="hidden" name="action" value="saveItem">
            </form>
        </c:if>
        <c:if test="${thisItem == null}">
            <form action="inventory" method="post">
                Category: <select name="category">
                    <c:forEach items="${categories}" var="categories">
                        <option value="${categories.categoryId}" ${categories.categoryId == categoiries.categoryId ? 'selected' : ''}>${categories.categoryName}</option>
                    </c:forEach>
                </select><br>
                Name: <input type="text" name="itemName" value="${thisItem.itemName}"><br>
                Price:<input type="number" name="price" value="${thisItem.price}"><br>
                <input type="submit" value="Add Item">
                <input type="hidden" name="action" value="addItem">
            </form>
        </c:if>
        <p>${testing}</p>
        <p>${message}</p>
    </body>
</html>
