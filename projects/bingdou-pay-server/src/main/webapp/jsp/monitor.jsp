<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Monitor</title>
</head>
<body>
REDIS CONNECT STATUS : <%=request.getAttribute("redisIsConnStr")  %><br>
USER DB CONNECT STATUS : <%=request.getAttribute("userDBIsConnStr") %><br>
PAY DB CONNECT STATUS : <%=request.getAttribute("payDBIsConnStr") %><br>
ALL CONNECT STATUS : <%=request.getAttribute("result") %>
</body>
</html>
