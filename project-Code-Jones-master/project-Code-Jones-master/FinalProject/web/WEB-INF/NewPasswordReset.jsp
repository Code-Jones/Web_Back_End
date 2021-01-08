<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Password</title>
    </head>
    <body>
        <h1>Enter a new password</h1>
        <form action="" method="post">
            <input name="password" type="password">
            <input name="verification" value="${verification}" type="hidden">
            <input type="submit" value="Submit">
        </form>
    </body>
</html>
