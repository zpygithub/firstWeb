define(["i18n/keyId", "bootstrap-table", "bootstrap-datetimepicker", "app/services/commonService", "bootstrap-table-cn", "bootstrap-datetimepicker-cn", "lobibox"],
    function (i18n, bootstrapTable, bootstrapDatetimepicker, CommonService, bootstrapTableCn, bootstrapDatetimepickerCn, lobibox) {
        "use strict";
        var adminListCtrl = ["$rootScope", "$scope", "$compile", function ($rootScope, $scope, $compile) {
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

            $scope.createTimeBegin = {
                id: "createTimeBeginId",
                label: i18n.createTime,
                value: ""
            };

            $scope.createTimeEnd = {
                id: "createTimeEndId",
                label: i18n.createTime,
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
                getCreateTime();
                $("#adminList").bootstrapTable("refresh", {pageNumber: 1});
            });

            $("#reset").bind("click", function () {
                $scope.account.value = "";
                $scope.username.value = "";
                $scope.email.value = "";
                $scope.telephone.value = "";
                $("#unzipPw").text("");
                $("#createTimeBegin").val("");
                $scope.createTimeBegin.value = "";
                $("#createTimeEnd").val("");
                $scope.createTimeEnd.value = "";
                $("#adminList").bootstrapTable("refresh", {pageNumber: 1});
            });

            $("#export").bind("click", function () {
                getCreateTime();
                var options = exportParams();
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
                        paginationLoop: false,
                        sortable: true,                    //是否启用排序
                        sortOrder: "asc",                   //排序方式
                        queryParamsType: "limit",
                        queryParams: queryParams,           //传递参数（*）
                        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                        dataType: "json",
                        pageNumber: 1,                      //初始化加载第一页，默认第一页
                        pageSize: 10,                       //每页的记录行数（*）
                        pageList: [10, 20, 50, 100],        //可供选择的每页的行数（*）
                        minimumCountColumns: 2,             //最少允许的列数
                        clickToSelect: true,                //是否启用点击选中行
                        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
                        showColumns: true,
                        responseHandler: getAdminListOnCondition,
                        columns: [
                            {
                                field: "account",
                                title: i18n.account,
                                align: "center",
                                width: "10%"
                            }, {
                                field: "username",
                                title: i18n.username,
                                align: "center",
                                width: "10%"
                            }, {
                                field: "email",
                                title: i18n.email,
                                align: "center",
                                width: "20%"
                            }, {
                                field: "telephone",
                                title: i18n.telephone,
                                align: "center",
                                width: "10%"
                            }, {
                                field: "createTime",
                                title: i18n.createTime,
                                align: "center",
                                width: "20%",
                                formatter: function (value) {
                                    return commonService.getFormatTime(value);
                                }
                            }, {
                                field: "status",
                                title: i18n.status,
                                align: "center",
                                width: "10%",
                                formatter: function (value) {
                                    if (0 == value) {
                                        return i18n.normal;
                                    } else {
                                        return i18n.freeze;
                                    }
                                }
                            }, {
                                field: "operate",
                                title: i18n.operate,
                                align: "center",
                                width: "20%",
                                events: operateEvents,
                                formatter: operateFormatter
                            }]
                    });
                },
                "export": function (options) {
                    $.ajax({
                        type: "post",
                        url: "system/exportAdminList/",
                        dataType: "json",
                        async: false,
                        data: options
                    }).done(function (data) {
                        if (data.code === "00000") {
                            if (data.value.downloadUrl && data.value.remark) {
                                Lobibox.notify("success", {msg: i18n.operation_succeeded});
                                $("#unzipPw").text(i18n.unzip_password + data.value.remark);
                                commonService.downLoadToPage("system/downLoadToPage", data.value.downloadUrl);
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

            function operateFormatter(value, row, index) {
                var optColumn = "";
                optColumn += "<a id='adminInfoModal'>" + i18n.modify + "</a>&nbsp;&nbsp;&nbsp;";
                if (0 == row.status) {
                    optColumn += "<a id='change'>" + i18n.freeze + "</a>";
                } else {
                    optColumn += "<a id='change'>" + i18n.recover + "</a>";
                }
                return optColumn;
            }

            window.operateEvents = {
                "click #adminInfoModal": function (e, value, row, index) {
                    $("#adminInfoModal").data("id", row.id);
                    $("#adminInfoModal").modal({
                        remote: "app/src/system/views/modifyAdminInfo.html",
                        backdrop: "static"
                    });
                    $("#adminInfoModal").on("hidden.bs.modal", function () {
                        $(this).removeData("bs.modal");
                        $("#adminList").bootstrapTable("refresh", {pageNumber: this.pageNumber});
                    })
                },
                "click #change": function (e, value, row, index) {
                    var content = "";
                    if (0 == row.status) {
                        content = i18n.freeze_admin
                    } else {
                        content = i18n.recover_admin
                    }
                    Lobibox.confirm({
                        title: i18n.confirm,
                        msg: content,
                        width: 300,
                        callback: function ($this, type, ev) {
                            if (type == "yes") {
                                var options = {
                                    id: row.id
                                };
                                if (0 == row.status) {
                                    options.status = -1;
                                } else {
                                    options.status = 0;
                                }
                                $.ajax({
                                    type: "post",
                                    url: "system/changeAdminStatus/",
                                    dataType: "json",
                                    async: false,
                                    data: options
                                }).done(function (data) {
                                    if (data.code === "00000") {
                                        Lobibox.notify("success", {msg: i18n.operation_succeeded});
                                        $("#adminList").bootstrapTable("refresh", {pageNumber: this.pageNumber});
                                    } else {
                                        Lobibox.notify("error", {msg: i18n.operation_failed});
                                    }
                                });
                            }
                        }
                    });
                }
            };

            function queryParams() {
                var options = exportParams();
                options.page = this.pageNumber;
                options.size = this.pageSize;
                return options;
            }

            function getCreateTime() {
                if ("" !== $("#createTimeBegin").val()) {
                    $scope.createTimeBegin.value = commonService.setFormatTime($("#createTimeBegin").val() + " 00:00:00");
                }
                if ("" !== $("#createTimeEnd").val()) {
                    $scope.createTimeEnd.value = commonService.setFormatTime($("#createTimeEnd").val() + " 23:59:59");
                }
            }

            function exportParams() {
                var options = {
                    account: $scope.account.value,
                    username: $scope.username.value,
                    email: $scope.email.value,
                    telephone: $scope.telephone.value,
                    createTimeBegin: $scope.createTimeBegin.value,
                    createTimeEnd: $scope.createTimeEnd.value
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

            function initDateTimePicker() {
                $("#createTimeBegin").datetimepicker({
                    format: "yyyy-mm-dd",
                    minView: 3,
                    autoclose: true,
                    language: "zh-CN"
                }).on("click", function () {
                    $("#createTimeBegin").datetimepicker("setEndDate", $("#createTimeEnd").val());
                });
                $("#createTimeEnd").datetimepicker({
                    format: "yyyy-mm-dd",
                    minView: 3,
                    autoclose: true,
                    language: "zh-CN"
                }).on("click", function () {
                    $("#createTimeEnd").datetimepicker("setStartDate", $("#createTimeBegin").val());
                });
            }

            function init() {
                initDateTimePicker();
                $scope.operate.query();
            }

            init();
        }];

        var systemModule = angular.module("system.config");
        systemModule.controller("system.adminList", adminListCtrl);
        return systemModule;
    });
