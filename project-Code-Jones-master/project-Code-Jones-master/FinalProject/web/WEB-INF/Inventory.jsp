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
            <li><a href="Account">Account Information</a></li>
            <li><a href="Login">Logout</a></li>
        </ul>
        <table>
            <tr>
                <th>Category</th>
                <th>Name</th>
                <th>Price</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>       
            <c:forEach items="${items}" var="itemList">
                <%--<c:if test="${itemList.length gt 0 }">--%>
                <c:if test="${itemList.owner.email == email}">
                    <tr>
                        <td>${itemList.category.categoryName}</td>
                        <td>${itemList.itemName}</td>
                        <td>${itemList.price}</td>
                        <td>
                            <form action="Inventory" method="get">
                                <input type="submit" value="Edit">
                                <input type="hidden" name="action" value="editItem">
                                <input type="hidden" name="thisItem" value="${itemList.itemId}">
                            </form>
                        </td>
                        <td>
                            <form action="Inventory" method="post">
                                <input type="submit" value="Delete">
                                <input type="hidden" name="action" value="deleteItem">
                                <input type="hidden" name="thisItem" value="${itemList.itemId}">
                            </form>
                        </td>
                    </tr>
                </c:if>
                <%--</c:if>--%>
            </c:forEach>
            <%--<c:if test="${itemList.size() eq 0}">--%>
            <!--<p>You have no items, add some...</p>-->
            <%--</c:if>--%>
        </table>
        <h2>${heading}</h2>
        <c:if test="${thisItem != null}">
            <form action="Inventory" method="post">
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
            <form action="Inventory" method="post">
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
        <p>${message}</p>
<!--        <form action="Inventory" method="post">
            <input type="submit" value="Undo Delete Item">
            <input type="hidden" name="action" value="undo">
        </form>-->
    </body>
</html>
