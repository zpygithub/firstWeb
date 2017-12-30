define(["lazy-load"], function (lazyLoad) {
    "use strict";
    var configArr = [
        {
            name: "user",
            url: "/user",
            templateUrl: "app/src/user/views/user.html",
            controller: "user",
            scripts: {
                "controllers": ["app/src/user/controllers/userCtrl"],
                "js": []
            }
        }, {
            name: "user.registerUserList",
            url: "/registerUserList",
            templateUrl: "app/src/user/views/registerUserList.html",
            controller: "user.registerUserList",
            scripts: {
                "controllers": ["app/src/user/controllers/registerUserListCtrl"],
                "js": []
            }
        }
    ];

    var userModule = angular.module("user.config", ["ui.router"]);
    userModule = lazyLoad.makeLazy(userModule);
    userModule.stateConfig({stateConfig: configArr});
    return userModule;
});
