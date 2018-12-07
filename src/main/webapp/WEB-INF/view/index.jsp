<%-- 
    Document   : index
    Created on : Dec 1, 2018, 10:41:15 PM
    Author     : Dovile
--%>

<%--<%@page import="com.dovile.model.Product"%>
<%@page import="java.util.List"%>--%>
<%@ page isELIgnored="false" %>
<!-- meta error-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            div {display: inline-block}
            .product_container{border: 1px solid grey; padding: 5px; margin-top: 10px; width: 250px}
            .hidden{display: none};
        </style>   
        <title>E-Shop</title> 
    </head>
    <body>
        <h1>Products</h1>
        
        
        <p>Welcome, ${user}</p>
        
        
        
        
        
            <c:if test="${empty user}">
            <a href="${pageContext.request.contextPath}/login"><button>Login</button></a>
            </c:if>
             <c:if test="${not empty user}">
              <a href="${pageContext.request.contextPath}/logout"><button>Logout</button></a>
            </c:if>            
<!--            <button>Sign up</button>-->
            <a href="basket"><button>Basket <strong>${basketCount}</strong></button></a>
            
            
        </div>
        <br/>

        <c:out value="${basket}"/>
        <c:out value="${toBasket}"/>

        <c:forEach var="product" items="${products}">                    
            <div class="product_container">
                <p class="hidden"><c:out value="${product.id}" /></p>
                <p><c:out value="${product.name}" /></p>
                <div>
                    <p> Available: <c:out value="${product.count}" /></p>
                    <p> Price: <c:out value="${product.price}" /></p>
                </div>
                <div>
                     <form method="POST" action="to_basket" modelAttribute="product">
                        <div class="hidden">
                            
                               <input type="number" name="id" value="${product.id}" />
                        </div>
                        <div class="hidden">
                               <input name="name" value="${product.name}" />
                        </div>

                        <div class="hidden">
                               <input name="price" value="${product.price}" />
                        </div>
                           <div>
                              <input name="count" type="number" path="count" min="0" max="${product.count}" style="width: 40px"  value="1"/>
                        </div>                      
                        <div>
                              <button type="submit" name="submit" value="submit">to Basket</button> 
                        </div>
                    </form>


                </div>
            </div>            
        </c:forEach>

    </body>
</html>
