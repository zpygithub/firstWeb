define(["../services/httpService",
        "./controllers/menusCtrl",
        "./configures/frameworkConfig"],
    function (http, menusCtrl, frameworkConfig) {
        "use strict";
        var dependency = [
            frameworkConfig.name,
        ];
        console.log("framework1");

        var framework = angular.module("framework", dependency);

        framework.controller("menusCtrl", menusCtrl);

        framework.service("camel", http);
        console.log("framework2");
        framework.config(["$controllerProvider", "$compileProvider", function ($controllerProvider, $compileProvider) {
            framework.controllerProvider = $controllerProvider;
            framework.compileProvider = $compileProvider;
        }]);

        return framework;
    })
