define(["i18n/keyId", "bootstrap", "lobibox"], function (i18n, bootstrap, lobibox) {
    "use strict";
    var modifyAdminInfoCtrl = ["$rootScope", "$scope", "camel", function ($rootScope, $scope, camel) {
            var id = $("#adminInfoModal").data("id");

            $scope.modifyAdminInfo = {
                id: "modifyAdminInfoId",
                label: i18n.modify_admin_info
            };

            $scope.account = {
                id: "accountId",
                label: i18n.account,
                value: "",
                disable: true
            };

            $scope.username = {
                id: "usernameId",
                label: i18n.username,
                value: ""
            };

            $scope.email = {
                id: "emailId",
                label: i18n.email,
                value: ""
            };

            $scope.telephone = {
                id: "telephoneId",
                label: i18n.telephone,
                value: ""
            };

            $scope.close = {
                id: "closeId",
                label: i18n.close
            };

            $scope.confirm = {
                id: "confirmId",
                label: i18n.confirm
            };

            $("#confirm").bind("click", function () {
                var username = $("#username").val();
                if ("" == username) {
                    Lobibox.notify("error", {msg: i18n.usernameCanNotBeEmpty});
                    $("#username").focus();
                    return false;
                }
                var email = $("#email").val();
                var telephone = $("#telephone").val();

                var data = {
                    "id": id,
                    "username": username,
                    "email": email,
                    "telephone": telephone
                };
                $.ajax({
                    type: 'post',
                    url: 'system/modifyAdminInfo',
                    dataType: 'json',
                    async: false,
                    data: data
                }).done(function (data) {
                    if (data.code == "00000") {
                        Lobibox.notify("success", {msg: i18n.operation_succeeded});
                        $("#adminInfoModal").modal("hide");
                    } else if (data.code == "00003") {
                        Lobibox.notify("error", {msg: i18n.usernameCanNotBeEmpty});
                    } else if (data.code == "00006") {
                        Lobibox.notify("warning", {msg: i18n.usernameAlreadyExists});
                    } else if (data.code == "00008") {
                        Lobibox.notify("error", {msg: i18n.usernameFormatError});
                    }
                });
            });

            function getAdministratorById(id) {
                $.ajax({
                    type: 'get',
                    url: 'system/getAdministratorById/' + id,
                    dataType: 'json',
                    async: false
                }).done(function (data) {
                    if (data.code === "00000") {
                        $scope.account.value = data.value.account;
                        $scope.username.value = data.value.username;
                        $scope.email.value = data.value.email;
                        $scope.telephone.value = data.value.telephone;
                    }
                });
            }

            function init() {
                getAdministratorById(id);
            }

            init();

        }]
    ;

    var systemModule = angular.module("system.config");
    systemModule.controller("modifyAdminInfoCtrl", modifyAdminInfoCtrl);
    return systemModule;
});
