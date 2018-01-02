define(["i18n/keyId", "bootstrap-table", "app/services/commonService"], function (i18n, bootstrapTable, CommonService) {
    "use strict";
    var registerUserListCtrl = ["$rootScope", "$scope", "$compile", function ($rootScope, $scope, $compile) {
        var commonService = new CommonService($scope);

        $scope.account = {
            id: "accountId",
            label: i18n.account,
            value: ""
        };

        $scope.username = {
            id: "usernameId",
            label: i18n.username,
            value: ""
        };

        $scope.sex = {
            id: "sexId",
            label: i18n.sex,
            value: "",
            selectedOptions: [{
                value: "",
                text: ""
            }, {
                value: "1",
                text: i18n.male
            }, {
                value: "2",
                text: i18n.female
            }, {
                value: "3",
                text: i18n.unknown
            }]
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

        $scope.district = {
            id: "districtId",
            label: i18n.district,
            value: ""
        };

        $scope.address = {
            id: "addressId",
            label: i18n.address,
            value: ""
        };

        $scope.query = {
            id: "queryId",
            label: i18n.query
        };

        $scope.reset = {
            id: "resetId",
            label: i18n.reset
        };

        $scope.export = {
            id: "exportId",
            label: i18n.export
        };

        $("#query").bind("click", function () {
            $("#registerUserList").bootstrapTable('refresh', {pageNumber: 1});
        });

        $("#reset").bind("click", function () {
            $("#unzipPw").text("");
            $scope.account.value = "";
            $scope.username.value = "";
            $scope.email.value = "";
            $scope.telephone.value = "";
            $scope.address.value = "";
            $scope.sex.value = "";
            $("#registerUserList").bootstrapTable('refresh', {pageNumber: 1});
        });

        $("#export").bind("click", function () {
            var options = {
                account: $scope.account.value,
                username: $scope.username.value,
                sex: $scope.sex.value,
                email: $scope.email.value,
                telephone: $scope.telephone.value,
                address: $scope.address.value
            };
            $scope.operate.export(options);
        });

        $scope.operate = {
            "query": function () {
                $("#registerUserList").bootstrapTable({
                    url: "user/getRegisterUserListOnCondition",         //请求后台的URL（*）
                    method: "get",                      //请求方式（*）
                    toolbar: "#toolbar",                //工具按钮用哪个容器
                    striped: false,                      //是否显示行间隔色
                    pagination: true,                   //是否显示分页（*）
                    sortable: true,                    //是否启用排序
                    sortOrder: "asc",                   //排序方式
                    queryParamsType: 'limit',
                    queryParams: queryParams,           //传递参数（*）
                    sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                    dataType: "json",
                    pageNumber: 1,                      //初始化加载第一页，默认第一页
                    pageSize: 5,                       //每页的记录行数（*）
                    pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
                    minimumCountColumns: 2,             //最少允许的列数
                    clickToSelect: true,                //是否启用点击选中行
                    uniqueId: "id",                     //每一行的唯一标识，一般为主键列
                    // paginationPreText: "上一页",
                    // paginationNextText: "下一页",
                    responseHandler: getAdminListOnCondition,
                    columns: [
                        {
                            field: "account",
                            title: i18n.account,
                            align: "center",
                            sortable: true
                        }, {
                            field: "username",
                            title: i18n.username,
                            align: "center",
                            visible: false
                        }, {
                            field: "sex",
                            title: i18n.sex,
                            align: "center"
                        }, {
                            field: "email",
                            title: i18n.email,
                            align: "center"
                        }, {
                            field: "telephone",
                            title: i18n.telephone,
                            align: "center"
                        }, {
                            field: "district",
                            title: i18n.district,
                            align: "center"
                        }, {
                            field: "address",
                            title: i18n.address,
                            align: "center"
                        }, {
                            field: "createTime",
                            title: i18n.createTime,
                            align: "center",
                            formatter: function (value) {
                                value = commonService.getFormatTime(value);
                                return value;
                            }
                        }, {
                            field: "status",
                            title: i18n.status,
                            align: "center"
                        }]
                });
            },
            "export": function (options) {
                $.ajax({
                    type: 'post',
                    url: 'user/exportRegisterUserList/',
                    dataType: 'json',
                    async: false,
                    data: options
                }).done(function (data) {
                    if (data.code === "00000") {
                        if (data.value.downloadUrl && data.value.remark) {
                            Lobibox.notify("success", {msg: i18n.operation_succeeded});
                            $("#unzipPw").text(i18n.unzip_password + data.value.remark);
                            commonService.downLoadToPage('user/downLoadToPage', data.value.downloadUrl);
                        } else {
                            Lobibox.notify("warning", {msg: i18n.export_no_data});
                        }
                    } else if (data.code === "00012") {
                        Lobibox.notify("warning", {msg: i18n.export_task_is_running});
                    } else {
                        Lobibox.notify("error", {msg: i18n.operation_failed});
                    }
                });
            }
        }

        function queryParams() {
            var options = {
                page: this.pageNumber,
                size: this.pageSize,
                account: $scope.account.value,
                username: $scope.username.value,
                sex: $scope.sex.value,
                email: $scope.email.value,
                telephone: $scope.telephone.value,
                address: $scope.address.value
            };
            return options;
        }

        function getAdminListOnCondition(res) {
            if (res) {
                return {
                    "rows": res.value.list,
                    "total": res.value.pageInfo.totalRecords
                };
            } else {
                return {
                    "rows": [],
                    "total": 0
                };
            }
        }

        function init() {
            $scope.operate.query();
        }

        init();
    }];

    var userModule = angular.module("user.config");
    userModule.controller("user.registerUserList", registerUserListCtrl);
    return userModule;
});
