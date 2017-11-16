$(document).ready(function () {
    msieversion();
    $("#register").bind("click", function () {
        window.location.href = "register.html";
    });

    $("#login").bind("click", function () {
        var account = $("#account").val();
        if ("" == account) {
            Lobibox.notify("error", {msg: getLocaleMsg("requiredUsername")});
            $("#account").focus();
            return false;
        }

        var password = $("#password").val();
        if ("" == password) {
            Lobibox.notify("error", {msg: getLocaleMsg("requiredPassword")});
            $("#password").focus();
            return false;
        }

        var data = {
            "account": account,
            "passwd": password,
        };
        $.ajax({
            type: 'post',
            url: 'login/mainLogin',
            dataType: 'json',
            async: false,
            data: data
        }).done(function (data) {
            if (data.code == "00000") {
                window.location.href = "index.html";
            } else if (data.code == "00010") {
                Lobibox.notify("error", {msg: getLocaleMsg("accountNotExist")});
                $("#account").focus();
            } else if (data.code == "00017") {
                Lobibox.notify("error", {msg: getLocaleMsg("accountOrPasswordError")});
                $("#password").focus();
            } else if (data.code == "00011") {
                Lobibox.notify("error", {msg: getLocaleMsg("accountLocked")});
                $("#account").focus();
            } else {
                Lobibox.notify("error", {msg: getLocaleMsg("accountOrPasswordError")});
            }
        });
    });
    $("input:visible:enabled:first").focus();
});

function msieversion() {
    var ua = window.navigator.userAgent;
    var msie = ua.indexOf("MSIE ");
    if (msie > 0) {
        var version = parseInt(ua.substring(msie + 5, ua.indexOf(".", msie)));
        if (version < 8 && (ua.indexOf("Trident") < 0)) {
            return false;
        } else {
            return true;
        }
    } else {
        return true;
    }
}

function getLocaleMsg(key) {
    return msg[key] == undefined ? key : msg[key];
}
