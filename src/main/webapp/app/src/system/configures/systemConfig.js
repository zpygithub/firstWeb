define(["lazy-load"], function (lazyLoad) {
    "use strict";
    var configArr = [
        {
            name: "system",
            url: "/system",
            templateUrl: "app/src/system/views/system.html",
            controller: "system",
            scripts: {
                "controllers": ["app/src/system/controllers/systemCtrl"],
                "js": []
            }
        }, {
            name: "system.admin",
            url: "/admin",
            templateUrl: "app/src/system/views/adminList.html",
            controller: "system.admin",
            scripts: {
                "controllers": ["app/src/system/controllers/adminListCtrl"],
                "js": []
            }
        }
    ];

    var systemModule = angular.module("system.config", ["ui.router"]);
    systemModule = lazyLoad.makeLazy(systemModule);
    systemModule.stateConfig({stateConfig: configArr});
    return systemModule;
});
