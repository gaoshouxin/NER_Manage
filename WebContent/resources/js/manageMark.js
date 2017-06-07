
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
						$("#project-list").empty()
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
		
		$("#cancel_upload").click(function(){
			$("#file_name").val("");
		});
		
		$("#confirm_upload").click(function(){
			var fileName = $("#file_name").val();
			if($.trim(fileName) == ""){
				alert("请选择文件");
				return ;
			}
			var form = new FormData($("#file_form")[0]);
            showMask();
			$.ajax({  
			      url:"fileUpload",  
			      type:"post",  
			      data:form,  
			      cache: false,
			      async: false, 
			      processData: false,  
			      contentType: false,  
			      success:function(data){
                      	hideMask();
			            alert("上传成功！");
			            $("#file-list").empty();
			            showAllFile();
			      },  
			      error:function(e){  
			          alert("网络错误，请重试！！");  
			      }  
			});   
		});
		
		$("#user_editor").click(function(){
			$("#user_phone").attr("readOnly",false);
			$("#user_email").attr("readOnly",false);
			$("#user_confirm").attr("disabled",false);
		});

	});
	
	
	
	/**
	 * 页面加载后执行的动作
	 */
	$(document).ready(function() {
		showAllFile();
		showUserInfo();
	});
	

	function showAllFile(){
		$.ajax({
			url : "queryAllFile",
			type : "post",
			data : {
				user_id : "45"
			},
			dataType : "json",
			async : false,
			success : function(data) {
				var context = "";
				$.each(data,function(index,item){
					var temp = item.fileName;
					var fileType = item.fileType;
            		temp = "<tr clss='warning'><td>" + temp+"</td><td ><button type='button' class='btn btn-danger' onclick='deleteFile(this)'>删除</button></td>"+"</tr>";
                    context = context + temp;
				});
                $("#file-list").append(context);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("网络错误，请重试");
			},
		});
	}
	
	function deleteFile(obj){
		var fileName = $(obj).parent().prev().text();
		$.ajax({
			url : "deleteFile",
			type : "post",
			data : {
				userId : "45",
				fileName : fileName
			},
			dataType : "text",
			success : function(data) {
				alert("删除成功！");
				$("#file-list").empty();
				showAllFile();
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("网络错误，请重试");

			}
		});
	}

	function showUserInfo() {
		var user_id = $.session.get("user_id");
		if( typeof(user_id) == "undefined"){
            window.location.href = "getLogin";
		}
        $.ajax({
            url : "queryUserInfo",
            type : "get",
            data : {
                user_id : user_id
            },
            dataType : "json",
            success : function(data) {
                $.each(data,function(index,item){
                	var user_name = item.userName;
                	var user_phone = item.userPhone;
                	var user_email = item.userEmail;
                	var file_sum = item.fileSum;
                	var update_time = item.updateTime;
                	$("#user_name").val(user_name);
                	$("#user_phone").val(user_phone);
                	$("#user_email").val(user_email);
                	$("#sum_file").val(file_sum);
                	$("#update_time").val(update_time);
                });
            },
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                alert("网络错误，请登录重试");
                window.location.href = "getLogin";
            }
        });
    }

    function editConfirm() {
		var user_phone = $("#user_phone").val();
		var user_email = $("#user_email").val();
		var user_id = $.session.get("user_id");
        $.ajax({
            url : "updateUserInfo",
            type : "post",
            data : {
                user_id : user_id,
				user_phone : user_phone,
				user_email : user_email
            },
            dataType : "text",
            success : function(data) {

            },
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                alert("网络错误，请重试");
            }
        });
	}
	
