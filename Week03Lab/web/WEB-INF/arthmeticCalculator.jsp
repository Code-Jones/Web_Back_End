<%-- 
    Document   : arthimeticCalculator
    Created on : Sep 24, 2020, 3:15:06 PM
    Author     : 786524
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Arithmetic Calculator</title>
    </head>
    <body>
        <h1>Arithmetic Calculator</h1>
        <form action="arthmeticCalculator" method="post">
            First: <input type="number" name="first" value="${first}"><br>
            Second: <input type="number" name="second" value="${second}"><br>
            <input type="submit" name="operation" value="+" >
            <input type="submit" name="operation" value="-">
            <input type="submit" name="operation" value="*">
            <input type="submit" name="operation" value="%">
        </form>
            <p>Result: ${message}</p>
            <a href="./ageCalculator">Age Calculator</a>
    </body>
</html>
