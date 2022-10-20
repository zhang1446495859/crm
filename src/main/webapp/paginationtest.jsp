<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String basePath = request.getScheme() + "://" +
            request.getServerName() + ":" + request.getServerPort() +
            request.getContextPath() + "/";
%>
<html>
<head>
    <title>Title</title>
    <base href="<%=basePath%>">
    <%--    jquery--%>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <%--    bootstrap --%>
    <link rel="stylesheet" href="jquery/bootstrap_3.3.0/css/bootstrap.min.css">
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

    <%--    分页框架--%>
    <link rel="stylesheet" href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css">
    <script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"></script>
    <script type="text/javascript">
        $(function (){
            $("#demo_page1").bs_pagination({
                currentPage:1,      //当前页号
                rowsPerPage:10,     //每页显示的条数，相当于pageSize
                totalRows:1000,     //总条数
                totalPages: 100,     //总页数，必填参数

                visiblePageLinks: 10, //最多可以显示的卡片数， 即页数
                showGoToPage: true, //是否显示“跳转到”部分，默认true--显示
                showRowsPerPage: true, //是否显示“每页显示条数”部分。默认true--显示
                showRowsInfo: true,    //是否显示记录的信息，默认true--显示

                //用户每次切换页号，都会自动触发本函数
                onChangePage:function (){
                    alert("aaa");
                }


            })
        })


    </script>
</head>
<body>

    <div id="demo_page1">

    </div>
</body>
</html>
