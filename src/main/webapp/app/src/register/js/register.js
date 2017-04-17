$(document).ready(function () {
    $("#register").bind("click", function () {
        var account = $("#account").val();
        if ("" == account) {
            alert("账号不能为空");
            return false;
        }

        var nickname = $("#nickname").val();
        if ("" == nickname) {
            alert("昵称不能为空");
            return false;
        }

        var password = $("#password").val();
        if ("" == password) {
            alert("密码不能为空");
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

        var errorCode = "";

        var data = {
            "account": account,
            "nickname": nickname,
            "passwd": password,
            "confirmPassword": confirmPassword,
        };
        $.ajax({
            type: 'post',
            url: 'register/mainRegister',
            dataType: 'json',
            async: false,
            data: data,
            success: function (data, textStatus) {
                if (textStatus == "success") {
                    errorCode = data.code;
                }
                if (errorCode == "00000") {
                    window.location.href = "login.html";
                    return;
                } else if (errorCode == "00015") {
                    generateErrorDiv("IncorrectCaptcha");
                    $("#j_captcha_parameter").focus();
                } else if (errorCode == "00016") {
                    generateErrorDiv("UserNotExists");
                    $("#account").focus();
                } else if (errorCode == "00017") {
                    generateErrorDiv("AccountOrPasswordError");
                    $("#password").focus();
                } else if (errorCode == "00062") {
                    generateErrorDiv("UserLock");
                    $("#account").focus();
                }
            },
            error: function (textStatus, xhr) {
                var errorCode = xhr.responseText;
                if (errorCode == "00000") {
                    window.location.href = "index.html";
                    return;
                } else if (errorCode == "4") {
                    generateErrorDiv("IncorrectCaptcha");
                    $("#j_captcha_parameter").focus();
                } else if (errorCode == "2") {
                    generateErrorDiv("UserNotExists");
                    $("#account").focus();
                } else if (errorCode == "3") {
                    generateErrorDiv("AccountOrPasswordError");
                    $("#password").focus();
                } else if (errorCode == "1") {
                    generateErrorDiv("UserLock");
                    $("#account").focus();
                }
            },
        });
    });
});