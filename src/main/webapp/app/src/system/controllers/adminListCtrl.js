define([], function () {
    "use strict";
    var adminListCtrl = ["$rootScope", "$scope", "$compile", function ($rootScope, $scope, $compile) {
        $("#ok123").bind("click", function () {
            console.log(123);
        });
    }];

    var systemModule = angular.module("system.config");
    systemModule.controller("system.admin", adminListCtrl);
    return systemModule;
});
