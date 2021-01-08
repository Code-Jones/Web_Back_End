<%-- 
    Document   : editnote
    Created on : Sep 28, 2020, 2:59:46 PM
    Author     : 786524
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Note Taker</title>
    </head>
    <body>
        <h1>Simple Note Keeper</h1>
        <h2>Edit Note</h2>
        <form action="note" method="post">
            Title: <input type="text" name="title" value="${note.title}"><br>
            Contents: <textarea name="content" rows="10" cols="10">${note.content}</textarea><br>
            <input type="submit" value="Submit">
        </form>
    </body>
</html>
