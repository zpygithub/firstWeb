define([], function () {
    var frameworkCtrl = ["$rootScope", "$scope", function ($rootScope, $scope) {
        $rootScope.menus = {
            "url": "app/framework/views/menus.html"
        };
    }];
    return frameworkCtrl;
})  
