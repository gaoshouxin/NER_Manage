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
		 <div id="divCenter" class="col-md-15">
            <div id="topbar" style="background: white">
		        <div class="form-group input-group" >
		        	<span  class="input-group-btn">
			            <label><h4>选择已有文件</h4></label>
			        </span>
			        <select id="select_file" style="width: 15%" class="form-control">
		            </select>
		            <select id="file_url" style="display:none"></select>
		            <button id="show_down" type="button" class="btn btn-info" onclick="showText()">显示文件内容</button>
		            <button id="btnDownload" type="button" class="btn btn-success" onclick="download()">下载标注结果</button>
			     	<button id="save_server" type="button" class="btn btn-warning" onclick="saveServer()">下载标注结果</button>
			     </div>
            </div>
            

            <div style="width:100%;height: 20px"></div>

            <div  style="font-size:20px" onmouseup="markupElement(event)">
                <ol id="markupTextHtml" style="line-height:50px;">
                </ol>
            </div>
        </div>
	</div>
</div>
<script src="<%=request.getContextPath()%>/resources/js/mark.js"></script>
<script type="text/javascript">
var beforeHtml1 = null;
var beforeHtml2 = null;
var beforeHtml3 = null;
var afterHtml1 = null;
var afterHtml2 = null;
var afterHtml3 = null;
var src1 = null;
var isExist = false;

//标注
function markupElement(e) {
    var choosedText = "";
    choosedText = getSelectedText();  //获取到需要添加标记的文本
    if (choosedText.length <= 0) {
        removeMarkupElement(e);
        return;
    }

    //设置颜色
    var radio = document.getElementsByName("optionsColor");
    for(var i=0;i<radio.length;i++){
        if(radio[i].checked){
            labelColor = radio[i].value;
        }
    }

    //以下为撤销做备份
    var txtHtml = $("#markupTextHtml").html();
    beforeHtml3 = beforeHtml2;
    beforeHtml2 = beforeHtml1;
    beforeHtml1 = txtHtml;

    var targ;
    if (!e) var e = window.event
    if (e.target) targ = e.target
    else if (e.srcElement) targ = e.srcElement
    if (targ.nodeType == 3) // defeat Safari bug
        targ = targ.parentNode
    var tname;
    tname = targ.tagName
    var par = targ;
    //   var par=targ.parentNode;   //得到s的父节点
    var parjquery = $(par); //jQuery对象
    var originalFontSize = parjquery.css("font-size");
    //   alert(originalFontSize  );  //12px

    //  alert( par.tagName  )

    var targTextContent = $(targ).contents().filter(function (index, content) {
        return content.nodeType === 3;
    }).text();

    // alert("选择文本:"+chooosedText+" 标签:" + tname +   " content:"+ targTextContent);

    //替换：
    var colorStringBegin = "<font style=\"font-size:" + originalFontSize + "\" color="+ "red" +">";
    var colorStringEnd = "" + "</font>";


    //替换页面html

    var markupText = "";
    //  [M]头痛[/M]
    var markup = "M";
    markupText = colorStringBegin + "[" + markup + "]" + choosedText + "[/" + markup + "]" + colorStringEnd;//加标注

    try {//IE低版本
        var range = document.selection.createRange();
        if (range.text != "") {
            range.pasteHTML(markupText);
        }
    } catch (err) {//IE高版本
        var range = document.getSelection().getRangeAt(0);
        var nnode = document.createElement("b");
        range.surroundContents(nnode);
        nnode.innerHTML = markupText;
        var replacedText = $("#markupTextHtml").html().replace("<b>", "").replace("</b>", "");
        $("#markupTextHtml").html(replacedText);
    }

    //以下代码为替换编辑框中的html源文件
   // updateSourceText();

    afterHtml1=afterHtml2=afterHtml3=null;

}

//获取到需要添加标记的文本
function getSelectedText() {
    if (window.getSelection) {
        return window.getSelection().toString();
    } else if (document.getSelection) {
        return document.getSelection();
    } else if (document.selection) {
        return document.selection.createRange().text;
    }
}

//单击删除标注
function removeMarkupElement(event) {
    try{
        // alert("df");
        var targ;//dom
        // if (!e) var e = window.event;
        if (!e) var e = event || window.event  //兼容IE, Firefox
        if (e.target) {
            targ = e.target;
        } else if (e.srcElement) {
            targ = e.srcElement;
        }
        if (targ.nodeType == 3) { // defeat Safari bug
            targ = targ.parentNode;
        }
        var tname;
        tname = targ.tagName;
        var targTextContent = $(targ).contents().filter(function (index, content) {
            return content.nodeType === 3;
        }).text();
        // 获得DOM节点的文本：
        //  [M]头痛[/M]
        var markupRemovedPattern = new RegExp("][^\\[]+\\[/", "i");  //正则
        var MarkupRemovedContent;
        MarkupRemovedContent = markupRemovedPattern.exec(targTextContent) //得到一个数组

        var contentTemp = "" + MarkupRemovedContent[0];
        var markupContent = (contentTemp.replace(/]/g, "")).replace(/\[\//g, "");//替换字符:  [ 或   ] ,得到 标记的内容,  以便后续 替换

        if (MarkupRemovedContent == null) {
            MarkupRemovedContent = "";
        }

        //以下为撤销做备份
        var txtHtml = $("#markupTextHtml").html();
        beforeHtml3 = beforeHtml2;
        beforeHtml2 = beforeHtml1;
        beforeHtml1 = txtHtml;

        $(targ).html(markupContent);
        $(targ).attr("color", "");
        $(targ).attr("style", "");
        // alert($(targ).html());
        var replaceSource1 = "<font color=\"\">" + $(targ).html() + "</font>";//IE
        var replaceSource2 = "<font style=\"\" color=\"\">" + $(targ).html() + "</font>";//firefox
        var replaceSource3 = "<font color=\"\" style=\"\">" + $(targ).html() + "</font>";//chrome
        var replaceText = $(targ).html();
        var txt = $("#markupTextHtml").html().replace(replaceSource1, replaceText).replace(replaceSource2, replaceText).replace(replaceSource3, replaceText);
        $("#markupTextHtml").html(txt);


        //以下代码修改源文件
        var newSourceMarkupTextHtml = $("#markupTextHtml").html();
        newSourceMarkupTextHtml=processSourceTextFormat(newSourceMarkupTextHtml);
        $("#markupTextSourceTextarea").html(newSourceMarkupTextHtml);


        afterHtml1=afterHtml2=afterHtml3=null;
    }catch(e){

    }
}

//将<li class="content"> </li> 替换成空，仅仅保存该标签里面的内容
function processSourceTextFormat(text){
    text=text.replace(/&lt;/g, "<").replace(/&gt;/g, ">").replace(/&nbsp;/g, " ").replace(/&#39;/g, "\'").replace(/&quot;/g, "\"").replace(/<li class="content">/g, "").replace(/<\/li>/g, "\n").trim();
    return text;
}
</script>
</body>
</html>