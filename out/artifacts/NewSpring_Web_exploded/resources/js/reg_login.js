/**
 * Created by gaoshouxin on 2017/5/7 0007.
 */

function  register() {
    var user_name = $("#user_name").val();
    var password = $("#password").val();
    var repeat_password = $("#repeat_password").val();
    var user_phone = $("#user_phone").val();
    var user_email = $("#user_email").val();
    if( user_name.trim()===""){
        alert("用户名不能为空");
        return ;
    }
    if((password.trim()==="")||(repeat_password.trim()===""))
    {
        alert("密码不能为空");
        return ;
    }

    if( password !== repeat_password){
        alert("两次输入密码不一致！");
        return ;
    }
    $.ajax({
        url : "registerUser",
        type : "post",
        data : {
            user_name : user_name,
            password : password,
            user_phone : user_phone,
            user_email : user_email
        },
        dataType : "text",
        async : false,
        success : function(data) {
            if(data === "用户名已存在"){
                alert(data);
            }else{
                $.session.set("user_id", data);
                window.location.href = "getManageMark";
            }
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert("网络错误，请重试");
        }
    });
}

function reSet() {
    $(".form-control").val("");
}

function reSetLogin() {
    $(".form-control").val("");
}

function login() {
    var user_name = $("#user_name").val().trim();
    var password = $("#password").val().trim();
    if( user_name.trim()===""){
        alert("用户名不能为空");
        return ;
    }
    if(password.trim()==="")
    {
        alert("密码不能为空");
        return ;
    }
    $.ajax({
        url : "loginUser",
        type : "post",
        data : {
            user_name : user_name,
            password : password
        },
        dataType : "text",
        async : false,
        success : function(data) {
            if(data === "用户名或密码错误"){
                alert(data);
            }else{
                $.session.set("user_id", data);
                window.location.href = "getManageMark";
            }
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert("网络错误，请重试");
        }
    });
}