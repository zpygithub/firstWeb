$(document).ready(function () {
    $("#register").bind("click", function () {
        var account = $("#account").val();
        if ("" == account) {
            alert("账号不能为空");
            return false;
        }
        var isAccount = /[0-9a-zA-Z]{6,20}/;
        if (!isAccount.test(account)) {
            alert("账号格式为6至20位数字或字母");
            return false;
        }

        var nickname = $("#nickname").val();
        if ("" == nickname) {
            alert("昵称不能为空");
            return false;
        }
        var isNickname = /.{2,8}/;
        if (!isNickname.test(nickname)) {
            alert("昵称格式位2至8位任意字符");
            return false;
        }

        var password = $("#password").val();
        if ("" == password) {
            alert("密码不能为空");
            return false;
        }
        var isPassword = /[0-9a-zA-Z`~!@#$^&*()-_=+{}\[\];:'\",.<>/?]{6,20}/;
        if (!isPassword.test(password)) {
            alert("密码格式为6至20位数字、字母或特殊符号");
            return false;
        }

        var confirmPassword = $("#confirmPassword").val();
        if ("" == confirmPassword) {
            alert("确认密码不能为空");
            return false;
        }

        if (password != confirmPassword) {
            alert("密码必须一致");
            return false;
        }

        var data = {
            "account": account,
            "nickname": nickname,
            "passwd": password,
            "confirmPasswd": confirmPassword,
        };
        $.ajax({
            type: 'put',
            url: 'register/mainRegister',
            dataType: 'json',
            async: false,
            data: data,
            success: function (data) {
                if (data.code == "00000") {
                    alert("注册成功！")
                    window.location.href = "login.html";
                    return;
                } else if (data.code == "00003") {
                    alert("全都需要填！")
                    return;
                } else if (data.code == "00004") {
                    alert("密码必须一致！")
                    return;
                } else if (data.code == "00005") {
                    alert("账号已存在！")
                    return;
                } else if (data.code == "00006") {
                    alert("昵称已存在！")
                    return;
                }
            },
        });
    });

    $("#cancel").bind("click", function () {
        window.location.href = "login.html";
    });
});