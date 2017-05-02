/**
 * 页面加载后执行的动作
 */
$(document).ready(function() {
	// 获取以标记文件夹
	showMarkedFile();
	showFormatedFile();
});

/**
 * 获取已标记文件夹
 */
function showMarkedFile(){
	$.ajax({
		url : "queryAllMarkedFile",
		type : "post",
		data : {
			user_id : "45"
		},
		dataType : "json",
		async : false,
		success : function(data) {
			var context = "";
			var url = "";
			$.each(data,function(index,item){
				var temp = item.fileName;
        		temp = "<option>" + temp + "</option>";
                context = context + temp;
                temp =item.url;
                temp = "<option>" + temp +"</option>";
                url = url + temp;
			});
            $("#marked_file").append(context);
            $("#file_url").append(url);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("网络错误，请重试");
		},
	});
}

function formatText(){
	var fileName = $("#marked_file option:selected").val();
	var selectIndex =  $("#marked_file option:selected").index();
	$("#file_url").get(0).selectedIndex=selectIndex;
	var fileUrl = $("#file_url option:selected").val();
	$.ajax({
		url : "formatText",
		type : "post",
		data : {
			fileUrl : fileUrl,
			fileName : fileName
		},
		success : function(data) {
			alert(data);
			window.location.reload();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("网络错误，请重试");
		},
	});
}

function showFormatedFile(){
	$.ajax({
		url : "queryAllFormatedFile",
		type : "post",
		data : {
			user_id : "45"
		},
		dataType : "json",
		async : false,
		success : function(data) {
			var context = "";
			var url = "";
			$.each(data,function(index,item){
				var temp = item.fileName;
        		temp = "<option>" + temp + "</option>";
                context = context + temp;
                temp =item.url;
                temp = "<option>" + temp +"</option>";
                url = url + temp;
			});
            $("#formated_file").append(context);
            $("#formated_file_url").append(url);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("网络错误，请重试");
		},
	});
}

function train(){
	var fileName = $("#formated_file option:selected").val();
	$.ajax({
		url : "train",
		type : "post",
		data : {
			fileName : fileName
		},
		success : function(data) {
			alert(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("网络错误，请重试");
		},
	});
}

function test(){
	
}