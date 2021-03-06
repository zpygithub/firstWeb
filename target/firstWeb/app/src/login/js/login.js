$(document).ready(function () {
    msieversion();
    $("#refresh").attr("innerHTML", getLocaleMsg("refresh"));
    $("#refresh").attr("title", getLocaleMsg("refreshTip"));
    $("#reset").attr("value", getLocaleMsg("cancelBtn"));
    $("#refresh").bind("click", function () {
        getCpatchaCode();
    });

    $("#register").bind("click", function () {
        window.location.href = "register.html";
    });

    $("#login").bind("click", function () {
        var account = $("#account").val();
        if ("" == account) {
            generateErrorDiv("requiredUsername");
            $("#status").attr("innerHTML", getLocaleMsg("requiredUsername"));
            $("#account").focus();
            return false;
        }

        var password = $("#password").val();
        if ("" == password) {
            generateErrorDiv("requiredPassword");
            $("#status").attr("innerHTML", getLocaleMsg("requiredPassword"));
            $("#password").focus();
            return false;
        }

        var errorCode = "";

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
            success: function (data, textStatus) {
                if (textStatus == "success") {
                    errorCode = data.code;
                }
                if (errorCode == "00000") {
                    window.location.href = "index.html";
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

    $("input:visible:enabled:first").focus();
    flushErrorBox();

    $("#userType").change(function () {
        var selectedValue = $(this).val();
        sessionStorage.setItem('loginScene', selectedValue);
    });

    var loginScene = sessionStorage.getItem('loginScene');
    if (loginScene) {
        $("#userType").val(loginScene);
    }
});

function getCpatchaCode() {
    $("#captchaImg").attr("src", $("#captchaImgHidden").attr("value") + "?now=" + new Date());
}

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

function flushErrorBox() {
    var message = $("status").attr("innerHTML");
    if ("undefined" != typeof message) {
        var newMessage = message.replace(/<[^>]*>/g, "");
        $("status").attr("innerHTML", newMessage);
    }
    if ("undefined" != typeof newMessage) {
        if (newMessage.indexOf("~") != -1) {
            var messageArray = newMessage.split("~");
            $("status").attr("innerHTML", messageArray[0]);
            $("account").attr("value", messageArray[1]);
            $("password").focus();
        }
    }
}

function generateErrorDiv(key) {
    val = getLocaleMsg(key);
    var strDiv = "<div id=\"status\" class=\"errors\">" + val + "</div>";
    $("#errorId").attr("innerHTML", strDiv);
    flushErrorBox();
}

function getLocaleMsg(key) {
    // return login_msg[key] == undefined ? key : login_msg[key];
    return key;
}
