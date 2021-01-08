<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Summary</title>
    </head>
    <body>
        <h1>Admin Summary</h1>
        <p>Total value for all users: $${total}. The most expensive item is ${item} at $${expensive} owned by ${owner}</p>
        <br>
        <a href="admin?logout">Logout</a>
    </body>
</html>
