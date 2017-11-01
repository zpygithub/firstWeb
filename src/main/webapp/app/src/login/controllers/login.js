$(document).ready(function () {
    msieversion();
    $("#register").bind("click", function () {
        window.location.href = "register.html";
    });

    $("#login").bind("click", function () {
        var account = $("#account").val();
        if ("" == account) {
            alert(getLocaleMsg("requiredUsername"));
            $("#account").focus();
            return false;
        }

        var password = $("#password").val();
        if ("" == password) {
            alert(getLocaleMsg("requiredPassword"));
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
            data: data,
            success: function (data) {
                if (data.code == "00000") {
                    window.location.href = "index.html";
                    return;
                } else if (data.code == "00010") {
                    alert(getLocaleMsg("accountNotExist"));
                    $("#account").focus();
                } else if (data.code == "00017") {
                    alert(getLocaleMsg("accountOrPasswordError"));
                    $("#password").focus();
                } else if (data.code == "00011") {
                    alert(getLocaleMsg("accountLocked"));
                    $("#account").focus();
                } else {
                    alert(getLocaleMsg("accountOrPasswordError"));
                    return;
                }
            }
        });
    });
    $("input:visible:enabled:first").focus();
});

function msieversion() {
    var ua = window.navigator.userAgent;
    console.log(ua);
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
