define(["i18n/keyId", "app/src/system/service/systemService"], function (i18n, SystemService) {
    "use strict";
    var modifyAdminInfoCtrl = ["$rootScope", "$scope", "camel", function ($rootScope, $scope, camel) {
        var systemService = new SystemService(camel, $scope);
        var id = $rootScope.adminInfo.id;

        $scope.modifyAdminInfo = {
            id: "modifyAdminInfoId",
            label: i18n.modify_admin_info,
        };

        $scope.account = {
            id: "accountId",
            label: i18n.admin_account,
            value: "",
            disable: true
        };

        $scope.nickname = {
            id: "nicknameId",
            label: i18n.admin_nickname,
            value: ""
        };

        $scope.email = {
            id: "emailId",
            label: i18n.admin_email,
            value: ""
        };

        $scope.telephone = {
            id: "telephoneId",
            label: i18n.admin_telephone,
            value: ""
        };

        $scope.close = {
            id: "closeId",
            label: i18n.close
        };

        $scope.confirm = {
            id: "confirmId",
            label: i18n.confirm,
            value: ""
        };

        $("#confirmId").bind("click", function () {
            var account = $("#account").val();

            var nickname = $("#nickname").val();
            if ("" == nickname) {
                Lobibox.notify("error", {msg: i18n.nicknameCanNotBeEmpty});
                $("#nickname").focus();
                return false;
            }

            var email = $("#email").val();
            // if ("" == nickname) {
            //     Lobibox.notify("error", {msg: getLocaleMsg("requiredPassword")});
            //     $("#nickname").focus();
            //     return false;
            // }

            var telephone = $("#telephone").val();
            // if ("" == nickname) {
            //     Lobibox.notify("error", {msg: getLocaleMsg("requiredPassword")});
            //     $("#nickname").focus();
            //     return false;
            // }

            var data = {
                "id": id,
                "account": account,
                "nickname": nickname,
                "email": email,
                "telephone": telephone
            };
            console.log(data);
            $.ajax({
                type: 'post',
                url: 'system/modifyAdminInfo',
                dataType: 'json',
                async: false,
                data: data,
                success: function (data) {
                    if (data.code == "00000") {
                        // window.location.href = "index.html";
                    } else if (data.code == "00003") {
                        Lobibox.notify("error", {msg: i18n.nicknameCanNotBeEmpty});
                    } else if (data.code == "00006") {
                        Lobibox.notify("warning", {msg: i18n.nicknameAlreadyExists});
                    } else if (data.code == "00008") {
                        Lobibox.notify("error", {msg: i18n.nicknameFormatError});
                    }
                }
            });
        });

        function getAdministratorById(id) {
            $.ajax({
                type: 'get',
                url: 'system/getAdministratorById/' + id,
                dataType: 'json',
                async: false,
                success: function (data) {
                    if (data.code === "00000") {
                        $scope.account.value = data.value.account;
                        $scope.nickname.value = data.value.nickname;
                        $scope.email.value = data.value.email;
                        $scope.telephone.value = data.value.telephone;
                    }
                }
            });
            // var deferred = systemService.getAdministratorById(id);
            // deferred.then(function (data) {
            //     if (data.code === "00000") {
            //         console.log(data);
            //         $scope.account.value = data.value.account;
            //         $scope.nickname.value = data.value.nickname;
            //         $scope.email.value = data.value.email;
            //         $scope.telephone.value = data.value.telephone;
            //     }
            // });
        }

        function init() {
            getAdministratorById(id);
        }

        init();

    }];

    var systemModule = angular.module("system.config");
    systemModule.controller("modifyAdminInfoCtrl", modifyAdminInfoCtrl);
    return systemModule;
});
