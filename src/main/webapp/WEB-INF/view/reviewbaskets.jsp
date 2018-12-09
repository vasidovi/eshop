<%-- 
    Document   : reviewbaskets
    Created on : Dec 8, 2018, 2:17:15 AM
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
        <style>
            .controller{
                padding: 5px; background-color: seashell; margin: 5px; max-width: 380px;                
            }
            .container{display: grid; grid-template-columns: 1fr 1fr}
            
        </style>
    </head>
    <body>
                     
          
          <c:if test="${empty user}">
            <a href="${pageContext.request.contextPath}/login"><button>Login</button></a>
            </c:if>
             <c:if test="${not empty user}">
              <a href="${pageContext.request.contextPath}/logout"><button>Logout</button></a>
            </c:if>
             <a href="${pageContext.request.contextPath}/admin"><button>To Goods List</button></a>
             
             <div class="controller">
                 <p> Controller</p>
                 
                 <form>
                      <input type="hidden" name="hasUser" value="true">
                      <input type="hidden" name="isPurchased" value="${param.isPurchased}">
                      <input type="hidden" name="id" value="${param.id}">
                      
                 <button>User Baskets</button>                      
                 </form>
                 <form>
                     <input type="hidden" name="hasUser" value="${param.hasUser}">
                      <input type="hidden" name="isPurchased" value="true">                     
                      <input type="hidden" name="id" value="${param.id}">

                 <button>Bought Baskets</button>
                 </form>
                 <form>
                      <input type="hidden" name="hasUser" value="false">
                      <input type="hidden" name="isPurchased" value="false">
                      <input type="hidden" name="id">
                 <button>All Baskets</button>
                 </form>                                
             </div>
                      
              <div class="container">          
      
         <c:if test="${not empty baskets}">      
         <table>
            <thead>
            <th>
                No
            </th>
            <th>
                User 
            </th>
            <th>
                Create Date
            </th>
            <th>
                Purchase Date
            </th>
           
                                                       
        </thead> 
        <tbody>
            <c:forEach var="basket" items="${baskets}">
                <c:set var="count" value="${count + 1}" scope="page"/>
                <tr>
                    <td >
                        <c:out value = "${basket.id}"/>
                    </td>
                    <td>
                        <c:out value ="${basket.userId.username}"/>
                    </td>
                     <td>
                        <c:out value = "${basket.createDate}"/>
                    </td>                    
                    <td>
                        <c:out value = "${basket.purchaseDate}"/>
                    </td>
                    <td>
                            <form>
                      <input type="hidden" name="hasUser" value="${hasUser}">
                      <input type="hidden" name="isPurchased" value="${isPurchased}">
                      <input type="hidden" name="id" value="${basket.id}">
                      <button>Show Products</button>
                            </form>                        
                    </td>
                    
                </tr>
            </c:forEach>
        </tbody>

    </table>
    </c:if> 
            <div>
           <p>Basket Id No. <c:out value="${param.id}" /></p>
         
               <table>
            <thead>
            <th>
                No
            </th>
            <th>
                Product Id 
            </th>
            <th>
                Name
            </th>
            <th>
               Price
            </th>
             <th>
               Count
            </th>
           
                                                       
        </thead> 
         <c:if test="${not empty basketLines}">
        <tbody>
            <c:forEach var="line" items="${basketLines}">
                <c:set var="lineCount" value="${lineCount + 1}" scope="page"/>
                <tr>
                    <td >
                        <c:out value = "${lineCount}"/>
                    </td>
                    <td>
                        <c:out value ="${line.productId.id}"/>
                    </td>
                     <td>
                        <c:out value = "${line.name}"/>
                    </td>
<td>
                        <c:out value = "${line.price}"/>
                    </td>                    
                    <td>
                        <c:out value = "${line.count}"/>
                    </td>                    
                </tr>
            </c:forEach>
        </tbody>  
                 </c:if>     

    </table>
             <c:if test="${empty basketLines && not empty id}">
             <p>Basket is empty</p>  
                     </c:if>

            </div>
           
        </div>         
    </body>
</html>
