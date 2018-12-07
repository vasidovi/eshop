<%-- 
    Document   : index
    Created on : Dec 1, 2018, 10:41:15 PM
    Author     : Dovile
--%>


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
            .product_container{border: 1px solid grey; padding: 5px; margin-top: 10px;}
            .hidden{display: none}
            .line_element{  border: 1px solid silver}
/*            .line_row{display: block}*/
        </style>   
        <title>E-Shop</title> 
    </head>
    <body>
        <h2>My Basket</h2>
        <div>
          
       
            <c:if test="${empty user}">
            <a href="${pageContext.request.contextPath}/login"><button>Login</button></a>
            </c:if>
             <c:if test="${not empty user}">
              <a href="${pageContext.request.contextPath}/logout"><button>Logout</button></a>
            </c:if>         
<!--        <button>Sign up</button>-->
            <a href="${pageContext.request.contextPath}"><button>To Shop</button></a>
            
            <c:if test="${not empty user}">
             <p>Basket owner: ${user}</p>
             <p>Basket id: ${basketLines[0].basketId}</p>
             </c:if>      
        </div>
        <br/>

         <tr>
              <table>
            <c:if test="${not empty basketLines}">      
            <thead>
            <th>
                No.
            </th>
            <th>
               Name   
            </th>
            <th>
               Count
            </th>
            <th>
                Price
            </th>
             <th>
                Total Price
            </th>
              <th>
                Remove form Basket
            </th>
        </thead> 
        </c:if>
        <tbody>             
         <c:set var="count" value="0" scope="page" />
         <c:set var="sum" value="0" scope="page"/>
        <c:forEach var="line" items="${basketLines}">
            <c:set var="count" value="${count + 1}" scope="page"/>
            

            <tr class="line_row">                
                <td class="line_element">
                  <c:out value = "${count}"/>                      
                </td> 
                <td class="line_element">
                  <c:out value="${line.name}"/>                     
                </td> 
                 <td class="line_element">
                   <c:out value="${line.count}"/>               
                </td> 
                <td class="line_element">
                   <c:out value="${line.price}"/>               
                </td> 
                <td class="line_element">
                   <c:out value="${line.price * line.count}"/> 
                    <c:set var="sum" value="${sum + line.price * line.count}" scope="page"/>
                </td> 
                <td>
                    <form method="POST" action="from_basket" modelAttribute="product">
                        <div class="hidden">
                               <input name="id" value="${line.productId.id}" />
                        </div>
                        <div class="hidden">
                               <input name="name" value="${line.name}" />
                        </div>

                        <div class="hidden">
                               <input name="price" value="${line.price}" />
                        </div>
                           <div>
                              <input name="count" type="number" min="1" max="${line.count}" style="width: 40px"/>
                        </div>                      
                        <div>
                              <button type="submit" name="submit" value="submit">from Basket</button> 
                        </div>
                    </form>
                </td>
            </tr>
        </c:forEach>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td><strong>Sum:</strong></td>
                 <td> <c:out value="${sum}"/></td>
            </tr>
       </thead>
      </table>
      <a href="${pageContext.request.contextPath}/buy"><button>Buy</button></a>
   
</body>
</html>
