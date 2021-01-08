<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inv?</title>
    </head>
    <body>
        <h1>Home Inventory</h1>
        <form action="inventory" method="post">
            Category    <select name="category">
                <option>bedroom</option>
                <option>garage</option>
                <option>living</option>
                <option>kitchen</option>
            </select><br>
            Item name:   <input type="text" name="item" value="${item}"><br>
            Price:   <input type="number" name="price" value="${price}"><br>
            <input type="submit" value="Add">
        </form>
            <p>${message}</p>
            <p>Total value in inventory: ${total}</p>
            <a href="inventory?logout">Logout</a>
    </body>
</html>
