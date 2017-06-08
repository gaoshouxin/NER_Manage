
	$(function() {
		
		$("#cancel_upload").click(function(){
			$("#file_name").val("");
		});
		
		$("#confirm_upload").click(function(){
			var fileName = $("#file_name").val();
			var userId = $("#user_id").val();
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
				user_id : $.session.get("user_id")
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
				userId : $.session.get("user_id"),
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
            	alert(data);
            	showUserInfo();
                $("#user_phone").attr("readOnly",true);
                $("#user_email").attr("readOnly",true);
                $("#user_confirm").attr("disabled",true);
            },
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                alert("网络错误，请重试");
            }
        });
	}
	
