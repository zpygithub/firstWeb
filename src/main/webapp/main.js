"use strict";
require.config({
    "baseUrl": "./",
    "waitSeconds": 0,
    "paths": {
        "app": "app",
        "jquery": "lib/js/jquery/jquery-1.12.4",
        "angular": "lib/js/angular/angular",
        "ui-router": "lib/js/angular/angular-ui-router",
        "bootstrap": "lib/js/bootstrap/bootstrap3.3.7",
        "bootstrap-table": "lib/js/bootstrap/bootstrap-table1.11.1",
        "bootstrap-table-cn": "lib/js/bootstrap/bootstrap-table-zh-CN",
        "bootstrap-datetimepicker": "lib/js/bootstrap/bootstrap-datetimepicker",
        "bootstrap-datetimepicker-cn": "lib/js/bootstrap/bootstrap-datetimepicker.zh-CN",
        "lobibox": "lib/js/lobibox/lobibox",
        "lazy-load": "lib/js/lazyLoad/lazyLoad",
        "i18n": "i18n/zh"
    },
    "shim": {
        "jquery": {
            "exports": "$"
        },
        "angular": {
            "exports": "angular"
        },
        "bootstrap": {
            "deps": ["jquery"],
            "exports": "bootstrap"
        },
        "ui-router": {
            "deps": ["angular"]
        },
        "lobibox": {
            "deps": ["jquery"]
        },
        "bootstrap-table": {
            "deps": ["jquery", "bootstrap"],
            "exports": "bootstrap-table"
        },
        "bootstrap-table-cn": {
            "deps": ["jquery", "bootstrap-table"],
            "exports": "bootstrap-table-cn"
        },
        "bootstrap-datetimepicker": {
            "deps": ["jquery", "bootstrap"],
            "exports": "bootstrap-datetimepicker"
        },
        "bootstrap-datetimepicker-cn": {
            "deps": ["jquery", "bootstrap-datetimepicker"],
            "exports": "bootstrap-datetimepicker-cn"
        }
    }
});

require(["app/framework/framework", "i18n/keyId", "angular"],
    function (framework, i18n, angular) {
        var injector = angular.bootstrap($("html"), [framework.name]);
        var rootScope = injector.get("$rootScope");
        var state = injector.get("$state");

        $.ajax({
            type: 'get',
            url: 'system/getAdministrator',
            async: false
        }).done(function (data) {
            if (data.code === "00000") {
                rootScope.i18n = i18n;
                rootScope.adminInfo = data.value;
            } else {
                window.location.href = "login.html";
            }
        }).fail(function () {
            var index = window.location.href.indexOf("#");
            if (index == -1) {
                window.location.href = "login.html";
            } else {
                window.location.href = "login.html?service=" + window.location.href.substring(index);
            }
        });

        $.ajax({
            type: 'get',
            url: 'system/getMainMenus',
            async: false
        }).done(function (data) {
            if (data.code === "00000") {
                rootScope.mainMenus = data.value;
            } else {
                window.location.href = "login.html";
            }
        }).fail(function () {
            window.location.href = "login.html";
        });
    });
