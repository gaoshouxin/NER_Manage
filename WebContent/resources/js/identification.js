/**
 * 页面加载后执行的动作
 */
$(document).ready(function() {
	// 页面载入时执行的操作
	showMarkedFile();
	showFormatedFile();
	showAllModel();
    showTarinFile();
    showTestFile();
    showTemplateFile();
    $("#train_param").val("-f 2 -c 1.5 -p 14");
});

/**
 * 获取已标记文件
 */
function showMarkedFile(){
	$.ajax({
		url : "queryAllMarkedFile",
		type : "post",
		data : {
			user_id : $.session.get("user_id")
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
			alert("网络错误，请刷新");
		},
	});
}
/**
 * 格式化文件，生成crf训练需要的格式
 */
function formatText(){
	showMask();
	var fileName = $("#marked_file option:selected").val();
	var selectIndex =  $("#marked_file option:selected").index();
	$("#file_url").get(0).selectedIndex=selectIndex;
	var fileUrl = $("#file_url option:selected").val();
	$.ajax({
		url : "formatText",
		type : "post",
		data : {
			fileUrl : fileUrl,
			fileName : fileName,
            userId : $.session.get("user_id")
		},
		success : function(data) {
			hideMask();
			alert(data);
			window.location.reload();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("网络错误，请重试");
		}
	});
}

function showFormatedFile(){
	$.ajax({
		url : "queryAllFormatedFile",
		type : "post",
		data : {
			user_id : $.session.get("user_id")
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
		}
	});
}

function showAllModel(){
	$.ajax({
		url : "queryAllModel",
		type : "post",
		data : {
			user_id : $.session.get("user_id")
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
            $("#select_model").append(context);
            $("#model_url").append(url);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("网络错误，请重试");
		}
	});
}

function  produceCrossedData(){
	showMask();
    var fileName = $("#formated_file option:selected").val();
    var selectIndex =  $("#formated_file option:selected").index();
    $("#formated_file_url").get(0).selectedIndex=selectIndex;
    var fileUrl = $("#formated_file_url option:selected").val();
    var crossedVal = $("#crossed_val").val();
    if (($.trim(crossedVal) == "")) {
        alert("请输入交叉验证数值");
        hideMask();
        return;
    }
    if((crossedVal|0)!=crossedVal){
    	hideMask();
    	alert("请输入二进制在32位以内的整数");
    	return ;
	}
    $.ajax({
        url : "produceCrossedData",
        type : "post",
        data : {
        	fileName: fileName,
            fileUrl : fileUrl,
			crossedVal :crossedVal,
            userId : $.session.get("user_id")
        },
        success : function(data) {
            hideMask();
            alert(data);
            window.location.reload();
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert("网络错误，请重试");
        }
    });
}

function showTarinFile() {
    $.ajax({
        url : "queryAllTrainFile",
        type : "post",
        data : {
            user_id : $.session.get("user_id")
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
            $("#train_file").append(context);
            $("#train_file_url").append(url);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert("网络错误，请重试");
        }
    });
}

function showTestFile() {
    $.ajax({
        url : "queryAllTestFile",
        type : "post",
        data : {
            user_id : $.session.get("user_id")
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
            $("#test_file").append(context);
            $("#test_file_url").append(url);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert("网络错误，请重试");
        }
    });
}

function train(){
	showMask();
    var selectIndex =  $("#train_file option:selected").index();
    $("#train_file_url").get(0).selectedIndex=selectIndex;
    var trainFileUrl = $("#train_file_url option:selected").val();

    var selectIndex =  $("#formated_file option:selected").index();
    $("#template_file_url").get(0).selectedIndex=selectIndex;
    var templateFileUrl = $("#template_file_url option:selected").val();
    var parameter = $("#train_param").val();
    if (($.trim(parameter) == "")) {
        alert("请输入训练参数");
        hideMask();
        return;
    }
    $.ajax({
        url : "train",
        type : "post",
        data : {
        	trainFileUrl : trainFileUrl,
            templateFileUrl : templateFileUrl,
            parameter : parameter,
            user_id : $.session.get("user_id")
        },
        success : function(data) {
            $("#trained_data").val(data);
            $("#select_model").empty();
            showAllModel();
            hideMask();
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert("网络错误，请重试");
            hideMask();
        }
    });
}

function testModel(){
    showMask();
    var selectIndex =  $("#test_file option:selected").index();
    $("#test_file_url").get(0).selectedIndex=selectIndex;
    var testFileUrl = $("#test_file_url option:selected").val();

    var selectIndex =  $("#select_model option:selected").index();
    $("#model_url").get(0).selectedIndex=selectIndex;
    var modelUrl = $("#model_url option:selected").val();

    $.ajax({
        url : "testModel",
        type : "post",
        data : {
            testFileUrl : testFileUrl,
			modelUrl : modelUrl
        },
        dataType : "json",
        async : false,
        success : function(data) {
            var testData ="";
            var accuracyRate = "";
            var recallRate = "";
            var fValue = "";
            $.each(data,function(index,item){
                testData =item.testData;
                accuracyRate = item.accuracyRate;
                recallRate = item.recallRate;
                fValue = item.fValue;
            });
            $("#tested_data").val(testData);
            $("#accuracyRate").text(accuracyRate);
            $("#recallRate").text(recallRate);
            $("#fValue").text(fValue);
            hideMask();
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert("网络错误，请重试");
            hideMask();
        }
    });
}
function  showTemplateFile() {
    $.ajax({
        url : "queryAllTemplateFile",
        type : "post",
        data : {
            user_id : $.session.get("user_id")
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
            $("#template_file").append(context);
            $("#template_file_url").append(url);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert("网络错误，请重试");
        }
    });
}
