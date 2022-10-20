<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String basePath = request.getScheme() + "://" +
            request.getServerName() + ":" + request.getServerPort() +
            request.getContextPath() + "/";
%>
<html>
<head>
    <meta charset="UTF-8">
    <base href="<%=basePath%>">
    <title>Title</title>
<%--    jquery--%>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<%--    bootstrap --%>
    <link rel="stylesheet" href="jquery/bootstrap_3.3.0/css/bootstrap.min.css">
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<%--    日历框架--%>
    <link rel="stylesheet" href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css">
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
    <script type="text/javascript" >
        $(function (){
            //当容器加载完成后，对容器调用工具函数
            $("#date").datetimepicker({
                language:"zh-CN" ,//语言
                format:"yyyy-mm-dd",//日期格式
                minView:"month", //可以选择的最小视图
                initialDate:new Date(),
                autoclose:true  //设置选择完日期或者时间之后，是否自动关闭日历
            });
        })
    </script>
</head>
<body>
    <input type="text" id="date">
</body>
</html>
