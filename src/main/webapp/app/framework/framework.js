define(["./controllers/menusCtrl", "./configures/frameworkConfig"], function (menusCtrl, frameworkConfig) {
    "use strict";
    var dependency = [
        "frameworkConfig.name",
    ];
    var framework = angular.module("framework", dependency);

    framework.controller("menusCtrl", menusCtrl);
    console.log(1);
    framework.config(["$controllerProvider", "$compileProvider", function ($controllerProvider, $compileProvider) {
        framework.controllerProvider = $controllerProvider;
        framework.compileProvider = $compileProvider;
    }]);

    return framework;
})
