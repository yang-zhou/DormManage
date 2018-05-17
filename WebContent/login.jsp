<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="com.lero.model.Admin" %>
<%@ page import="com.lero.model.DormManager" %>
<%@ page import="com.lero.model.Student" %>
<%
	String userName=null;
	String password=null;
	String userType=null;
	String remember=null;
	if(request.getAttribute("user")==null){
		Cookie[] cookies=request.getCookies();
		for(int i=0;cookies!=null && i<cookies.length;i++){
			if(cookies[i].getName().equals("dormuser")){
				userName=cookies[i].getValue().split("-")[0];
				password=cookies[i].getValue().split("-")[1];
				userType=cookies[i].getValue().split("-")[2];
				remember=cookies[i].getValue().split("-")[3];
			}
		}
		
		if(userName==null){
			userName="";
		}
		
		if(password==null){
			password="";
		}
		
		if(userType==null){
			userType="";
		} else if("admin".equals(userType)){
			pageContext.setAttribute("user", new Admin(userName,password));
			//pageContext.setAttribute("userType", 1);
		} else if("dormManager".equals(userType)) {
			pageContext.setAttribute("user", new DormManager(userName,password));
			//pageContext.setAttribute("userType", 2);
		} else if("student".equals(userType)) {
			pageContext.setAttribute("user", new Student(userName,password));
			//pageContext.setAttribute("userType", 3);
		}
		
		if("yes".equals(remember)) {
			pageContext.setAttribute("remember", 1);
		}
		
	}
%>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>宿舍管理系统登录</title>
<link href="${pageContext.request.contextPath}/source/plugs/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/source/plugs/bootstrap/css/bootstrap-theme.css" rel="stylesheet">

<script type="text/javascript" src="${pageContext.request.contextPath}/source/bootstrap/js/jQuery.js"></script>
<script src="${pageContext.request.contextPath}/source/plugs/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript">
	function checkForm() {
		var userName = document.getElementById("userName").value;
		var password = document.getElementById("password").value;
		var userType = document.getElementById("userType").value;
		if (userName == null || userName == "") {
			document.getElementById("error").innerHTML = "用户名不能为空";
			return false;
		}
		if (password == null || password == "") {
			document.getElementById("error").innerHTML = "密码不能为空";
			return false;
		}
		if (userType == null || userType == "") {
			document.getElementById("error").innerHTML = "请选择用户类型";
			return false;
		}
		return true;
	}
</script>

<style type="text/css">
	  body {
        padding-top: 200px;
        padding-bottom: 40px;
        background-image: url('source/images/bg.jpg');
        background-position: center;
		background-repeat: no-repeat;
		background-attachment: fixed;
      }
      
      .radio {
      	padding-top: 10px;
       	padding-bottom:10px;
      }
      
      .form-signin-heading{
      	text-align: center;
      }

      .form-signin {
        max-width: 300px;
        padding: 19px 29px 0px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }
</style>

</head>
<body>
<div class="container">
      <form name="myForm" class="form-signin" action="login" method="post" onsubmit="return checkForm()">
        <h2 class="form-signin-heading"><font color="gray">宿舍管理系统</font></h2>
        <input id="userName" name="userName" value="${user.userName }" type="text" class="form-control" placeholder="账号">
        <input id="password" name="password" value="${user.password }" type="password" class="form-control" placeholder="密码" >
        <select id="userType" name="userType" class="form-control">
        	<option value="">请选择...</option>
        	<option value="admin">系统管理员</option>
        	<option value="dormManager">宿舍管理员</option>
        	<option value="student">学生</option>
        </select>
        <label class="checkbox" style="padding-left: 20px">
          <input id="remember" name="remember" type="checkbox" value="remember-me" ${remember==1?'checked':''}>记住我 &nbsp;&nbsp;&nbsp;&nbsp; <font id="error" color="red">${error }</font>  
        </label>
        <button class="btn btn-large btn-primary" type="submit">登录</button>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <button id="resetButton" class="btn btn-large btn-primary" type="button">重置</button>

		<p align="center" style="padding-top: 15px;"></p>
      </form>
</div>
</body>
<script type="text/javascript">
$("#resetButton").off();
$("#resetButton").on("click", function(){
	clearAllCookie();
	location.reload();
});
function clearAllCookie() {  
    var keys = document.cookie.match(/[^ =;]+(?=\=)/g);  
    if(keys) {  
        for(var i = keys.length; i--;)  
            document.cookie = keys[i] + '=0;expires=' + new Date(0).toUTCString()  
    }  
} 
$(document).ready(function(){
	console.log("<%=userType%>");
	if("<%=userType%>" != ""){
		console.log($("#userType")[0]);
		$("#userType").val("<%=userType%>");
	}
})
</script>
</html>