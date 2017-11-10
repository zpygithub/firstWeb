define([], function () {
    "use strict";
    var modifyAdminInfoCtrl = ["$rootScope", "$scope", "$compile", function ($rootScope, $scope, $compile) {
        $("#ok123").bind("click", function () {
            console.log(123);
        });
    }];

    var systemModule = angular.module("system.config");
    systemModule.controller("modifyAdminInfoCtrl", modifyAdminInfoCtrl);
    return systemModule;
});
