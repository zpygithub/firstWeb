"use strict";
require.config({
    "baseUrl": "./",
    "waitSeconds": 0,
    "paths": {
        "app": "app",
        "angular": "lib/angular/angular",
        "ui-router": "lib/angular/angular-ui-router",
        "file-input": "lib/bootstrap/bootstrap_fileinput4.4.4",
        "bootstrap": "lib/bootstrap/bootstrap3.3.7",
        "jquery": "lib/jquery/jquery-3.2.1",
        "i18n": "i18n/zh"
    },
    "shim": {
        "angular": {
            "deps": ["jquery"],
            "exports": "angular"
        },
        "bootstrap": {
            "deps": ["jquery"],
            "exports": "bootstrap"
        },
        "ui-router": {
            "deps": ["angular"]
        },
        "jquery": {
            "exports": "$"
        }
    }
});

require(["app/framework/framework", "i18n/keyId", "angular", "jquery"],
    function (framework, i18n, angular, $) {
        var injector = angular.bootstrap($("html"), [framework.name]);
        var rootScope = injector.get("$rootScope");
        var state = injector.get("state");

        $.ajax({
            type: 'get',
            url: 'account/getAdministratorById',
            async: false,
            success: function (data) {
                if (data.code === "00000") {
                    rootScope.i18n = i18n;
                    rootScope.account = data.value;
                } else {
                    window.location.href = "login.html";
                }
            },
            error: function () {
                window.location.href = "login.html";
            }
        });

        $.ajax({
            type: 'get',
            url: 'system/getMainMenus',
            async: false,
            success: function (data) {
                if (data.code === "00000") {
                    var mainMenus = data.value;
                    mainMenus.unshift({
                        createTime: new Date(),
                        id: 100,
                        parentId: 0,
                        resourceName: "首页",
                        uri: "home"
                    });
                    rootScope.mainMenus = mainMenus;
                } else {
                    window.location.href = "login.html";
                }
            },
            error: function () {
                window.location.href = "login.html";
            }
        });

        function showFirstUri(menuArray) {
            if (menuArray[0].uri) {
                return menuArray[0].uri;
            }
            if (menuArray[0].children) {
                return showFirstUri(menuArray[0].children);
            }
            return "";
        }
    })
