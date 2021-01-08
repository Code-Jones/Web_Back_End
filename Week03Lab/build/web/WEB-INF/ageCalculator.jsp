<%-- 
    Document   : ageCalculator
    Created on : Sep 24, 2020, 1:36:18 PM
    Author     : 786524
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lab 3</title>
    </head>
    <body>
        <h1>Age Calculator</h1>
        <form action="ageCalculator" method="post">
            Enter your age: <input type="number" name="age" value="${age}"><br>
            <input type="submit" value="Your next age">
        </form>
            <a href="./arthmeticCalculator">Arithmetic Calculator</a>
            <p>${message}</p>
    </body>
</html>
