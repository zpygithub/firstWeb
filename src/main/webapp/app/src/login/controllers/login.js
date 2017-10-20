$(document).ready(function () {
    msieversion();
    $("#register").bind("click", function () {
        window.location.href = "register.html";
    });

    $("#login").bind("click", function () {
        var account = $("#account").val();
        if ("" == account) {
            generateErrorDiv("requiredUsername");
            $("#status").attr("innerHTML", "requiredUsername");
            $("#account").focus();
            return false;
        }

        var password = $("#password").val();
        if ("" == password) {
            generateErrorDiv("requiredPassword");
            $("#status").attr("innerHTML", "requiredPassword");
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
                	generateErrorDiv("accountNotExist");
                    $("#account").focus();
                } else if (data.code == "00017") {
                    generateErrorDiv("accountOrPasswordError");
                    $("#password").focus();
                } else if (data.code == "00011") {
                	generateErrorDiv("accountLocked");
                    $("#account").focus();
                } else {
                	generateErrorDiv("accountOrPasswordError");
                    return;
                }
            }
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
    var strDiv = "<div id=\"status\" class=\"errors\">" + key + "</div>";
    $("#errorId").attr("innerHTML", strDiv);
    flushErrorBox();
}
