define(['jquery', "angular"], function ($, angular) {
    "use strict";
    var ctrl = ["$rootScope", "$scope", "$compile", function ($rootScope, $scope, $compile) {
        var user = $rootScope.user;
        var i18n = $rootScope.i18n;
        console.log(i18n.term_errorForbidden_msg);
    }];
    return ctrl;
});
