define([], function () {
    "use strict";
    var systemCtrl = ["$rootScope", "$scope", "$state", function ($rootScope, $scope, $state) {

    }];
    var systemModule = angular.module("system.config");
    systemModule.controller("system", systemCtrl);
    return systemModule;
});
