<%-- 
    Document   : register
    Created on : Dec 9, 2018, 12:19:09 AM
    Author     : Dovile
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <body>
 <!--     Simple test form needs to be upgraded  to implement validation-->

        <form method="POST" action="newUser" modelAttribute="newUser">
            <div>

                <div>
                    <label>username</label>
                    <input type ="text" name="username" required="required"/>
                </div>
                <div>
                    <label>password</label>   
                    <input type=" text" name="password" required="required"/>
                </div>
                <div>
                    <label>password</label> 
                    <input type=" text" name="passwordConfirm" required="required"/>
                </div>
                <button type="submit">submit</button>
        </form>

    </body>
</html>