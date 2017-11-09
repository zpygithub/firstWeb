"use strict";
require.config({
    "baseUrl": "./",
    "waitSeconds": 0,
    "paths": {
        "app": "app",
        "jquery": "lib/js/jquery/jquery-3.2.1",
        "angular": "lib/js/angular/angular",
        "ui-router": "lib/js/angular/angular-ui-router",
        "file-input": "lib/js/bootstrap/bootstrap_fileinput4.4.4",
        "bootstrap": "lib/js/bootstrap/bootstrap3.3.7",
        "lobibox": "lib/js/lobibox/lobibox",
        "lazy-load": "lib/js/lazyLoad/lazyLoad",
        "layer": "lib/js/layer/layer",
        "i18n": "i18n/zh"
    },
    "shim": {
        "jquery": {
            "exports": "$"
        },
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
        "lobibox": {
            "deps": ["jquery"],
            "exports": "lobibox"
        }
    }
});

require(["app/framework/framework", "i18n/keyId", "angular", "jquery"],
    function (framework, i18n, angular, $) {
        var injector = angular.bootstrap($("html"), [framework.name]);
        var rootScope = injector.get("$rootScope");
        var state = injector.get("$state");

        $.ajax({
            type: 'get',
            url: 'account/getAdministratorById',
            async: false,
            success: function (data) {
                if (data.code === "00000") {
                    rootScope.i18n = i18n;
                    rootScope.personalInfo = data.value;
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
                    rootScope.mainMenus = data.value;
                } else {
                    window.location.href = "login.html";
                }
            },
            error: function () {
                window.location.href = "login.html";
            }
        });
    })
