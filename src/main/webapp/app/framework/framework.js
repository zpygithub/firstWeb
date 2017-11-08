define(["angular",
        "ui-router",
        "app/services/httpService",
        "app/services/maskService",
        "app/services/cookieService",
        "app/framework/configures/frameworkConfig",
        "app/framework/controllers/homeCtrl",
        "app/framework/controllers/menusCtrl",
        "app/framework/controllers/serviceCtrl"],
    function (angular, router, http, mask, storage, frameworkConfig, homeCtrl, menusCtrl, serviceCtrl) {
        "use strict";
        var dependency = [
            "ng",
            "ui.router",
            frameworkConfig.name
        ];

        var framework = angular.module("framework", dependency);

        framework.controller("menusCtrl", menusCtrl);
        framework.controller("serviceCtrl", serviceCtrl);

        framework.service("camel", http);
        framework.service("mask", mask);
        framework.service("storage", storage);

        framework.config(["$controllerProvider", "$compileProvider", function ($controllerProvider, $compileProvider) {
            framework.controllerProvider = $controllerProvider;
            framework.compileProvider = $compileProvider;
        }]);

        return framework;
    })
