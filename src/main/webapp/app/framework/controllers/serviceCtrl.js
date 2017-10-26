define(['i18n/keyId'], function (i18n) {
    "use strict";
    var ctrl = function ($rootScope, $state, $stateParams, mask) {
        $rootScope.i18n = i18n;
        mask.pageInitShow();
        $rootScope.menus = {
            url: "app/framework/views/menus.html"
        };
        $rootScope.footer = {
            url: "app/framework/views/footer.html"
        };
        $rootScope.$state = $state;
        $rootScope.$stateParams = $stateParams;

        angular.element(window).bind("resize", setContentMinHeight);
        $rootScope.$on("$viewContentLoaded", function () {
            setContentMinHeight();
            mask.pageInitHide();
        });
        function setContentMinHeight() {
            var height1 = $(window).height();
            if (height1 < 600) {
                height1 = 600;
            }
            var height2 = $("#service-menus").height() || 60;
            var height3 = $("#console-top-footer").height() || 101;
            $("#service-content").css("min-height", height1 - height2 - height3);
        }
    };
    ctrl.$injector = ["$rootScope", "$state", "$stateParams"];
    return ctrl;
});
