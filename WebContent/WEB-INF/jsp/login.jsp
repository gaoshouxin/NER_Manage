<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="header.jsp"%>
	<% request.setCharacterEncoding("utf-8");%>
	<style type="text/css">
		#register {
			position: absolute;
			top: 50%;
			left: 50%;
			transform: translate(-50%, -50%);
		}
	</style>
</head>
<body >
	<div class="col-lg-4" id="register">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title"><i class="fa fa-long-arrow-right"></i> 用户登录页面</h3>
					</div>
					<div class="panel-body">
						<div >
							<form role="form">
								<div class="form-group">
									<label>用户名</label>
									<input class="form-control" id="user_name">
								</div>
								<div class="form-group">
									<label>密码</label>
									<input class="form-control" id="password" type="password" >
								</div>
								<div style="text-Align:center;">
									<button type="button" id="user_confirm" class="btn btn-success" onclick="login()">确认</button>
									<button type="button" id="user_editor" class="btn btn-danger" onclick="reSetLogin()">重置</button>
								</div>
							</form>
							<div class="text-right">
								<a href="<%=request.getContextPath()%>/mvc/getRegister">没有用户? 去注册 <i class="fa fa-arrow-circle-right"></i></a>
							</div>
						</div>
					</div>
				</div>
			</div>
<script src="<%=request.getContextPath()%>/resources/js/reg_login.js"></script>
</body>

</html>