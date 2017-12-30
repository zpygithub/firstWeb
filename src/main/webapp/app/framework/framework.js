define(["angular",
        "ui-router",
        "app/services/httpService",
        "app/services/maskService",
        "app/services/cookieService",
        "app/framework/configures/frameworkConfig",
        "app/src/user/configures/userConfig",
        "app/src/system/configures/systemConfig",
        "app/framework/controllers/homeCtrl",
        "app/framework/controllers/menusCtrl",
        "app/framework/controllers/serviceCtrl"],
    function (angular, router, http, mask, storage, frameworkConfig, userConfig, systemConfig, homeCtrl, menusCtrl, serviceCtrl) {
        "use strict";
        var dependency = [
            "ng",
            "ui.router",
            frameworkConfig.name,
            userConfig.name,
            systemConfig.name
        ];

        var framework = angular.module("framework", dependency);

        framework.controller("menusCtrl", menusCtrl);
        framework.controller("serviceCtrl", serviceCtrl);

        framework.service("camel", http);
        framework.service("mask", mask);
        framework.service("storage", storage);

        framework.config(["$controllerProvider", "$compileProvider", '$locationProvider', function ($controllerProvider, $compileProvider, $locationProvider) {
            framework.controllerProvider = $controllerProvider;
            framework.compileProvider = $compileProvider;
            $locationProvider.hashPrefix('');
        }]);

        return framework;
    });
