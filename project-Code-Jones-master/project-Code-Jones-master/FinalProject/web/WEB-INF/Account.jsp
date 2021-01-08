<%-- 
    Document   : Account
    Created on : Dec 13, 2020, 12:43:35 AM
    Author     : 786524
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Account</title>
    </head>
    <body>
        <h1>Account Management</h1>
        <h2>Welcome ${fullName}</h2>
        <a href="Inventory">Inventory</a><br>
        <a href="Login">Logout</a><br><br>
        
        <h2> Edit Your Account Information</h2>
        <form action="Account" method="post">
            First Name: <input type="text" name="updateFirst" value="${first}"><br>
            Last Name: <input type="text" name="updateLast" value="${last}"><br>
            Email: <input type="text" name="updateEmail" value="${email}"><br>
            <p>To change password use the reset password link <a href="ResetPassword">here</a></p>
            <!--Password: <input type="text" name="updatePassword" value="${password}"><br> use reset password--> 
            <input type="submit" value="Change Information">
            <input type="hidden" name="action" value="edit"><br>
        </form>
        <form method="post">
            <input type="submit" value="Deactivate account">
            <input type="hidden" name="action" value="deactivate"><br>
        </form>
        <p>${message}</p>
    </body>
</html>
