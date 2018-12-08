<%-- 
    Document   : admin
    Created on : Dec 5, 2018, 4:21:33 PM
    Author     : Dovile
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
          <base href="${pageContext.request.contextPath}">
        <style>
            .hidden{display: none}
            input{width: 80px}
        
        
        </style>
    </head>
    <body>
        <h1>Admin Page</h1>
        
          <c:if test="${empty user}">
            <a href="${pageContext.request.contextPath}/login"><button>Login</button></a>
            </c:if>
             <c:if test="${not empty user}">
              <a href="${pageContext.request.contextPath}/logout"><button>Logout</button></a>
            </c:if>            
       
        <p>Welcome, ${user}</p>
        
        <a href="#"><button>Review invoices TBI</button></a>
        <a href="#"><button>Review baskets TBI</button></a>
        </br>      
              
         <c:if test="${not empty products}">      
         <table>
            <thead>
            <th>
                No.
            </th>
            <th>
                Name  
            </th>
            <th>
                Price
            </th>
            <th>
                Available
            </th>
            <th>
                Count
            </th>
                                                       
        </thead> 
        <tbody>
            <c:forEach var="product" items="${products}">
                <c:set var="count" value="${count + 1}" scope="page"/>
                    <form method="POST" action="${pageContext.request.contextPath}/editProductDetail" modelAttribute="editProductDetail">
                <tr>
                    <td >
                        <c:out value = "${count}"/>
                        <input name="id" value="${product.id}" class="hidden"/>
                    </td>
                    <td>
                       <input name="name" value="${product.name}" />
                    </td>
                    <td>
                        <input name="price" value="${product.price}" pattern="\d+(.\d{2})?" />
                    </td>
                     <td>
                        <c:out value = "${product.count}"/>
                    </td>                    
                    <td>
                       <button type="submit" name="submit" value="submit">Edit</button> 
                    </td>
        </form>
                    
            </c:forEach>
        </tbody>

    </table>
    </c:if>       
        
        
        
                        <a href="${pageContext.request.contextPath}/newShipment" /><button>New shipment</button></a>                             
                
        
        
        
        
        
        
        
    </body>
</html>
