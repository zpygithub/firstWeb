"use strict";
require.config({
    "baseUrl": "./",
    "waitSeconds": 0,
    "paths": {
        "can": "lib/can", //桩路径
        "app": "app",
        "angular": "lib/angular/angular",
        "ui-router": "lib/angular/angular-ui-router",
        "file-input": "lib/bootstrap/bootstrap_fileinput4.4.4",
        "bootstrap": "lib/bootstrap/bootstrap3.3.7",
        "jquery": "lib/jquery/jquery-3.1.1.min",
        "i18n": "i18n/zh"
    },
    "shim": {
        "angular": {
            "deps": ["jquery"],
        },
        "bootstrap": {
            "deps": ["jquery"],
        },
        "ui-router": {
            "deps": ["angular"]
        },
    }
});

require(["app/framework/framework", "i18n/keyId", "angular"],
    function (framework, i18n, angular) {
        var rootScope;
        var state;
        $.ajax({
            type: 'get',
            url: 'account/getAdministratorById',
            async: false,
            success: function (data) {
                console.log(data);
                if (data.code === "00000") {
                    var injector = angular.bootstrap($("html"), [framework.name]);
                    rootScope = injector.get("$rootScope");
                    // state = injector.get("state");
                    rootScope.i18n = i18n;
                    rootScope.account = data.value;
                } else {
                    // window.location.href = "login.html";
                }
            },
            error: function (data) {
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
                    console.log(mainMenus);
                    mainMenus.unshift({
                        createTime: new Date(),
                        id: 100,
                        parentId: 0,
                        resourceName: "首页",
                        uri: "home"
                    });

                    rootScope.mainMenus = mainMenus;
                } else {
                    // window.location.href = "login.html";
                    // alert("fail to get mainMenus");
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
