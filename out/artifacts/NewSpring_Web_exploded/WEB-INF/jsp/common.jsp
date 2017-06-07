<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="<%=request.getContextPath()%>/index.jsp">系统主界面</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse navbar-ex1-collapse">
          <ul class="nav navbar-nav side-nav">
            <li><a href="<%=request.getContextPath()%>/mvc/getManageMark"><i class="fa fa-dashboard"></i> 项目管理</a></li>
            <li><a href="<%=request.getContextPath()%>/mvc/getMark"><i class="fa fa-edit"></i> 标记系统</a></li>
            <li><a href="<%=request.getContextPath()%>/mvc/getIdentification"><i class="fa fa-desktop"></i> 训练/测试</a></li>
            <li><a href="<%=request.getContextPath()%>/mvc/getApplication"><i class="fa fa-font"></i> 句子识别</a></li>
            <li><a href="<%=request.getContextPath()%>/mvc/getIdentyFile"><i class="fa fa-file"></i> 文件识别</a></li>
          </ul>

          <ul class="nav navbar-nav navbar-right navbar-user">
            <li class="dropdown user-dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> 高守信 <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="#"><i class="fa fa-power-off"></i> Log Out</a></li>
              </ul>
            </li>
          </ul>
        </div>
      </nav>
<div id="mask" class="mask"></div>