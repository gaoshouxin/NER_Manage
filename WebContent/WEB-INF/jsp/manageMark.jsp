<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="header.jsp"%>
	<%request.setCharacterEncoding("utf-8");%>
</head>
<body>
<div id="wrapper">
	<%@ include file="common.jsp"%>
	<div id ="page-wrapper">
		<div class="row">
          <div class="col-lg-4">
            <div class="panel panel-primary">
              <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-long-arrow-right"></i> 文件上传</h3>
              </div>
              <div class="panel-body">
                <div style="height:500px">
					<div style="height: 20%"></div>
					<form role="form" id="file_form" enctype="multipart/form-data" accept-charset="utf-8" >
						<label>上传文件</label>
						<div class="form-group input-group">
			                <input type="text" id="file_name" class="form-control" readOnly="true">
							<input type="text" id="user_id" class="form-control" style="display: none">
			                <span  class="input-group-btn">
			                  <button  id="choose_file" class="btn btn-primary" type="button">选择文件</button>
			                </span>
			                <input id="upload_file" type="file" name="file" style="display:none">
			            </div>
						<div class="form-group">
			                <label>选择文件类型</label>
			                <select class="form-control"  name="file_type">
			                  <option>待标记文件</option>
			                  <option>训练模板</option>
			                  <option>测试文件</option>
			                  <option>可用模型</option>
			                </select>
			            </div>
			             <div style="text-Align:center;"> 
			            	<button type="button" id="confirm_upload" class="btn btn-success">上传</button>
			            	<button type="button" id="cancel_upload" class="btn btn-warning">取消</button>
						 </div> 
		          </form>
                </div>
              </div>
            </div>
          </div>
          <div class="col-lg-4">
            <div class="panel panel-primary">
              <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-long-arrow-right"></i> 所有文件</h3>
              </div>
              <div class="panel-body">
                 <div style="height:500px">
                	<div style="height: 100%; overflow:auto">
		            	<div class="table-responsive">
				              <table class="table table-hover tablesorter">
				                <thead>
				                  <tr class="success">
				                    <th class="header">文件名<i class="fa "></i></th>
				                    <th class="header">操作<i class="fa "></i></th>
				                  </tr>
				                </thead>
				                 <tbody  id="file-list">
				                 </tbody>
				              </table>
            			</div>
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
			                <label>文件总数</label>
			                <input class="form-control" id ="sum_file" readOnly="true">
			            </div>
			            <div style="text-Align:center;"> 
			            	<button type="button" id="user_confirm" class="btn btn-success" disabled="true" onclick="editConfirm()">确认</button>
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
			$("#user_id").val($.session.get("user_id"));
		});		
	});
</script>
</body>
</html>