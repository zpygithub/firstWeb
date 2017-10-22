$(document).ready(function () {
    msieversion();
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

function flushErrorBox() {
    var message = $("#status").html();
    if ("undefined" != typeof message) {
        $("#status").val() = "";
    }
    // if ("undefined" != typeof newMessage) {
    //     if (newMessage.indexOf("~") != -1) {
    //         var messageArray = newMessage.split("~");
    //         $("#status").append(messageArray[0]);
    //         $("#account").attr("value", messageArray[1]);
    //         $("#password").focus();
    //     }
    // }
}

function generateErrorDiv(key) {
    flushErrorBox();
    var strDiv = "<div id=\"status\" class=\"errors\">" + getLocaleMsg(key) + "</div>";
    $("#errorId").append(strDiv);

}

function getLocaleMsg(key)
{
    return msg[key] == undefined ? key : msg[key];
}
