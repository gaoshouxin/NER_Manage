<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>基于CRF的命名实体识别系统</title>

    <!-- Bootstrap core CSS -->
    <link href="<%=request.getContextPath()%>/resources/css/bootstrap.css" rel="stylesheet" type="text/css"/>

    <!-- Add custom CSS here -->
    <link href="<%=request.getContextPath()%>/resources/css/sb-admin.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/resources/css/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    
      <!-- JavaScript -->
    <script src="<%=request.getContextPath()%>/resources/js/jquery-1.11.2.min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/bootstrap.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/jquerysession.js"></script>
    <style type="text/css">
        .mask {
            position: absolute; top: 0px; filter: alpha(opacity=60); background-color: #777;
            z-index: 1002; left: 0px;
            opacity:0.5; -moz-opacity:0.5;
        }
    </style>

    <script type="text/javascript">
        //兼容火狐、IE8
        //显示遮罩层
        function showMask(){
            $("#mask").css("height",$(document).height());
            $("#mask").css("width",$(document).width());
            $("#mask").show();
        }
        //隐藏遮罩层
        function hideMask(){

            $("#mask").hide();
        }
    </script>

