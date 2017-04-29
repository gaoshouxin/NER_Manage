
	$(function() {
		$("#submit-project").click(function() {
			var projectName = $("#project_name").val();
			var markSign = $("#mark_sign").val();
			if (($.trim(projectName) == "")) {
				alert("请输入工程名");
				return;
			} else if (markSign.length > 1) {
				alert("字符太长，只能输入一个字符");
				return;
			}
			if (($.trim(projectName) == "")) {
				markSign = "#";
			}
			($.trim(projectName) == "")
			$.ajax({
				url : "createProject",
				type : "post",
				data : {
					project_name : projectName,
					mark_sign : markSign
				},
				dataType : "text",
				async : false,
				success : function(data) {
					if (data == "1") {
						alert("创建成功");
						showProjetList();
					} else {
						alert("该项目名已存在");
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("网络错误，请重试");
				},
			});
		});

		$("#reset-project").click(function() {
			$("#mark_sign").val("").focus(); //清空并获得焦点
			$("#project_name").val("").focus();
		});

	});
	
	/**
	 * 页面加载后执行的动作
	 */
	$(document).ready(function() {
		// 获取所有工程
		showProjetList()
	});
	
	function showProjetList(){
		$.ajax({
			url : "queryAllProject",
			type : "post",
			data : {
				user_id : "45"
			},
			dataType : "json",
			async : false,
			success : function(data) {
				var context = "";
				$.each(data,function(index,item){
					var temp = item.proName;
            		temp = "<li class='list-group-item'>" + temp + "</li>";
                    context = context + temp;
				});
                $("#project-list").append(context);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("网络错误，请重试");
			},
		});
	}
