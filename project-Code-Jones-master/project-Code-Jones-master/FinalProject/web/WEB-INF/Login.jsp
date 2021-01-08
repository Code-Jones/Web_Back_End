<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inventory Login</title>
    </head>
    <body>
        <h1>Inventory Manager</h1>
        <h2>Login</h2>
        <form action="Login" method="post">
            email: <input type="text" name="email" value="${email}"><br>
            password: <input type="password" name="password"><br>
            <input type="submit" value="Sign in">
        </form>
        
        <p>Wanna Register ? <a href="Registration">Click this mysterious link</a></p>
        <p>Forgot your password? Reset it <a href="ResetPassword">here</a></p>
        <p>${message}</p>
    </body>
</html>
