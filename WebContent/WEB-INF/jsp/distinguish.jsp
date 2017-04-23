<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>基于CRF的命名实体识别系统</title>

    <!-- Bootstrap core CSS -->
    <link href="<%=request.getContextPath()%>/resources/css/bootstrap.css" rel="stylesheet" type="text/css"/>

    <!-- Add custom CSS here -->
    <link href="<%=request.getContextPath()%>/resources/css/sb-admin.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/font-awesome/css/font-awesome.min.css">
    <!-- Page Specific CSS -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/morris-0.4.3.min.css">
    
      <!-- JavaScript -->
    <script src="<%=request.getContextPath()%>/resources/js/jquery-1.11.2.min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/bootstrap.js"></script>
    <!-- Page Specific Plugins -->    
    <script src="<%=request.getContextPath()%>/resources/js/raphael-min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/tablesorter/jquery.tablesorter.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/tablesorter/tables.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/Tools.js"></script>
    
    <script type="text/javascript">
        var beforeHtml1 = null;
        var beforeHtml2 = null;
        var beforeHtml3 = null;
        var afterHtml1 = null;
        var afterHtml2 = null;
        var afterHtml3 = null;

        //var msg = document.getElementById("msg");
        var src1 = null;
        var isExist = false;
        $().ready(function () {
            $('#btnStartLabel').click(function () {
                setDivMmarkupTextHtml();
            });
            $('#btnHtml').click(function () {
                showDivMmarkupTextHtml();
            });
            $('#btnTxt').click(function () {
                showDivMmarkupTextSourceTextarea();
            });
        });
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

                $("#btnBackward").attr("disabled",false);

                afterHtml1=afterHtml2=afterHtml3=null;
                $("#btnForward").attr("disabled",true);
            }catch(e){

            }
        }
		
        /**
         * 读取文件方法
         * @param filePath 文件的路径
         */
        function readFile(filePath) {
        	var context = "";
            $.ajax({
                url:filePath,
                type:"GET",
                dataType: 'text',
                success: function (data) {
                    var datas = data.split("\n");
                    for (var i = 0; i < datas.length; i++) {
                        datas[i] = $.trim(datas[i]);
                        if(datas[i] != ""){
                            var temp = addStrSign(datas[i], "##");
                            temp = "<li class='content'>" + temp + "</li>";
                            context = context + temp;
                        }
                    }
                    $("li").remove(".content");
                    $("#markupTextHtml").append(context);
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                	alert(XMLHttpRequest);
                    alert("读取文件失败，请检查下列原因。\n1、是否将标注的文件放在该系统下的【needMark】文件夹中。\n2、文件名是否输入错误，需要加扩展名。");
                },

            });
        }
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
            $("#btnBackward").attr("disabled",false);

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
            var colorStringBegin = "<font style=\"font-size:" + originalFontSize + "\" color="+ labelColor +">";
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
            updateSourceText();

            afterHtml1=afterHtml2=afterHtml3=null;
            $("#btnForward").attr("disabled",true);

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
        //将<li class="content"> </li> 替换成空，仅仅保存该标签里面的内容
        function processSourceTextFormat(text){
            text=text.replace(/&lt;/g, "<").replace(/&gt;/g, ">").replace(/&nbsp;/g, " ").replace(/&#39;/g, "\'").replace(/&quot;/g, "\"").replace(/<li class="content">/g, "").replace(/<\/li>/g, "\n").trim();
            return text;
        }

        function setDivMmarkupTextHtml() {
            var text = $("#markupTextSourceTextarea").html().replace(/\n/g, "<p></p>");
            alert(text);
            $("#markupTextHtml").html(text);
            showDivMmarkupTextHtml();
        }

        function showDivMmarkupTextHtml() {
            $("#markupTextHtml").show();
            $("#markupTextSourceTextarea").hide();
            // $("#liHtml").setAttribute('class','active');
            // $("#liTxt").setAttribute('class','');
        }

        function showDivMmarkupTextSourceTextarea() {
            $("#markupTextSourceTextarea").show();
            // $("#liHtml").setAttribute('class','');
            // $("#liTxt").setAttribute('class','active');
        }
        $("#markupTextHtml").hide();

        //选择你要标注的文件
        function selectFile(){
            var fileName = prompt("请输入你需要读取的文件名称","");//将输入的内容赋给变量 fileName ，
            //这里需要注意的是，prompt有两个参数，前面是提示的话，后面是当对话框出来后，在对话框里的默认值
            var localObj = window.location.host;
            var filePath = "http://"+localObj+"/NewSpring/resources/needMark/"+fileName;
            if(fileName != null)//如果返回的有内容
            {
                readFile(filePath);
            }
        }

        //下载标注的文件
        function download(){
            var data = getFileStr("##");
            export_raw("标记文件.txt", data);
        }


        //撤销
        function backward(){
            afterHtml3 = afterHtml2;
            afterHtml2 = afterHtml1;
            afterHtml1 = $("#markupTextHtml").html();
            $("#markupTextHtml").html(beforeHtml1);
            beforeHtml1 = beforeHtml2;
            beforeHtml2 = beforeHtml3;
            beforeHtml3 = null;

            //以下代码修改源文件
            var newSourceMarkupTextHtml = $("#markupTextHtml").html();
            newSourceMarkupTextHtml=processSourceTextFormat(newSourceMarkupTextHtml);
            $("#markupTextSourceTextarea").html(newSourceMarkupTextHtml);

            if(beforeHtml1==null){
                $("#btnBackward").attr("disabled",true);
            }else{
                $("#btnBackward").attr("disabled",false);
            }

            if(afterHtml1==null){
                $("#btnForward").attr("disabled",true);
            }else{
                $("#btnForward").attr("disabled",false);
            }
        }

        //前进
        function forward(){
            beforeHtml3 = beforeHtml2;
            beforeHtml2 = beforeHtml1;
            beforeHtml1 = $("#markupTextHtml").html();
            $("#markupTextHtml").html(afterHtml1);
            afterHtml1 = afterHtml2;
            afterHtml2 = afterHtml3;
            afterHtml3 = null;

            //以下代码为替换编辑框中的html源文件
            var newSourceMarkupTextHtml = $("#markupTextHtml").html();
            newSourceMarkupTextHtml=processSourceTextFormat(newSourceMarkupTextHtml);
            $("#markupTextSourceTextarea").html(newSourceMarkupTextHtml);

            if(beforeHtml1==null){
                $("#btnBackward").attr("disabled",true);
            }else{
                $("#btnBackward").attr("disabled",false);
            }

            if(afterHtml1==null){
                $("#btnForward").attr("disabled",true);
            }else{
                $("#btnForward").attr("disabled",false);
            }
        }
        
        $(function(){
            $("#btn").click(function(){
                $.post("mvc/getPerson",{name:"SSS"},function(data){
                 	  alert(data);
                });
  
            });
        });

    </script>
</head>
<body>
<div id="wrapper">
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="<%=request.getContextPath()%>/index.jsp">系统主界面</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse navbar-ex1-collapse">
          <ul class="nav navbar-nav side-nav">
            <li><a href="#"><i class="fa fa-dashboard"></i> 数据采集</a></li>
            <li><a href="#"><i class="fa fa-edit"></i> 标记系统</a></li>
            <li><a href="#"><i class="fa fa-desktop"></i> 识别系统</a></li>
            <li><a href="#"><i class="fa fa-font"></i> 翻译系统</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-caret-square-o-down"></i> 测试下拉框 <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="#">Dropdown Item</a></li>
                <li><a href="#">Another Item</a></li>
                <li><a href="#">Third Item</a></li>
                <li><a href="#">Last Item</a></li>
              </ul>
            </li>
          </ul>
          <ul class="nav navbar-nav navbar-right navbar-user">
            <li class="dropdown messages-dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-envelope"></i> Messages <span class="badge">7</span> <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li class="dropdown-header">7 New Messages</li>
                <li class="message-preview">
                  <a href="#">
                    <span class="avatar"><img src="http://placehold.it/50x50"></span>
                    <span class="name">高守信</span>
                    <span class="message">Hey there, I wanted to ask you something...</span>
                    <span class="time"><i class="fa fa-clock-o"></i> 4:34 PM</span>
                  </a>
                </li>
                <li class="divider"></li>
                <li class="message-preview">
                  <a href="#">
                    <span class="avatar"><img src="http://placehold.it/50x50"></span>
                    <span class="name">John Smith:</span>
                    <span class="message">Hey there, I wanted to ask you something...</span>
                    <span class="time"><i class="fa fa-clock-o"></i> 4:34 PM</span>
                  </a>
                </li>
                <li class="divider"></li>
                <li class="message-preview">
                  <a href="#">
                    <span class="avatar"><img src="http://placehold.it/50x50"></span>
                    <span class="name">John Smith:</span>
                    <span class="message">Hey there, I wanted to ask you something...</span>
                    <span class="time"><i class="fa fa-clock-o"></i> 4:34 PM</span>
                  </a>
                </li>
                <li class="divider"></li>
                <li><a href="#">View Inbox <span class="badge">7</span></a></li>
              </ul>
            </li>
            <li class="dropdown alerts-dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bell"></i> Alerts <span class="badge">3</span> <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="#">Default <span class="label label-default">Default</span></a></li>
                <li><a href="#">Primary <span class="label label-primary">Primary</span></a></li>
                <li><a href="#">Success <span class="label label-success">Success</span></a></li>
                <li><a href="#">Info <span class="label label-info">Info</span></a></li>
                <li><a href="#">Warning <span class="label label-warning">Warning</span></a></li>
                <li><a href="#">Danger <span class="label label-danger">Danger</span></a></li>
                <li class="divider"></li>
                <li><a href="#">View All</a></li>
              </ul>
            </li>
            <li class="dropdown user-dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> 高守信 <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="#"><i class="fa fa-user"></i> Profile</a></li>
                <li><a href="#"><i class="fa fa-envelope"></i> Inbox <span class="badge">7</span></a></li>
                <li><a href="#"><i class="fa fa-gear"></i> Settings</a></li>
                <li class="divider"></li>
                <li><a href="#"><i class="fa fa-power-off"></i> Log Out</a></li>
              </ul>
            </li>
          </ul>
        </div>
      </nav>
     <div id="page-wrapper">
	<div class="container">
    <div class="row">
        <div class="col-md-1"></div>
        <div id="divCenter" class="col-md-10">
            <div id="topbar" style="background: white">

                <table width="100%" border="0">
                    <tr>
                        <td width="400px">
                            <button id="btnBackward" type="button" class="btn btn-danger" onclick="">选择数据集</button>
                            <button id="btnForward" type="button" class="btn btn-warning" onclick="" >训练</button>
                            <button id="btnSelectFile" type="button" class="btn btn-primary" onclick="">选择文件</button>
                            <button id="btnDownload" type="button" class="btn btn-success" onclick="">下载标注结果</button>
                        	<button id="btn" class="btn btn-default" type="button">测试ajax</button>
                        </td>
                        <td width="80px">
                            标注颜色：
                        </td>
                        <td width="70px">
                            <div class="radio">
                                <label>
                                    <input type="radio" name="optionsColor" id="optionRed" value="red" checked><font color="red">红色</font>
                                </label>
                            </div>
                        </td>
                        <td width="70px">
                            <div class="radio">
                                <label>
                                    <input type="radio" name="optionsColor" id="optionYellow" value="orange"><font color="orange">黄色</font>
                                </label>
                            </div>
                        </td>
                        <td width="70px">
                            <div class="radio">
                                <label>
                                    <input type="radio" name="optionsColor" id="optionBlue" value="blue"><font color="blue">蓝色</font>
                                </label>
                            </div>
                        </td>
                        <td width="70px">
                            <div class="radio">
                                <label>
                                    <input type="radio" name="optionsColor" id="optionGreen" value="green"><font color="green">绿色</font>
                                </label>
                            </div>
                        </td>
                        <td width="70px">
                            <div class="radio">
                                <label>
                                    <input type="radio" name="optionsColor" id="optionPurple" value="purple"><font color="purple">紫色</font>
                                </label>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>

            <div style="width:100%;height: 50px"></div>

            <div  style="font-size:20px" onmouseup="markupElement(event)">
                <ol id="markupTextHtml" style="line-height:50px;">
                </ol>
            </div>

            <button id="btnHtml" type="button" class="btn btn-default" style="display:none;">标注页面</button>
            <button id="btnTxt" type="button" class="btn btn-default" style="display:none;">文本页面</button>
            <button id="btnStartLabel" type="button" class="btn btn-default" style="display:none;">进行标注</button>
        </div>

    </div>
</div>
     </div>
  </div>
</body>
</html>