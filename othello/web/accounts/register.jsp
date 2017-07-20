<%-- 
    Document   : register
    Created on : 2017/07/21, 1:30:31
    Author     : kjaeyun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
    </head>
    <body>
        New Account 
        <form method="post" action="register">
            ID : <input type="text" name="userid"><br />
            PW : <input type="password" name='userpw'><br />
            PW(again) : <input type="password" name="userpw2"><br />
            <input type="submit" value="register">
        </form>
    </body>
</html>
