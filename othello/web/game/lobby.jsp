<%-- 
    Document   : lobby
    Created on : 2017/07/21, 0:45:48
    Author     : kjaeyun
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lobby</title>
        <script type="text/javascript">
            
        </script>
    </head>
    <body>
        <c:out value="${userid}" /> !, Welcome to SNK Othello JSP<br />
        <form method="get" action="lobby/makeroom">
            <input type="submit" value="New Room"><br />
        </form>
        <br />
        <table>
                <tr>
                    <td>Title</td>
                    <td>State</td>
                    <td>Join</td>
                </tr>
            <c:forEach var="room" items="${rooms}">
                <tr>
                    <td>${room.roomName}</td>
                    <td>${room.state}</td>
                    <td> <input type="button" value="Join"> </td>
                </tr>
            </c:forEach>
        </table> 
    </body>
</html>
