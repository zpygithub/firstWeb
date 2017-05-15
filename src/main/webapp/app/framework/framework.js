define(["app/framework/controllers/menusCtrl", "app/framework/configures/frameworkConfig"], function (menusCtrl, frameworkConfig) {
    "use strict";
    var dependency = [
        "frameworkConfig.name",
    ];
    console.log(1);

    var framework = angular.module("framework", dependency);

    framework.controller("menusCtrl", menusCtrl);
    console.log(1);
    framework.config(["$controllerProvider", "$compileProvider", function ($controllerProvider, $compileProvider) {
        framework.controllerProvider = $controllerProvider;
        framework.compileProvider = $compileProvider;
    }]);

    return framework;
})
