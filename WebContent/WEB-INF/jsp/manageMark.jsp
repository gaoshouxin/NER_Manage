<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="header.jsp"%>
</head>
<body>
<div id="wrapper">
	<%@ include file="common.jsp"%>
	<div id ="page-wrapper">
		<div class="row">
          <div class="col-lg-4">
            <div class="panel panel-primary">
              <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-long-arrow-right"></i> 项目管理</h3>
              </div>
              <div class="panel-body">
                <div style="height:500px">
                	<form role="form">
                		<div class="form-group">
			                <label>项目名称</label>
			                <input class="form-control" id="project_name" placeholder="请输入项目名称" >
			            </div>
			            <div class="form-group">
			                <label>标注字符</label>
			                <input class="form-control" id ="mark_sign" placeholder="字符长度为1，建议使用 #" >
			            </div>
			            <div style="text-Align:center;"> 
			            	<button type="button" id="submit-project" class="btn btn-success">提交</button>
			            	<button type="button" id="reset-project" class="btn btn-warning">重置</button>
						</div> 
                	</form>
                	<div style="height: 2%"></div>
                	<label>所有项目</label>
                	<div style="height: 55%; overflow:auto">
		            	<ul class="list-group" id="project-list">
						</ul>
					</div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-lg-4">
            <div class="panel panel-primary">
              <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-long-arrow-right"></i> 文件管理</h3>
              </div>
              <div class="panel-body">
                 <div style="height:500px">
                 	<form role="form" id="file_form" enctype="multipart/form-data">
                 	  <label>文件上传</label>
						<div class="form-group input-group">
			                <input type="text" id="file_name" class="form-control" readOnly="true">
			                <span  class="input-group-btn">
			                  <button  id="choose_file" class="btn btn-primary" type="button">选择文件</button>
			                </span>
			                <input id="upload_file" type="file" name="file" style="display:none">
			             </div>
			             <div style="text-Align:center;"> 
			            	<button type="button" id="confirm_upload" class="btn btn-success">上传</button>
			            	<button type="button" id="cancel_upload" class="btn btn-warning">取消</button>
						 </div> 
		             </form>
		            <div style="height: 2%"></div>
                	<label>所有文件</label>
                	<div style="height: 70%; overflow:auto">
		            	<ul class="list-group" id="file-list">
						</ul>
					</div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-lg-4">
            <div class="panel panel-primary">
              <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-long-arrow-right"></i> 当前用户信息</h3>
              </div>
              <div class="panel-body">
                <div style="height:500px">
                	<form role="form">
                		<div class="form-group">
			                <label>用户名</label>
			                <input class="form-control" id="user_name" readOnly="true" >
			            </div>
			            <div class="form-group">
			                <label>联系电话</label>
			                <input class="form-control" id ="user_phone" readOnly="true" >
			            </div>
			            <div class="form-group">
			                <label>电子邮箱</label>
			                <input class="form-control" id ="user_email" readOnly="true" >
			            </div>
			            <div class="form-group">
			                <label>更新时间</label>
			                <input class="form-control" id ="update_time" readOnly="true">
			            </div>
			            <div class="form-group">
			                <label>项目总数</label>
			                <input class="form-control" id ="sum_project" readOnly="true">
			            </div>
			             <div class="form-group">
			                <label>文件总数</label>
			                <input class="form-control" id ="sum_file" readOnly="true">
			            </div>
			            <div style="text-Align:center;"> 
			            	<button type="button" id="user_confirm" class="btn btn-success" disabled="true">确认</button>
			            	<button type="button" id="user_editor" class="btn btn-danger">编辑</button>
						</div> 
                	</form>
                </div>
              </div>
            </div>
          </div>
        </div>
	</div>
</div>
<script src="<%=request.getContextPath()%>/resources/js/manageMark.js"></script>
<script type="text/javascript">
	$("#choose_file").click(function(){
		$("#upload_file").click();
		$("#upload_file").change(function(){
			$("#file_name").val($("#upload_file").val());
		});		
	});
</script>
</body>
</html>