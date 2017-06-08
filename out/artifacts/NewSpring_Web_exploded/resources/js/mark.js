/**
 * 页面加载后执行的动作
 */
$(document).ready(function() {
	// 获取所有文件
	showAllFile();
});

/**
 * 显示所有文件名
 */
function showAllFile(){
	$.ajax({
		url : "queryAllOrgFile",
		type : "post",
		data : {
			user_id : $.session.get("user_id"),
			file_type : "O"
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
            $("#select_file").append(context);
            $("#file_url").append(url);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("网络错误，请重试");
		},
	});
}

function showText(){
	var context = "";
	var fileName = $("#select_file option:selected").val();
	var selectIndex =  $("#select_file option:selected").index();
	$("#file_url").get(0).selectedIndex=selectIndex;
	var fileUrl = $("#file_url option:selected").val();
	var subString = fileName.slice(-3,fileName.length);
	
	if(subString == "txt"){
		$.ajax({
            url:"getText",
            type:"post",
            dataType: "text",
            data : {
    			file_url : fileUrl
    		},
    		async : false,
            success: function (data) {
                var datas = data.split("\n");
                for (var i = 0; i < datas.length; i++) {
                    datas[i] = $.trim(datas[i]);
                    if(datas[i] != ""){
                        var temp = addStrSign(datas[i], "#");
                        temp = "<li class='content'>" + temp + "</li>";
                        context = context + temp;
                    }
                } 
                $("li").remove(".content");
                $("#markupTextHtml").append(context);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                alert("读取文件失败，请检查下列原因。\n1、是否将标注的文件放在该系统下的【needMark】文件夹中。\n2、文件格式是否正确，仅支持后缀为txt和json。");
            },
        });
	}else{
		$.ajax({
            url:"getText",
            type:"post",
            dataType: "json",
            data : {
    			file_url : fileUrl
    		},
    		async : false,
            success: function (data) {
                $.each(data,function(index,item){
                	if((item.question != "" )&& (item.question != "\"" )&&(item.question != "空" )&&(typeof(item.question)!="undefined")&&($.trim(item.question) != "" )){
                		var temp = addStrSign(item.question.toString(),"#");
                		//var temp = item.question
                		temp = "<li class='content'>" + temp + "</li>";
                        context = context + temp;
                	}
                	if((item.answers != "" )&& (item.answers != "\"" )&&(item.answers != "空" )&&(typeof(item.answers)!="undefined")&&($.trim(item.answers) != "" )){
                		var temp = addStrSign(item.answers.toString(),"#");
                		//var temp = item.answers
                		temp = "<li class='content'>" + temp + "</li>";
                        context = context + temp;
                	}
                });
                $("li").remove(".content");
                $("#markupTextHtml").append(context);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                alert("读取文件失败，请检查下列原因。\n1、是否将标注的文件放在该系统下的【needMark】文件夹中。\n2、文件格式是否正确，仅支持后缀为txt和json。");
            },
        });
	}
}

/**
 * 传入源字符串和标记字符串，返回网页上显示的字符
 * @param sourceStr ： 需要标记的字符串
 * @param signStr ： 用来标记的字符串
 * @returns {string} ： 用来网页显示的字符串
 */
function addStrSign(sourceStr, signStr) {
    var returnStr = "";
    var sourceStrs = sourceStr.split(signStr);
    var len = sourceStrs.length;
    //判断是否标注合格，如果合格则进行替换
    var conunt = getNumOfEqualStr(sourceStr, signStr);
    if (conunt % 2 == 0) {
        //替换文本标记的字符，转换为网页显示的字符
        for (var i = 0; i < len; i++) {
            if (i % 2 == 1) {
                var temp = "<font style='font-size:20px' color='red'>[M]" + sourceStrs[i] + "[/M]</font>";
            } else {
                temp = sourceStrs[i];
            }
            returnStr = returnStr + temp;
        }
    } else {
        returnStr = sourceStr;
    }
    return returnStr;
}

/**
 * 传入源字符串和标记字符串，返回该源字符串里面标记字符串的个数
 * @param sourceStr ： 源字符串
 * @param signStr ： 标记字符串
 */
function getNumOfEqualStr(sourceStr, signStr) {
    var index = 0;
    var conunt = 0;
    while (true) {
        var i = sourceStr.indexOf(signStr, index);// 本次下标
        if (i == -1) {
            break;
        }
        conunt++;
        index = i + signStr.length;
    }
    return conunt;
}

/**
 * 读取网页上的数据，替换网页上的字符串
 * @param signStr ：用什么字符进行标记
 */
function getFileStr(signStr){
    var returnStr = "";
    var $contentNodes = $(".content");
    for (var i = 0; i < $contentNodes.length; i++) {
        var temp = $contentNodes[i].innerHTML;
        var fontNodes = $contentNodes[i].getElementsByTagName('font');
        //(/\n/g) /*/g 表示正则里面的全文匹配
        temp = temp.replace(/<font style="font-size:20px" color=".{0,8}">\[M\]/g, signStr);
        temp = temp.replace(/\[\/M\]<\/font>/g, signStr);
        returnStr = returnStr + $.trim(temp) + "\n";
    }
    return  returnStr;
}

function fake_click(obj) {
    var ev = document.createEvent("MouseEvents");
    ev.initMouseEvent(
        "click", true, false, window, 0, 0, 0, 0, 0
        , false, false, false, false, 0, null
    );
    obj.dispatchEvent(ev);
}
/**
 * 保存文件内容
 * @param name ： 文件的名称
 * @param data ：文件里面的数据
 */
function export_raw(name, data) {
    var urlObject = window.URL || window.webkitURL || window;
    var export_blob = new Blob([data]);
    var save_link = document.createElementNS("http://www.w3.org/1999/xhtml", "a")
    save_link.href = urlObject.createObjectURL(export_blob);
    save_link.download = name;
    fake_click(save_link);
}

//下载标注的文件
function download(){
    var data = getFileStr("#");
    var fileName = $("#select_file option:selected").val();
    export_raw("marked_"+fileName, data);
}

//保存到服务器
function saveServer(){
	var data = getFileStr("#");
	var fileName = $("#select_file option:selected").val();
	showMask();
	$.ajax({
        url:"saveServer",
        type:"post",
        data : {
			fileName : fileName,
			data :data,
            userId : $.session.get("userId")
		},
		async : false,
        success: function (data) {
           alert("保存成功");
            hideMask();
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert("保存失败，请重试");
        },
    });
}


