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
          <div class="col-lg-12">
            <div class="panel panel-primary">
              <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-bar-chart-o"></i> 实体识别应用</h3>
              </div>
              <div class="panel-body">
                <div class="form-group">
		                <label>原始数据</label>
		                <textarea id="source_data" class="form-control" rows="10"></textarea>
		        </div>
		        <div class="form-group input-group" >
			        	<span  class="input-group-btn">
				            <label><h4>选择模型</h4></label>
				        </span>
				        <select id="select_model" style="width: 20%" class="form-control">
			            </select>
			            <select id="model_url" style="display:none"></select>
			            <button type="button" class="btn btn-primary" onclick="identification()">识别</button>
			     </div>
			     <div class="form-group" style="height: 50%">
			     	<label>识别结果</label>
			     	<div  style="font-size:20px;overflow:auto" >
		                <ol id="result" style="line-height:50px;">
		                </ol>
		            </div>
			     </div>
              </div>
            </div>
          </div>
        </div> 
        
	</div>
</div>
<script src="<%=request.getContextPath()%>/resources/js/application.js"></script>
<script type="text/javascript">

</script>
</body>
</html>