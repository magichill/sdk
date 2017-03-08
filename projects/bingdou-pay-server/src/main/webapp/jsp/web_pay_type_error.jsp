<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>支付方式网页版请求错误</title>
</head>
<h3>错误原因 : <%= request.getAttribute("errorMessage") %>
</h3>
<%
    if (request.getAttribute("errorReturnUrl") != null
            && request.getAttribute("errorReturnUrl") != "") {%>
<h3><a href="<%= request.getAttribute("errorReturnUrl") %>">返回</a></h3>
<%}%>
<body>
</body>
</html>
