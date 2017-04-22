/**
 * Created by zpy on 2017/3/30.
 */
"use strict";
require.config({
    "baseUrl": "./",
    "waitSeconds": 0,
    "paths": {
        "can": "lib/can", //桩路径
        "app": "app",
        "bootstrap": "lib/bootstrap",
        "jquery": "lib/jquery",
    },
    "shim": {
        "bootstrap/bootstrap.min": {
            "exports": "bootstrap",
            "deps": [""]
        },
        "angular": {
            "deps": [""]
        }
    }
});

require([
        "app/framework/framework",
        "app/services/httpService"],
    function (app, httpService) {
        var rootScope;
        var state;

        $.ajax({
            type: 'get',
            url: 'account/info',
            async: false,
            success: function (data) {
                if (data.code === "00000") {
                    var injector = angular.bootstrap($("html"), [app.name]);
                    rootScope = injector.get("$rootScope");
                    state = injector.get("state");

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
                    var resources = data.value;
                    resources.unshift({
                        createTime: new Date(),
                        id: 100,
                        parentId: 0,
                        resourceName: "首页",
                        uri: "home"
                    });

                    rootScope.resources = resources;
                } else {
                    window.location.href = "login.html";
                    console.log("fail to get resources");
                }
            },
            error: function (data) {
                window.location.href = "login.html";
                console.log(data);
            }
        });

        function showFirstUri(resourceArray) {
            if (resourceArray[0].uri) {
                return resourceArray[0].uri;
            }
            if (resourceArray[0].children) {
                return showFirstUri(resourceArray[0].children);
            }
            return "";
        }
    })
