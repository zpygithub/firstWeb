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
                    alert("账号不存在");
                    $("#account").focus();
                } else if (data.code == "00017") {
                    generateErrorDiv("AccountOrPasswordError");
                    $("#password").focus();
                } else if (data.code == "00011") {
                    alert("账号已被锁定");
                    $("#account").focus();
                } else {
                    alert("AccountOrPasswordError");
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
    val = getLocaleMsg(key);
    var strDiv = "<div id=\"status\" class=\"errors\">" + val + "</div>";
    $("#errorId").attr("innerHTML", strDiv);
    flushErrorBox();
}

function getLocaleMsg(key) {
    // return login_msg[key] == undefined ? key : login_msg[key];
    return key;
}


// $(function () {
// //0.初始化fileinput
//     var oFileInput = new FileInput();
//     oFileInput.Init("txt_file", "/api/OrderApi/ImportOrder");
// });
// //初始化fileinput
// var FileInput = function () {
//     var oFile = new Object();
// //初始化fileinput控件（第一次初始化）
//     oFile.Init = function (ctrlName, uploadUrl) {
//         var control = $('#' + ctrlName);
// //初始化上传控件的样式
//         control.fileinput({
//             language: 'zh', //设置语言
//             uploadUrl: uploadUrl, //上传的地址
//             allowedFileExtensions: ['jpg', 'gif', 'png'],//接收的文件后缀
//             showUpload: true, //是否显示上传按钮
//             showCaption: false,//是否显示标题
//             browseClass: "btn btn-primary", //按钮样式
// //dropZoneEnabled: false,//是否显示拖拽区域
// //minImageWidth: 50, //图片的最小宽度
// //minImageHeight: 50,//图片的最小高度
// //maxImageWidth: 1000,//图片的最大宽度
// //maxImageHeight: 1000,//图片的最大高度
// //maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
// //minFileCount: 0,
//             maxFileCount: 10, //表示允许同时上传的最大文件个数
//             enctype: 'multipart/form-data',
//             validateInitialCount: true,
//             previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
//             msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
//         });
// //导入文件上传完成之后的事件
//         $("#txt_file").on("fileuploaded", function (event, data, previewId, index) {
//             $("#myModal").modal("hide");
//             var data = data.response.lstOrderImport;
//             if (data == undefined) {
//                 toastr.error('文件格式类型不正确');
//                 return;
//             }
// //1.初始化表格
//             var oTable = new TableInit();
//             oTable.Init(data);
//             $("#div_startimport").show();
//         });
//     }
//     return oFile;
// };