/**
 * 页面加载后执行的动作
 */
$(document).ready(function() {
	showAllModel();
});

function showAllModel(){
    var user_id = $.session.get("user_id");
	$.ajax({
		url : "queryAllModel",
		type : "post",
		data : {
			user_id : user_id
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

function identification(){
    showMask();
	var text = $("#source_data").val();
    var selectIndex =  $("#select_model option:selected").index();
    $("#model_url").get(0).selectedIndex=selectIndex;
    var modelUrl = $("#model_url option:selected").val();
	$.ajax({
		url : "identity",
		type : "post",
		data : {
			text : text,
			modelUrl : modelUrl
		},
		success : function(data) {
			var context= "";
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
            $("#result").append(context);
            hideMask();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("网络错误，请重试");
			hideMask();
		}
	});
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