$(document).ready(function () {
    $("#register").bind("click", function () {
        var account = $("#account").val();
        if ("" == account) {
            alert(getLocaleMsg("accountCanNotBeEmpty"));
            return false;
        }
        var isAccount = /[0-9a-zA-Z]{6,20}/;
        if (!isAccount.test(account)) {
            alert(getLocaleMsg("accountFormatError"));
            return false;
        }

        var nickname = $("#nickname").val();
        if ("" == nickname) {
            alert(getLocaleMsg("nicknameCanNotBeEmpty"));
            return false;
        }
        var isNickname = /.{2,8}/;
        if (!isNickname.test(nickname)) {
            alert(getLocaleMsg("nicknameFormatError"));
            return false;
        }

        var password = $("#password").val();
        if ("" == password) {
            alert(getLocaleMsg("passwordCanNotBeEmpty"));
            return false;
        }
        var isPassword = /[0-9a-zA-Z`~!@#$^&*()-_=+{}\[\];:'\",.<>/?]{6,20}/;
        if (!isPassword.test(password)) {
            alert(getLocaleMsg("passwordFormatError"));
            return false;
        }

        var confirmPassword = $("#confirmPassword").val();
        if ("" == confirmPassword) {
            alert(getLocaleMsg("confirmPasswordCanNotBeEmpty"));
            return false;
        }

        if (password != confirmPassword) {
            alert(getLocaleMsg("passwordMustBeConsistent"));
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
                    alert(getLocaleMsg("registrationSuccess"));
                    window.location.href = "login.html";
                    return;
                } else if (data.code == "00004") {
                    alert(getLocaleMsg("passwordMustBeConsistent！"));
                    return;
                } else if (data.code == "00005") {
                    alert(getLocaleMsg("accountAlreadyExists"));
                    return;
                } else if (data.code == "00006") {
                    alert(getLocaleMsg("nicknameAlreadyExists"));
                    return;
                }
            },
        });
    });

    $("#cancel").bind("click", function () {
        window.location.href = "login.html";
    });
});

function getLocaleMsg(key) {
    return msg[key] == undefined ? key : msg[key];
}
