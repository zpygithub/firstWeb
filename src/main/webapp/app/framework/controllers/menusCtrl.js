define([], function () {
    "use strict";
    var menusCtrl = ["$rootScope", "$scope", "$compile", "storage", function ($rootScope, $scope, $compile, storage) {
        var personalInfo = $rootScope.personalInfo;
        var i18n = $rootScope.i18n;

        $scope.personalInfo = {
            id: "personalInfoId",
            text: personalInfo.account
        };

        $scope.modifyPersonalInfo = {
            id: "modifyPersonalInfoId",
            title: i18n.personal_info
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
