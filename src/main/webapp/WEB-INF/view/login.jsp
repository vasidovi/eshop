<%-- 
    Document   : login
    Created on : Dec 4, 2018, 5:11:22 PM
    Author     : Dovile
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            .hidden{display: none};
        </style>
    </head>
    <body>
        <div>
             <form method="POST" action="login" modelAttribute="userLogin">
                <div >
                    <label name="username">Username: </label>
                    <input name="username"/>
                </div>
                <div>
                    <label name="password">Password: </label>
                    <input type="password" name="password"/>
                </div>
                <div>
                   <button type="submit" name="submit" value="submit">Login</button>
                </div>
            </form>
 <br/>
 
 <c:out value="${invalid}"/>
 <p>New User? <a href=""><button>Sign Up</button></a></p>
 
 

    </body>
</html>
