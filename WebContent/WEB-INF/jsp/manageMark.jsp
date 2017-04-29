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
                	<label>已有项目</label>
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
					<div class="form-group input-group">
		                <input type="text" class="form-control">
		                <span class="input-group-btn">
		                  <button class="btn btn-success" type="button">上传文件</button>
		                </span>
		             </div>
					<div style="height: 2%"></div>
                	<label>已有项目</label>
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
                <h3 class="panel-title"><i class="fa fa-long-arrow-right"></i> Bar Graph Example</h3>
              </div>
              <div class="panel-body">
                <div id="morris-chart-bar" style="position: relative;"><svg height="342" version="1.1" width="306" xmlns="http://www.w3.org/2000/svg" style="overflow: hidden; position: relative;"><desc style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);">Created with Raphaël 2.1.0</desc><defs style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></defs><text x="43.53125" y="275.33934579000004" text-anchor="end" font="10px &quot;Arial&quot;" stroke="none" fill="#888888" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0); text-anchor: end; font-style: normal; font-variant: normal; font-weight: normal; font-stretch: normal; font-size: 12px; line-height: normal; font-family: sans-serif;" font-size="12px" font-family="sans-serif" font-weight="normal"><tspan dy="4.0112207900000385" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);">0</tspan></text><path fill="none" stroke="#aaaaaa" d="M56.03125,275.33934579000004H281" stroke-width="0.5" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></path><text x="43.53125" y="212.75450934250003" text-anchor="end" font="10px &quot;Arial&quot;" stroke="none" fill="#888888" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0); text-anchor: end; font-style: normal; font-variant: normal; font-weight: normal; font-stretch: normal; font-size: 12px; line-height: normal; font-family: sans-serif;" font-size="12px" font-family="sans-serif" font-weight="normal"><tspan dy="4.004509342500029" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);">500</tspan></text><path fill="none" stroke="#aaaaaa" d="M56.03125,212.75450934250003H281" stroke-width="0.5" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></path><text x="43.53125" y="150.16967289500002" text-anchor="end" font="10px &quot;Arial&quot;" stroke="none" fill="#888888" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0); text-anchor: end; font-style: normal; font-variant: normal; font-weight: normal; font-stretch: normal; font-size: 12px; line-height: normal; font-family: sans-serif;" font-size="12px" font-family="sans-serif" font-weight="normal"><tspan dy="4.013422895000019" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);">1,000</tspan></text><path fill="none" stroke="#aaaaaa" d="M56.03125,150.16967289500002H281" stroke-width="0.5" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></path><text x="43.53125" y="87.58483644750001" text-anchor="end" font="10px &quot;Arial&quot;" stroke="none" fill="#888888" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0); text-anchor: end; font-style: normal; font-variant: normal; font-weight: normal; font-stretch: normal; font-size: 12px; line-height: normal; font-family: sans-serif;" font-size="12px" font-family="sans-serif" font-weight="normal"><tspan dy="4.00671144750001" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);">1,500</tspan></text><path fill="none" stroke="#aaaaaa" d="M56.03125,87.58483644750001H281" stroke-width="0.5" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></path><text x="43.53125" y="24.99999999999997" text-anchor="end" font="10px &quot;Arial&quot;" stroke="none" fill="#888888" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0); text-anchor: end; font-style: normal; font-variant: normal; font-weight: normal; font-stretch: normal; font-size: 12px; line-height: normal; font-family: sans-serif;" font-size="12px" font-family="sans-serif" font-weight="normal"><tspan dy="3.9999999999999716" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);">2,000</tspan></text><path fill="none" stroke="#aaaaaa" d="M56.03125,24.99999999999997H281" stroke-width="0.5" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></path><text x="262.25260416666663" y="287.83934579000004" text-anchor="middle" font="10px &quot;Arial&quot;" stroke="none" fill="#888888" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0); text-anchor: middle; font-style: normal; font-variant: normal; font-weight: normal; font-stretch: normal; font-size: 12px; line-height: normal; font-family: sans-serif;" font-size="12px" font-family="sans-serif" font-weight="normal" transform="matrix(0.8192,-0.5736,0.5736,0.8192,-137.719,223.2867)"><tspan dy="4.0112207900000385" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);">iPhone 5</tspan></text><text x="224.7578125" y="287.83934579000004" text-anchor="middle" font="10px &quot;Arial&quot;" stroke="none" fill="#888888" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0); text-anchor: middle; font-style: normal; font-variant: normal; font-weight: normal; font-stretch: normal; font-size: 12px; line-height: normal; font-family: sans-serif;" font-size="12px" font-family="sans-serif" font-weight="normal" transform="matrix(0.8192,-0.5736,0.5736,0.8192,-147.3622,203.8031)"><tspan dy="4.0112207900000385" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);">iPhone 4S</tspan></text><text x="187.26302083333334" y="287.83934579000004" text-anchor="middle" font="10px &quot;Arial&quot;" stroke="none" fill="#888888" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0); text-anchor: middle; font-style: normal; font-variant: normal; font-weight: normal; font-stretch: normal; font-size: 12px; line-height: normal; font-family: sans-serif;" font-size="12px" font-family="sans-serif" font-weight="normal" transform="matrix(0.8192,-0.5736,0.5736,0.8192,-151.5868,180.8152)"><tspan dy="4.0112207900000385" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);">iPhone 4</tspan></text><text x="149.76822916666669" y="287.83934579000004" text-anchor="middle" font="10px &quot;Arial&quot;" stroke="none" fill="#888888" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0); text-anchor: middle; font-style: normal; font-variant: normal; font-weight: normal; font-stretch: normal; font-size: 12px; line-height: normal; font-family: sans-serif;" font-size="12px" font-family="sans-serif" font-weight="normal" transform="matrix(0.8192,-0.5736,0.5736,0.8192,-164.5786,163.348)"><tspan dy="4.0112207900000385" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);">iPhone 3GS</tspan></text><text x="112.2734375" y="287.83934579000004" text-anchor="middle" font="10px &quot;Arial&quot;" stroke="none" fill="#888888" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0); text-anchor: middle; font-style: normal; font-variant: normal; font-weight: normal; font-stretch: normal; font-size: 12px; line-height: normal; font-family: sans-serif;" font-size="12px" font-family="sans-serif" font-weight="normal" transform="matrix(0.8192,-0.5736,0.5736,0.8192,-168.5239,139.8583)"><tspan dy="4.0112207900000385" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);">iPhone 3G</tspan></text><text x="74.77864583333333" y="287.83934579000004" text-anchor="middle" font="10px &quot;Arial&quot;" stroke="none" fill="#888888" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0); text-anchor: middle; font-style: normal; font-variant: normal; font-weight: normal; font-stretch: normal; font-size: 12px; line-height: normal; font-family: sans-serif;" font-size="12px" font-family="sans-serif" font-weight="normal" transform="matrix(0.8192,-0.5736,0.5736,0.8192,-167.3461,112.8195)"><tspan dy="4.0112207900000385" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);">iPhone</tspan></text><rect x="60.718098958333336" y="258.31627027628" width="28.12109375" height="17.023075513720016" r="0" rx="0" ry="0" fill="#0b62a4" stroke="#000" stroke-width="0" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></rect><rect x="98.21289062499999" y="258.19110060338505" width="28.12109375" height="17.148245186614986" r="0" rx="0" ry="0" fill="#0b62a4" stroke="#000" stroke-width="0" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></rect><rect x="135.70768229166666" y="240.91768574387504" width="28.12109375" height="34.421660046125" r="0" rx="0" ry="0" fill="#0b62a4" stroke="#000" stroke-width="0" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></rect><rect x="173.20247395833334" y="227.77487008990002" width="28.12109375" height="47.56447570010002" r="0" rx="0" ry="0" fill="#0b62a4" stroke="#000" stroke-width="0" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></rect><rect x="210.697265625" y="193.35321004377502" width="28.12109375" height="81.98613574622502" r="0" rx="0" ry="0" fill="#0b62a4" stroke="#000" stroke-width="0" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></rect><rect x="248.19205729166666" y="78.69778967195501" width="28.12109375" height="196.64155611804503" r="0" rx="0" ry="0" fill="#0b62a4" stroke="#000" stroke-width="0" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></rect></svg><div class="morris-hover morris-default-style" style="display: none;"></div></div>
                <div class="text-right">
                  <a href="#">View Details <i class="fa fa-arrow-circle-right"></i></a>
                </div>
              </div>
            </div>
          </div>
        </div>
	</div>
</div>
<script src="<%=request.getContextPath()%>/resources/js/manageMark.js"></script>

</body>
</html>