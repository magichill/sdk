<%--
  Created by IntelliJ IDEA.
  User: liuyang
  Date: 16/7/23
  Time: 08:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no"/>
    <title>海马VIP</title>
    <style>
        body, div, dl, dt, dd, ul, ol, li, h1, h2, h3, h4, h5, h6, pre, code, form, fieldset, legend, input, textarea, p, blockquote, th, td, hr, button, article, aside, details, figcaption, figure, footer, header, hgroup, menu, nav, section {
            margin: 0;
            padding: 0;
        }

        ul, li {
            list-style: none
        }

        input, select, textarea {
            font-size: 100%;
        }

        h1, h2, h3, h4, h5, h6 {
            font-size: 100%;
            font-weight: 500;
        }

        address, caption, cite, code, dfn, em, strong, th, var {
            font-style: normal;
            font-weight: 500;
        }

        fieldset, img {
            border: 0;
        }

        .fl {
            float: left
        }

        .fr {
            float: right
        }

        .clearfix:after {
            content: ".";
            display: block;
            height: 0;
            clear: both;
            visibility: hidden
        }

        .clearfix {
            zoom: 1;
            display: inline-block;
            _height: 1px
        }

        * html .clearfix {
            height: 1%
        }

        * + html .clearfix {
            height: 1%
        }

        .clearfix {
            display: block
        }

        html {
            font-size: 20px;
            -webkit-text-size-adjust: 100%;
        }

        .fwsm {
            width: 18.75rem;
        }

        .fwsm img {
            width: 100%;
        }
    </style>
    <script src="${ctx}/static/user_vip/jquery-1.11.0.min.js"></script>
    <script>
        var ctx = '${ctx}';
    </script>
</head>
<body>
<div class="fwsm">
    <img src="" alt="">
</div>
</body>
<script src="${ctx}/static/user_vip/c.js"></script>
</html>