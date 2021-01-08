<%-- 
    Document   : hello
    Created on : Sep 11, 2020, 4:52:50 PM
    Author     : 786524
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hello World</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <form action="hello" method="post">
            First Name: <input type="text" name="firstname" value="${firstname}"><br>
            Last Name: <input type="text" name="lastname" value="${lastname}"<br>
            <input type="submit" value="Submit">
        </form>
            <p>${message}</p>
    </body>
</html>
