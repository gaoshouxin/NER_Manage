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
                <h3 class="panel-title"><i class="fa fa-long-arrow-right"></i> CRF训练</h3>
              </div>
              <div class="panel-body">
                <div style="height:570px">
                	<label>选择已标记文件</label>
                	<div class="form-group" >
				        <select id="marked_file" style="width: 100%" class="form-control">
			            </select>
			            <select id="file_url" style="display:none"></select>
			     	</div>
			     	<div class="form-group" >
			            <button type="button" style="width: 100%" class="btn btn-primary" onclick="formatText()">格式化文件</button>
			     	</div>
					<div class="form-group " >
						<label>选择已格式化文件</label>
						<select id="formated_file" style="width: 100%" class="form-control">
						</select>
						<select id="formated_file_url" style="display:none"></select>
					</div>

			     	<div class="form-group">
			                <label>输入交叉验证数值</label>
			                <input class="form-control" id ="crossed_val" placeholder="请输入整数值" >
			        </div>
			     	<div class="form-group" >
			            <button type="button" style="width: 100%" class="btn btn-primary " onclick="produceCrossedData()">生成交叉实验数据</button>
			     	</div>
					<div class="form-group " >
						<label>选择待训练文件</label>
						<select id="train_file" style="width: 100%" class="form-control">
						</select>
						<select id="train_file_url" style="display:none"></select>
					</div>

			     	<div class="form-group " >
			     		<label>选择模板文件</label>
			            <select id="template_file" style="width: 100%" class="form-control">
			            </select>
			            <select id="template_file_url" style="display:none"></select>
			     	</div>
			     	
			     	<div class="form-group">
			                <label>输入训练参数</label>
			                <input class="form-control" id ="train_param">
			        </div>
			        <div class="form-group" >
			            <button type="button" style="width: 100%" class="btn btn-success " onclick="train()">训练</button>
			     	</div>
			     	
	            </div>
              </div>
            </div>
          </div>
          <div class="col-lg-4">
            <div class="panel panel-primary">
              <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-long-arrow-right"></i> CRF训练</h3>
              </div>
              <div class="panel-body">
                <div style="height:570px">
                	
			     	<div class="form-group">
			                <label>训练数据</label>
			                <textarea id="trained_data" class="form-control" rows="27" style="overflow:auto"></textarea>
			        </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-lg-4">
            <div class="panel panel-primary">
              <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-long-arrow-right"></i> CRF测试</h3>
              </div>
              <div class="panel-body">
                <div style="height:570px">
			     	<div class="form-group" >
			     		<label>选择测试文件</label>
			            <select id="test_file" style="width: 100%" class="form-control">
			            </select>
			            <select id="test_file_url" style="display:none"></select>
			     	</div>
			     	<div class="form-group " >
			     		<label>选择模型</label>
			            <select id="select_model" style="width: 100%" class="form-control">
			            </select>
			            <select id="model_url" style="display:none"></select>
			     	</div>
			     	<div class="form-group" >
			            <button type="button" style="width: 100%" class="btn btn-success " onclick="testModel()">测试</button>
			     	</div>
			     	<div class="form-group">
			                <label>测试数据</label>
			                <textarea id="tested_data" class="form-control" rows="8" style="overflow:auto"></textarea>
			        </div>
			        <div class="form-group">
			                <label>模型精度</label>
			                 <table class="table table-hover tablesorter">
				                <thead>
				                  <tr class="success">
				                    <th class="header">项目<i class="fa "></i></th>
				                    <th class="header">精度<i class="fa "></i></th>
				                  </tr>
				                </thead>
				                 <tbody  id="file-list">
				                 <tr class="warning">
				                    <td>准确率</td>
				                    <td id="accuracyRate"></td>
				                 </tr>
				                 <tr class="warning">
				                    <td>召回率</td>
				                    <td id="recallRate"></td>
				                 </tr>
				                 <tr class="warning">
				                    <td>F值</td>
				                    <td id="fValue"></td>
				                 </tr>
				                 </tbody>
				            </table>
			        </div>
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