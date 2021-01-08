<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin</title>
    </head>
    <body>
        <h1>Admin Page</h1>
        <h2>Welcome ${name}</h2>
        <h3>Menu</h3>
        <ul>
            <li><a href="Inventory">Inventory</a></li>
            <li><a href="Login">Logout</a></li>
        </ul>
        <h2>Manage Users</h2>
        <table>
            <tr>
                <th>Email</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Active Status</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>
            <c:forEach items="${users}" var="users">
                <tr>
                    <td>${users.email}</td>
                    <td>${users.firstName}</td>
                    <td>${users.lastName}</td>
                    <td>${users.active}</td>
                    <td>
                        <form action="Admin" method="get">
                            <input type="submit" value="Edit">
                            <input type="hidden" name="action" value="editUser">
                            <input type="hidden" name="thisUser" value="${users.email}">
                        </form>
                    </td>
                    <td>
                        <form action="Admin" method="post">
                            <input type="submit" value="Delete">
                            <input type="hidden" name="action" value="deleteUser">
                            <input type="hidden" name="thisUser" value="${users.email}">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <!--categories-->
        <h2>Categories</h2>
        <table>
            <tr>
                <th>Category</td>
                <th>Edit</td>
                <th>Delete</td>
                    <!--add item number-->
            </tr>
            <c:forEach items="${categories}" var="categories">
                <tr>
                    <td>${categories.categoryName}</td>
                    <td><form action="" method="get">
                            <input type="submit" value="Edit">
                            <input type="hidden" name="action" value="editCat">
                            <input type="hidden" name="thisCat" value="${categories.categoryId}">
                        </form>
                    </td>
                    <td>
                        <form action="" method="post">
                            <input type="submit" value="Delete">
                            <input type="hidden" name="action" value="deleteCat">
                            <input type="hidden" name="thisCat" value="${categories.categoryId}">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <h2>${heading}</h2>
        <c:if test="${thisUser != null}">
            <form action="Admin" method="post">
                Email: <input type="text" name="email" value="${thisUser.email}"><br>
                Password: <input type="password" name="password" value="${thisUser.password}"><br>
                First Name: <input type="text" name="fname" value="${thisUser.firstName}"><br>
                Last Name: <input type="text" name="lname" value="${thisUser.lastName}"><br>
                <input type="submit" value="Save">
                <input type="hidden" name="action" value="save">
            </form>
        </c:if>
        <c:if test="${thisUser == null}">
            <form action="Admin" method="post">
                Email: <input type="text" name="email"><br>
                Password: <input type="password" name="password"><br>
                First Name: <input type="text" name="fname"><br>
                Last Name: <input type="text" name="lname"><br>
                <input type="submit" value="Add User">
                <input type="hidden" name="action" value="addUser">
            </form>
        </c:if>
        <h2>${catheading}</h2>
        <c:if test="${thisCat != null}">
            <form action="" method="post">
                Category ID: <input type="text" name="editCatId" value="${thisCat.categoryId}"><br>
                Category Name: <input type="text" name="editCategoryName" value="${thisCat.categoryName}"><br>
                <input type="submit" value="Save">
                <input type="hidden" name="action" value="saveCat">
<!--                <input type="hidden" name="id" value="${thisCat.categoryId}">-->
            </form>
        </c:if>
        <c:if test="${thisCat == null}">
            <form action="" method="post">
                Category Name: <input type="text" name="addCategoryName" value="${thisCat.categoryName}"><br>
                <input type="submit" value="Add">
                <input type="hidden" name="action" value="addCat">
            </form>
        </c:if>

        <p>${message}</p>
        <p>${test1}</p>
        <p>${test2}</p>
    </body>
</html>
