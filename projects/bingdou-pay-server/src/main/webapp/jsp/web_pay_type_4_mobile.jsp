<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>WEB PAY TYPE</title>
    <style type="text/css">

        div {
            padding: 20px;
            margin: auto;
        }

    </style>
</head>
<div style="width: 300px">
    <iframe src="<%= request.getContextPath() %>/web_pay_type/create_4_mobile?<%=request.getAttribute("param")%>"
            width="300px" height="300px" frameborder="0" scrolling="no">
    </iframe>
</div>
<body>
</body>
</html>