<%-- 
    Document   : log
    Created on : Jan 18, 2022, 3:39:13 PM
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
        <form action="logvalidate" method="post">
        <label>username</label><br>
        <% if(request.getAttribute("ui")!=null){ %><label style="color:red;" name="un"><%=request.getAttribute("ui")%> </label><br><%}else { } %>
            <input type="text" name="userid" /><br>
            <label>Password</label><br>
            <% if(request.getAttribute("pas")!=null){ %><label style="color:red;" name="ps"><%=request.getAttribute("pas")%> </label><br><%}else { } %>
            <input type="password" name="password" /><br>
            <input type="submit" value="Login" />
            <% if(request.getAttribute("lc")!=null){ %><label style="color:red;" name="lc"><%=request.getAttribute("lc")%> </label><br><%}else { } %>
        </form>
        <a href="/">Register</a>
    </body>
</html>
