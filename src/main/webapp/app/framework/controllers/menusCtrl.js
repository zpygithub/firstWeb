define([], function () {
    "use strict";
    var menusCtrl = ["$rootScope", "$scope", "$compile", "storage", function ($rootScope, $scope, $compile, storage) {
        var adminInfo = $rootScope.adminInfo;
        var i18n = $rootScope.i18n;

        $scope.adminInfo = {
            id: "adminInfoId",
            text: adminInfo.account
        };

        $scope.modifyAdminInfo = {
            id: "modifyAdminInfoId",
            title: i18n.admin_info
        };

        $scope.logout = {
            id: "logoutId",
            title: i18n.logout,
            click: function () {
                storage.flush();
                window.location = "./logout";
            }
        };

    }];
    return menusCtrl;
});
