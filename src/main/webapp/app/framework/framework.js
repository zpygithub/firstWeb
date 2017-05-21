define(["app/framework/controllers/menusCtrl"], function (menusCtrl) {
    "use strict";
    var dependency = [
        "frameworkConfig.name",
    ];
    console.log("framework1");

    var framework = angular.module("framework", dependency);

    framework.controller("menusCtrl", menusCtrl);
    console.log("framework2");
    framework.config(["$controllerProvider", "$compileProvider", function ($controllerProvider, $compileProvider) {
        framework.controllerProvider = $controllerProvider;
        framework.compileProvider = $compileProvider;
    }]);

    return framework;
})
