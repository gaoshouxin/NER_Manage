/**
 * Created by HP on 2017/1/25.
 */


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