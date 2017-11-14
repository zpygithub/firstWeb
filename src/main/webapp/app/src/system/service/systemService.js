define(function () {
    "use strict";
    var service = function (camel, scope) {
        this.getAdministratorById = function (id) {
            var deferred = camel.get({
                url: {
                    s: "system/getAdministratorById/{id}",
                    o: {
                        id: id
                    }
                }
            });
            return deferred;
        };
    };

    service.$injector = ["camel"];
    return service;
});
