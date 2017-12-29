define(["i18n/keyId", "bootstrap-table", "app/services/commonService"], function (i18n, bootstrapTable, CommonService) {
    "use strict";
    var adminListCtrl = ["$rootScope", "$scope", "$compile", function ($rootScope, $scope, $compile) {
        var commonService = new CommonService($scope);

        $scope.account = {
            id: "accountId",
            label: i18n.account,
            value: ""
        };

        $scope.nickname = {
            id: "nicknameId",
            label: i18n.nickname,
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
            $scope.account.value = $("#account").val().trim();
            $scope.nickname.value = $("#nickname").val().trim();
            $scope.email.value = $("#email").val().trim();
            $scope.telephone.value = $("#telephone").val().trim();
            $("#adminList").bootstrapTable('refresh', {pageNumber: 1});
        });

        $("#reset").bind("click", function () {
            $scope.account.value = "";
            $scope.nickname.value = "";
            $scope.email.value = "";
            $scope.telephone.value = "";
            $("#adminList").bootstrapTable('refresh', {pageNumber: 1});
        });

        $("#export").bind("click", function () {
            var options = {
                account: $("#account").val().trim(),
                nickname: $("#nickname").val().trim(),
                email: $("#email").val().trim(),
                telephone: $("#telephone").val().trim()
            };
            $scope.operate.export(options);
        });

        $scope.operate = {
            "query": function () {
                $("#adminList").bootstrapTable({
                    url: "system/getAdminListOnCondition",         //请求后台的URL（*）
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
                            field: "id",
                            title: "id",
                            align: "center",
                            valign: "middle",
                            sortable: true
                        }, {
                            field: "account",
                            title: i18n.account,
                            align: "center",
                            valign: "middle",
                            sortable: true
                        }, {
                            field: "nickname",
                            title: i18n.nickname,
                            align: "center",
                            valign: "middle"
                        }, {
                            field: "email",
                            title: i18n.email,
                            align: "center",
                            valign: "middle"
                        }, {
                            field: "telephone",
                            title: i18n.telephone,
                            align: "center",
                            valign: "middle"
                        }, {
                            field: "createTime",
                            title: i18n.createTime,
                            align: "center",
                            valign: "left",
                            formatter: function (value) {
                                value = commonService.getFormatTime(value);
                                return value;
                            }
                        }, {
                            field: "status",
                            title: i18n.status,
                            align: "center",
                            valign: "middle",
                            formatter: function (status) {
                                if (status == 0) {
                                    return i18n.normal;
                                } else {
                                    return i18n.freeze;
                                }
                            }
                        }]
                });
            },
            "export": function (options) {
                $.ajax({
                    type: 'post',
                    url: 'system/exportAdminList/',
                    dataType: 'json',
                    async: false,
                    data: options
                }).done(function (data) {
                    if (data.code === "00000") {
                        Lobibox.notify("success", {msg: i18n.operation_succeeded});
                        console.log(data.value);
                        if (data.value.downloadUrl && data.value.remark) {

                            $("#compressPw").val(data.value.compressPw);
                            openPostWindow('system/downLoadToPage', data.value.downloadUrl);
                        }
                    } else if (data.code === "00012") {
                        Lobibox.notify("warning", {msg: i18n.export_task_is_running});
                    } else {
                        Lobibox.notify("error", {msg: i18n.operation_failed});
                    }
                });
            }
        }

        var openPostWindow = function (url, attachAddress) {
            var tempForm = document.createElement("form");
            tempForm.id = "tempForm1";
            tempForm.method = "post";
            tempForm.action = url;

            var hideInput1 = document.createElement("input");
            hideInput1.type = "hidden";
            hideInput1.name = "attachAddress";
            hideInput1.value = attachAddress;

            tempForm.appendChild(hideInput1);
            if (document.all) {
                tempForm.attachEvent("onsubmit", function () {
                });
            } else {
                var subObj = tempForm.addEventListener("submit", function () {
                }, false);
            }
            document.body.appendChild(tempForm);
            tempForm.submit();
            document.body.removeChild(tempForm);
        }


        function queryParams() {
            var options = {
                page: this.pageNumber,
                size: this.pageSize,
                account: $scope.account.value,
                nickname: $scope.nickname.value,
                email: $scope.email.value,
                telephone: $scope.telephone.value
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

    var systemModule = angular.module("system.config");
    systemModule.controller("system.adminList", adminListCtrl);
    return systemModule;
});
