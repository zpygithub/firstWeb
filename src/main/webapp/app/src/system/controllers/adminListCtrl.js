define(["i18n/keyId", "bootstrap-table"], function (i18n, bootstrapTable) {
    "use strict";
    var adminListCtrl = ["$rootScope", "$scope", "$compile", function ($rootScope, $scope, $compile) {
        initTable();

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

        $("#query").bind("click", function () {
            var data = {
                "account": $("#account").val().trim(),
                "nickname": $("#nickname").val().trim(),
                "email": $("#email").val().trim(),
                "telephone": $("#telephone").val().trim()
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
                } else {
                    Lobibox.notify("error", {msg: i18n.nicknameFormatError});
                }
            });
        });

        function initTable() {
            $("#tb_departments").bootstrapTable({
                url: "system/getAdminList",         //请求后台的URL（*）
                method: "get",                      //请求方式（*）
                toolbar: "#toolbar",                //工具按钮用哪个容器
                striped: true,                      //是否显示行间隔色
                cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: true,                   //是否显示分页（*）
                sortable: false,                    //是否启用排序
                sortOrder: "asc",                   //排序方式
                // queryParams: queryParams,           //传递参数（*）
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                dataType: "json",
                pageNumber: 1,                      //初始化加载第一页，默认第一页
                pageSize: 10,                       //每页的记录行数（*）
                pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
                showColumns: true,                  //是否显示所有的列
                showRefresh: true,                  //是否显示刷新按钮
                minimumCountColumns: 2,             //最少允许的列数
                clickToSelect: true,                //是否启用点击选中行
                uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
                showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
                showExport: true,
                exportDataType: "all",
                responseHandler: getAdminList,
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
                        // }, {
                        //     field: "status",
                        //     title: "Create Time",
                        //     align: "center",
                        //     valign: "left",
                        //     formatter: function (value, row, index) {
                        //         return new Date(value).format("yyyy-MM-dd hh:mm:ss");
                        //     }
                    }]
            });
        }

        function getAdminList(res) {
            if (res) {
                console.log(res);
                return {
                    "rows": res.value,
                    "total": res.value.length
                };
            } else {
                return {
                    "rows": [],
                    "total": 0
                };
            }
        }
    }];

    var systemModule = angular.module("system.config");
    systemModule.controller("system.admin", adminListCtrl);
    return systemModule;
});
