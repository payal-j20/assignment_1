<%-- 
    Document   : reg
    Created on : Jan 18, 2022, 1:19:57 PM
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
        <form action="regvalidate" method="post" style="align:center;">
            <label>Email</label><br>
            <% if(request.getAttribute("em")!=null){ %><label style="color:red;" name="em"><%=request.getAttribute("em")%> </label><br><%}else { } %>
            <input type="text" name="email"/><br>
            <label>username</label><br>
            <% if(request.getAttribute("userid")!=null){ %><label style="color:red;" name="un"><%=request.getAttribute("userid")%> </label><br><%}else { } %>
            <input type="text" name="userid"/><br>
            <label>Password</label><br>
            <% if(request.getAttribute("pas")!=null){ %><label style="color:red;" name="ps"><%=request.getAttribute("pas")%> </label><br><%}else { } %>
            <input type="password" name="password"/><br>
            <input type="submit" value="Register" />
            
        </form>
        <a href="log" >Login</a>
    </body>
</html>
