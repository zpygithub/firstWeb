define(function () {
    "use strict";
    var service = function (camel) {
        this.getAdministratorById = function (id) {
            var deferred = camel.get({
                url: {
                    s: "system/getAdministratorById/{id}",
                    o: {id: id}
                }
            });
            return deferred;
        };
    };

    service.$injector = ["camel"];
    return service;
});

var optColumn = "";
                    if($scope.account.id === 1 && aData.status === 1){
	                    optColumn += "<a class='btn-link' ng-click='modify()'>修改</a>&nbsp;&nbsp;";
	                }
                    optColumn += "<a class='btn-link' ng-click='viewGroupInfo("+aData.id+")'>" +i18n.isv_role_search_group_label+ "</a>&nbsp;&nbsp;";
                	if($scope.account.id===1&&aData.id != 1){
                    	if(aData.status === 1){
                    		optColumn += "<a class='btn-link' ng-click='freeze(-1)'>停用</a> ";  
                        }else{
                        	optColumn += "<a class='btn-link' ng-click='freeze(1)'>启用</a> ";     
                        }
                	}
                    var optLink = $compile($(optColumn));
                    var optScope = $scope.$new();
                    optScope.viewGroupInfo = function () {
                 	   var createWindow = new Window({
                            "winId" : "createViewGroupWindowId",
                            "title" : i18n.isv_role_search_group_label,
                            "userId":aData.id,
                            "content-type" : "url",
                            "content" : "src/app/business/system/views/operatorDetailGroup.html",
                            "height" : 600,
                            "width" : 800,
                            "buttons": [
                                null,
                                null
                            ],
                            "close": function (event) {
                            }
                        }).show();                    	   
                    };
