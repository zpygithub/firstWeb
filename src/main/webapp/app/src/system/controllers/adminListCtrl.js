define(["bootstrap-table"], function (bootstrapTable) {
    "use strict";
    var adminListCtrl = ["$rootScope", "$scope", "$compile", function ($rootScope, $scope, $compile) {
        initTable();

        function initTable() {
            $('#tb_departments').bootstrapTable({
                // url: '/Home/GetDepartment',         //请求后台的URL（*）
                method: 'get',                      //请求方式（*）
                toolbar: '#toolbar',                //工具按钮用哪个容器
                striped: true,                      //是否显示行间隔色
                cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: true,                   //是否显示分页（*）
                sortable: false,                    //是否启用排序
                sortOrder: "asc",                   //排序方式
                // queryParams: queryParams,           //传递参数（*）
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                dataType: 'json',
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
                exportDataType: 'all',
                // responseHandler: responseHandler,
                columns: [
                    {
                        field: '',
                        title: 'Sort No.',
                        formatter: function (value, row, index) {
                            return index + 1;
                        }
                    },
                    {
                        field: 'id',
                        title: 'User ID',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                    }, {
                        field: 'institutionCode',
                        title: 'Institution Code',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                    }, {
                        field: 'institutionName',
                        title: 'Institution Name',
                        align: 'center',
                        valign: 'middle'
                    }, {
                        field: 'loginId',
                        title: 'Login Name',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                    }, {
                        field: 'realName',
                        title: 'Real Name',
                        align: 'center',
                        valign: 'middle'
                    }, {
                        field: 'createTime',
                        title: 'Create Time',
                        align: 'center',
                        valign: 'left',
                        formatter: function (value, row, index) {
                            return new Date(value).format('yyyy-MM-dd hh:mm:ss');
                        }
                    }, {
                        field: 'homeAddress',
                        title: 'Address',
                        align: 'center',
                        valign: 'middle'
                    }]
            });
        }
    }];

    var systemModule = angular.module("system.config");
    systemModule.controller("system.admin", adminListCtrl);
    return systemModule;
});
