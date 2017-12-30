define([], function () {
    "use strict";
    var userCtrl = ["$rootScope", "$scope", "$state", function ($rootScope, $scope, $state) {

    }];
    var userModule = angular.module("user.config");
    userModule.controller("user", userCtrl);
    return userModule;
});
