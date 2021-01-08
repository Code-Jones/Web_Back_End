<%-- 
    Document   : viewnote
    Created on : Sep 28, 2020, 2:59:34 PM
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
        <h2>View Note</h2>
        <p><b>Title: </b>"${note.title}"</p>
        <p><b>Contents: </b>"${note.content}"</p>
        <a href="note?edit">Edit</a>
    </body>
</html>
