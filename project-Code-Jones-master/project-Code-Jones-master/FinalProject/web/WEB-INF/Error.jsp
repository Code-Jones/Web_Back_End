<%-- 
    Document   : Error
    Created on : Dec 13, 2020, 10:34:57 AM
    Author     : 786524
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ERROR</title>
    </head>
    <body>
        <h1>Whoopsies</h1>
        <p>Something went wrong, guess your just going to have to press this <a href="Login">this </a>to get back to the login</p>
    <h2>Details</h2>
        <p>Type: ${pageContext.exception["class"]}</p>
        <p>Message: ${pageContext.exception.message}</p>
    </body>
</html>
