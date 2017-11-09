define(["lazy-load"], function (lazyLoad) {
    "use strict";
    var configArr = [
        {
            name: "systemMgr",
            url: "/systemMgr",
            templateUrl: "src/app/business/system/views/system.html",
            controller: "system",
            scripts: {
                'controllers': ['src/app/business/system/controllers/systemCtrl'],
                "js": []
            }
        }, {
            name: "systemMgr.admin",
            url: "/admin",
            templateUrl: "src/app/business/system/views/manageAdmin.html",
            controller: "systemMgr.admin",
            scripts: {
                'controllers': ['src/app/business/system/controllers/manageAdminCtrl'],
                "js": []
            }
        }
    ];

    var systemModule = angular.module("system.config", ["ui.router"]);
    systemModule = lazyLoad.makeLazy(systemModule);
    systemModule.stateConfig({stateConfig: configArr});
    return systemModule;
});
