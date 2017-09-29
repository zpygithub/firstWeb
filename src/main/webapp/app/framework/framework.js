define(["angular",
        "ui-router",
        "app/services/httpService",
        "app/services/maskService",
        "app/framework/controllers/menusCtrl",
        "app/framework/controllers/serviceCtrl",
        "app/framework/configures/frameworkConfig"],
    function (angular, router, http, mask, menusCtrl, serviceCtrl, frameworkConfig) {
        "use strict";
        var dependency = [
            "ng",  
            "ui.router",
            frameworkConfig.name,
        ];
        console.log("framework1");

        var framework = angular.module("framework", dependency);

        framework.service("camel", http);
        framework.service("mask", mask);
        framework.controller("menusCtrl", menusCtrl);
        framework.controller("serviceCtrl", serviceCtrl);

//         framework.config(["$controllerProvider", "$compileProvider", function ($controllerProvider, $compileProvider) {
//             framework.controllerProvider = $controllerProvider;
//             framework.compileProvider = $compileProvider;
//         }]);

        return framework;
    })
