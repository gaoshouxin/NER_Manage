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
          <div class="col-lg-6">
            <div class="panel panel-primary">
              <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-long-arrow-right"></i> CRF训练</h3>
              </div>
              <div class="panel-body">
                <div style="height:550px">
                	<div class="form-group input-group" >
		        	
			        <select id="marked_file" style="width: 35%" class="form-control">
		            </select>
		            <button type="button" class="btn btn-info" onclick="formatText()">格式化文件</button>
		            <select id="file_url" style="display:none"></select>
		            
		            <select id="formated_file" style="width: 35%" class="form-control">
		            </select>
		            <select id="formated_file_url" style="display:none"></select>
		            <button  type="button" class="btn btn-success" onclick="train()">训练</button>
			     </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-lg-6">
            <div class="panel panel-primary">
              <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-long-arrow-right"></i> CRF测试</h3>
              </div>
              <div class="panel-body">
                <div style="height:550px">
                </div>
              </div>
            </div>
          </div>
        </div>
	</div>
</div>
<script src="<%=request.getContextPath()%>/resources/js/identification.js"></script>
<script type="text/javascript">

</script>
</body>
</html>