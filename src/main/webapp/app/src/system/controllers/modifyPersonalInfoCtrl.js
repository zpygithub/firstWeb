define([], function () {
    "use strict";
    var modifyPersonalInfoCtrl = [
        "$rootScope",
        "$scope",
        "$compile",
        function ($rootScope, $scope, $compile) {
            $('#myModal').on('shown.bs.modal', function () {
                $('#myInput').focus()
            })

        }];
    return modifyPersonalInfoCtrl;
});
