<%-- 
    Document   : dis
    Created on : Jan 18, 2022, 4:25:28 PM
    Author     : payal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <%= request.getAttribute("userid") %>
        
        <form action="<%= request.getAttribute("userid") %>/order_confirm" method="post" >
            <img style="height:100px;width:100px;" src="template/dal.jpg" ><br>
            
            <label>Quantity </label>
            <select name="qty" >
                <% for(int i=1;i<=50;i++){ %>
                    <option name="<%= i %>" value="<%= i %>"> <%= i %></option>
                <%}%>
            </select><br>
            <label>Coupons</label>
            <select name="coupon" >
                <option value="off5">OFF5</option>
                <option value="off10">OFF10</option>
            </select><br>
            
            <input type="submit" value="Add to cart" />
        </form>
        <br>
        <br>
        
        
    </body>
</html>
