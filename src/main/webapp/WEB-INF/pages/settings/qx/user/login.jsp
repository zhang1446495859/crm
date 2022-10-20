<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String basePath = request.getScheme() + "://" +
			request.getServerName() + ":" + request.getServerPort() +
			request.getContextPath() + "/";
%>
<html>
<head>
	<meta charset="UTF-8">
	<base href="<%=basePath%>">
	<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
	<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(function () {

			//给整个浏览器窗口添加键盘按下事件
			$(window).keydown(function (e) {
				//如果按的是回车键，则提交登录请求
				if(e.keyCode == 13){
					$("#loginBtn").click();
				}
			});


			$("#loginBtn").click(function (){
				$("#msg").html("");
				var loginAct = $.trim($("#loginAct").val());
				var loginPwd = $.trim($("#loginPwd").val());
				//使用jquery获取指定元素的指定属性的值：
				//选择器.attr("属性名")； 用来获取那些值不是true/false的属性值。
				//选择器.prop("属性名")； 用来获取值是true/false的属性值。
				var isRemPwd = $("#isRemPwd").prop("checked");

				if(loginAct==""){
					alert("账号不能为空！");
					return;
				}
				if(loginPwd==""){
					alert("密码不能为空！");
					return;
				}

				$.ajax({
					url:"settings/qx/user/toLogin",
					data:{
						loginAct:loginAct,
						loginPwd:loginPwd,
						isRemPwd:isRemPwd
					},
					type:"post",
					dataType:"json",
					success:function (data) {
						if(data.code == "1"){
							//跳转业务主页面
							window.location.href="workbench/index";
						}else{
							//提示信息
							$("#msg").html(data.message);
						}
					},
					beforeSend:function (){
						//当ajax向后台发送请求之前，会自动执行本函数
						//该函数的返回值能够决定ajax是否真正向后台发送请求，
						//如果该函数返回true，则ajax会真正向后台发送请求；否则，如果该函数返回false，则ajax放弃向后台发送请求。
						//显示正在验证
						$("#msg").text("正在验证！");
						return true;
					}
				})
			})
		})
		
	</script>
</head>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2019&nbsp;动力节点</span></div>
	</div>
	
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form action="workbench/index.html" class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
	<%-- cookie 表示cookie域中 名字为loginAct的cookie对象，value为值 不加cookie 表示在4个作用域中找名为loginAct的变量 获取get参数 param.参数--%>
						<input class="form-control" id="loginAct" type="text" value="${cookie.loginAct.value}" placeholder="用户名">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input class="form-control" id="loginPwd" type="password" value="${cookie.loginPwd.value}" placeholder="密码">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">
						<label>
							<c:if test="${not empty cookie.loginAct and not empty cookie.loginPwd}">
								<input type="checkbox" id="isRemPwd" checked>
							</c:if>
							<c:if test="${empty cookie.loginAct or empty cookie.loginPwd}">
								<input type="checkbox" id="isRemPwd" >
							</c:if>
							 十天内免登录
						</label>
						&nbsp;&nbsp;
						<span id="msg" style="color: red"></span>
					</div>
					<button type="button" id="loginBtn" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>