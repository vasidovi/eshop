<%-- 
    Document   : shipmentform
    Created on : Dec 5, 2018, 6:36:58 PM
    Author     : Dovile
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib  uri="http://www.springframework.org/tags" prefix="spring"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New invoice</title>
        <style>
            .hidden{display: none}
            form{width: 300px;}
            form, form div, form ol { display: grid}
        </style>
    </head>
    <body>

        <a href="${pageContext.request.contextPath}/logout"><button>Logout</button></a>
        <a href="${pageContext.request.contextPath}/admin_page"><button>To Good List</button></a>
        <a href="${pageContext.request.contextPath}/new_product"><button>New Product</button></a>

        <div class="container">

            <form method="POST"  action="${pageContext.request.contextPath}/registerInvoice" modelAttribute="invoice">
                <div>
                    <div>
                        <label name="recieveDate">Recieve Date: </label> 
                        <input type="date" name="recieveDate" required = "required"/>
                    </div>
                    <div>
                        <label name="supplier">Supplier: </label> 
                        <input name="supplier" required = "required"/>
                    </div>
                </div>    
                </br>
                <ol class="item">
                    <c:if test="${empty lines}">
                        <c:set var="count" value="2" scope="page"/>
                    </c:if>
                    <c:if test="${not empty lines}">
                        <c:set var="count" value="${lines}" scope="page"/>
                        <c:set var="index" value="0" scope="page"/>
                    </c:if>  
                    <c:forEach begin="1" end="${count}" varStatus="loop">
                        <li>

                             <div>
                                <label name="count">Id (Product identificator, if new good please leave blank) </label> 
                                <input type= "number" name="invoiceLineList[${loop.index-1}].productId" max="${fn:length(products)}" />
                            </div>                                               
                            <div>
                                <label name="count">Count:  </label> 
                                <input type= "number" name="invoiceLineList[${loop.index-1}].count" value="1" required="required"/>
                            </div>
                            <div>
                                <label name="price">Price:  </label> 
                                <input name="invoiceLineList[${loop.index-1}].price" pattern="\d+(.\d{2})?"/>   
                            </div>
                            <div>   
                                <label name="name">Name:  </label> 
                                <input name="invoiceLineList[${loop.index-1}].name" value="placeholer" required = "required"/>
                            </div>
                        </li>
                        <c:set var="index" value="${index +1 }" scope="page"/>    
                    </c:forEach>                          
                </ol>
                <input type="submit" value="Submit">
            </form>
            <c:if test="${not empty products}">  
                <div>
                    <p> List of products in shop </p> 

                    <table>

                        <thead>
                        <th>
                            Id.
                        </th>
                        <th>
                            Name  
                        </th>                                        
                        </thead> 
                        <tbody>
                            <c:forEach var="product" items="${products}">
                                <c:set var="count" value="${count + 1}" scope="page"/>

                                <tr>
                                    <td>
                                        <c:out value = "${product.id}"/>
                <!--                        <input name="id" value="${product.id}" class="hidden"/>-->
                                    </td>
                                    <td>
                                        <c:out value = "${product.name}"/>
                 <!--                       <input name="name" value="${product.name}" />-->
                                    </td>
                                    <td>
                                        <button type="submit" name="submit" value="submit">Select</button> 
                                    </td>

                        </c:forEach>
                        </tbody>

                    </table>

                </c:if>       
            </div>     

        </div>
    </body>
</html>