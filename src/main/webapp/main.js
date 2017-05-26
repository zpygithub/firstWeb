"use strict";
require.config({
    "baseUrl": "./",
    "waitSeconds": 0,
    "paths": {
        "can": "lib/can", //桩路径
        "app": "app",
        "ui-router": "lib/angular/angular-ui-router",
        "bootstrap": "lib/bootstrap/bootstrap3.3.7.min",
        "jquery": "lib/jquery/jquery-3.1.1.min",
        "angular": "lib/angular/angular",
        "i18n": "i18n"
    },
    "shim": {
        "bootstrap": {
            "deps": ["jquery"],
            // "exports": "bootstrap"
        },
        "angular": {
            "deps": ["angular"],
            // "exports": "angular"
        },
        "ui-router": {
            "deps": ["angular"]
        },
    }
});

require(["app/framework/framework",
        "i18n/keyId",
        "app/services/httpService"],
    function (framework, i18n, httpService) {
        var rootScope;
        var state;
        console.log("main.js进来了");
        $.ajax({
            type: 'get',
            url: 'account/info',
            async: false,
            success: function (data) {
                if (data.code === "00000") {
                    var injector = angular.bootstrap($("html"), [framework.name]);
                    rootScope = injector.get("$rootScope");
                    state = injector.get("state");
                    rootScope.i18n = i18n;
                    rootScope.account = data.value;
                } else {
                    window.location.href = "login.html";
                }
            },
            error: function (data) {
                console.log(data);
                window.location.href = "login.html";
            }
        });

        $.ajax({
            type: 'get',
            url: 'system/resource/menu/0',
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
                    console.log("fail to get mainMenus");
                }
            },
            error: function (data) {
                window.location.href = "login.html";
                console.log(data);
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