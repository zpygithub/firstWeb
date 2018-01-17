define(["bootstrap"], function () {
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
            label: i18n.admin_info,
            click: function () {
                $("#adminInfoModal").data("id", $rootScope.adminInfo.id);
                $("#adminInfoModal").modal({
                    remote: "app/src/system/views/modifyAdminInfo.html",
                    backdrop: "static"
                });
            }
        };

        $scope.logout = {
            id: "logoutId",
            label: i18n.logout,
            click: function () {
                storage.flush();
                window.location = "./login.html";
            }
        };

    }];
    return menusCtrl;
});
