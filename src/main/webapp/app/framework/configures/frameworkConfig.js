define(["angular", "ui-router"], function (angular, router) {
    "use strict";
    var serviceConfigs = ["$stateProvider", "$urlRouterProvider", "$controllerProvider",
        function ($stateProvider, $urlRouterProvider, $controllerProvider) {
            $urlRouterProvider.otherwise("/home");
            $stateProvider.state("home", {
                url: "/home",
                templateUrl: "app/framework/views/home.html",
                controller: "home.ctrl",
                resolve: {
                    deps: function ($q, $rootScope) {
                        var deferred = $q.defer();
                        var dependencies = ["app/framework/controllers/homeCtrl"];
                        require(dependencies, function (ctrl) {
                            $rootScope.$apply(function () {
                                $controllerProvider.register("home.ctrl", ctrl);
                                deferred.resolve();
                            });
                        });
                        return deferred.promise;
                    }
                }
            });
        }];

    var frameworkConfig = angular.module("frm", ["ui.router"]);
    frameworkConfig.config(serviceConfigs);
    return frameworkConfig;
});
