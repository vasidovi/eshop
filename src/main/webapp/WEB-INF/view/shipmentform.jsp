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
        <a href="${pageContext.request.contextPath}/admin_page"><button>To Goods List</button></a>
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
                        <input name="supplier"  value="Anonymous" required = "required"/>
                    </div>
                </div>    
                </br>
                <ol class="item">
                        <li>
                            <button type="button" class="remove">Remove</button>
                            <div>
                                <label name="count">Id (Product identificator, if new good please leave blank) </label> 
                                <input type= "number" name="invoiceLineList[0].productId" max="${fn:length(products)}" />
                            </div>                                               
                            <div>
                                <label name="count">Count:  </label> 
                                <input type= "number" name="invoiceLineList[0].count" value="1" required="required"/>
                            </div>
                            <div>
                                <label name="price">Price:  </label> 
                                <input name="invoiceLineList[0].price" pattern="\d+(.\d{2})?"/>   
                            </div>
                            <div>   
                                <label name="name">Name:  </label> 
                                <input name="invoiceLineList[0].name"/>
                            </div>
                        </li>                    
                </ol>
                <button type="button" id="add-new" style="width: 80px">Add New</button>
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
                                        <button id="addExisting">Add</button> 
                                    </td>

                                </c:forEach>
                        </tbody>

                    </table>

                </c:if>       
            </div>     

        </div>
        <script
        src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
        <script type="text/javascript" language="javascript">


            $("#add-new").click(function () {
                $(".item li:last").clone()
                        .find("input").each(function () {
                    $(this).attr('name', function (_, attr) {
                        var i = attr.match(/[0-9]+/g);
                        var returnIndex = attr.replace(i[0], parseInt(i[0]) + 1);
                        return returnIndex;
                    });

                }).end()
                        .find("input[name$=count]").attr("value", 1).end()
                        .find(".remove").click(function () {
                   removeItem($(this));
                }).end()

                        .appendTo(".item");

            });

            $(".remove").click(function () {
                removeItem($(this));
            });



            var d = new Date();
            
            
            var removeItem = function(e) {
                if ($(".remove").length > 1){
                e.parent().remove();
            }
            }


            var toDoubleDigits = function (i) {
                i = "" + i;
                if (i.length == 1) {
                    i = "0" + i;
                }
                return i;
            }

            var receiveDate = d.getFullYear() + "-" + toDoubleDigits(d.getMonth() + 1) + "-" + toDoubleDigits(d.getDate());

            $("input[name=recieveDate]").attr("value", receiveDate);


//var i = 1;
//$("button").click(function() ​​​{
//  $("table tr:first").clone().find("input").each(function() {
//    $(this).val('').attr('id', function(_, ids) { return id + i });
//  }).end().appendTo("table");
//  i++;
//})​;​




        </script>         
    </body>
</html>