$(document).ready(function () {
    $("#register").bind("click", function () {
        var account = $("#account").val();
        if ("" == account) {
            Lobibox.notify("error", {msg: getLocaleMsg("accountCanNotBeEmpty")});
            return false;
        }
        var isAccount = /[0-9a-zA-Z]{6,20}/;
        if (!isAccount.test(account)) {
            Lobibox.notify("error", {msg: getLocaleMsg("accountFormatError")});
            return false;
        }

        var nickname = $("#nickname").val();
        if ("" == nickname) {
            Lobibox.notify("error", {msg: getLocaleMsg("nicknameCanNotBeEmpty")});
            return false;
        }
        var isNickname = /.{2,8}/;
        if (!isNickname.test(nickname)) {
            Lobibox.notify("error", {msg: getLocaleMsg("nicknameFormatError")});
            return false;
        }

        var password = $("#password").val();
        if ("" == password) {
            Lobibox.notify("error", {msg: getLocaleMsg("passwordCanNotBeEmpty")});
            return false;
        }
        var isPassword = /[0-9a-zA-Z`~!@#$^&*()-_=+{}\[\];:'\",.<>/?]{6,20}/;
        if (!isPassword.test(password)) {
            Lobibox.notify("error", {msg: getLocaleMsg("passwordFormatError")});
            return false;
        }

        var confirmPassword = $("#confirmPassword").val();
        if ("" == confirmPassword) {
            Lobibox.notify("error", {msg: getLocaleMsg("confirmPasswordCanNotBeEmpty")});
            return false;
        }

        if (password != confirmPassword) {
            Lobibox.notify("error", {msg: getLocaleMsg("passwordMustBeConsistent")});
            return false;
        }

        var data = {
            "account": account,
            "nickname": nickname,
            "passwd": password,
            "confirmPasswd": confirmPassword,
        };
        $.ajax({
            type: "put",
            url: "register/mainRegister",
            dataType: "json",
            async: false,
            data: data
        }).done(function (data) {
            if (data.code == "00000") {
                Lobibox.notify("success", {msg: getLocaleMsg("registrationSuccess")});
                window.location.href = "login.html";
            } else if (data.code == "00004") {
                Lobibox.notify("error", {msg: getLocaleMsg("passwordMustBeConsistent")});
            } else if (data.code == "00005") {
                Lobibox.notify("warning", {msg: getLocaleMsg("accountAlreadyExists")});
            } else if (data.code == "00006") {
                Lobibox.notify("warning", {msg: getLocaleMsg("nicknameAlreadyExists")});
            }
        });
    });

    $("#cancel").bind("click", function () {
        window.location.href = "login.html";
    });
});

function getLocaleMsg(key) {
    return msg[key] == undefined ? key : msg[key];
}
