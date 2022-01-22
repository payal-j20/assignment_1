<%-- 
    Document   : payment
    Created on : Jan 21, 2022, 1:16:53 PM
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
        
        <form action="pay" method="post">
            <input type="hidden" name="userid" value="<%= request.getAttribute("userid") %>"/>
            <input type="hidden" name="qty" value="<%= request.getAttribute("qty") %>"/>
            <input type="hidden" name="coupon" value="<%= request.getAttribute("coupon") %>"/>
            <input type="hidden" name="amount" value="<%= request.getAttribute("amount") %>"/>
            <label> Amount :<%= request.getAttribute("amount") %></label>
            <input type="submit" value="confirm" />
        </form>
    </body>
</html>
