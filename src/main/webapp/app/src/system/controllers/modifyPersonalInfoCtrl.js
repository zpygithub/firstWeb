define([], function () {
    "use strict";
    var modifyPersonalInfoCtrl = ["$rootScope", "$scope", "$compile", function ($rootScope, $scope, $compile) {
        $("#ok123").bind("click", function () {
            console.log(123);
        });
    }];

    var systemModule = angular.module("system.config");
    systemModule.controller("modifyPersonalInfoCtrl", modifyPersonalInfoCtrl);
    return systemModule;
});
