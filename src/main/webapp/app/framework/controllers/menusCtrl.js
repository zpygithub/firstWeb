define([], function () {
    "use strict";
    var menusCtrl = ["$rootScope", "$scope", "$compile", "storage", function ($rootScope, $scope, $compile, storage) {
        var adminInfo = $rootScope.adminInfo;
        var i18n = $rootScope.i18n;

        $scope.adminInfo = {
            id: "adminInfoId",
            label: adminInfo.account
        };

        $scope.modifyAdminInfo = {
            id: "modifyAdminInfoId",
            label: i18n.admin_info
        };

        $scope.logout = {
            id: "logoutId",
            label: i18n.logout,
            click: function () {
                storage.flush();
                window.location = "./logout";
            }
        };

    }];
    return menusCtrl;
});
