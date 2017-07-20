<%-- 
    Document   : login
    Created on : 2017/07/21, 1:30:26
    Author     : kjaeyun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>login</title>
        <script>
        </script>
    </head>
    <body>
        <form method="post" action="login">
            ID : <input type="text" name="userid" required><br />
            PW : <input type="password" name="userpw" required><br />
            <input type="submit" value="login">
        </form>
    </body>
</html>
