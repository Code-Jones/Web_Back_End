<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration</title>
    </head>
    <body>
        <h1>Make yourself a new account!</h1>
        <form action="" method="post">
            Email: <input type="text" name="email" value="${email}"><br>
            Password <input type="password" name="password"><br>
            Confirm Password: <input type="password" name="confirmPassword"><br>
            First name: <input type="text" name="firstname" value="${firstname}"><br>
            Last name: <input type="text" name="lastname" value="${lastname}"><br>
            <input type="submit" value="Registration"><br>
            ${message}
        </form>
    </body>
</html>
