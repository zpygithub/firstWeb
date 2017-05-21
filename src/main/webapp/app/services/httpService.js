/**
 * Created by zpy on 2017/4/20.
 */
define(['i18n/keyId'], function (i18n) {
    "use strict";
    var subRegRex = /\{s*([^\|\}]+?)\s*(?:\|([^\}]*))?\s*\}/g;
    console.log("httpService");
    var homeLocation = "login.html";
    var sub = function (s, o) {
        return ((s.replace) ? s.replace(subRegRex, function (match, key) {
                return (!angular.isUndefined(o[key])) ? o[key] : match;
            }) : s);
    };
    var timeOut = 10 * 60 * 1000;
    var redirect302 = function (xhr) {
        if (xhr.status === 200) {
            window.location.reload();
        }
    };
    var redirect403 = function (xhr) {
        if (xhr.status === 403) {
            if ($('#console_frame_for_bidden_confirm').length === 0) {
                var options = {
                    type: "error",
                    content: i18n.console_term_errorForbidden_msg,
                    width: "550px",
                    modal: true,
                    "append-to": "body",
                    close: function (evt) {
                        window.location.href = homeLocation;
                    },
                    buttons: [{
                        key: "console_frame_forbidden_confirm",
                        label: i18n.console_frame_forbidden_confirm,
                        focused: true,
                        handler: function (event) {
                            msg.destroy();
                            window.location.href = homeLocation;
                        }
                    }]
                };
                var msg = new tinyWidget.Message(options);
                msg.show();
            }
        }
    };
    var redirect401 = function (xhr) {
        if (xhr.status === 401) {
            window.location.href = "./logout";
        }
    };
    var service = function (mask, $q, storage, $rootScope) {
        this.get = function (config) {
            var deferred = $q.defer();
            var settings = {
                "type": "GET",
                "contentType": "application/json; charset=UTF-8",
                "timeout": config.timeout || timeOut,
                "url": !angular.isString(config.url) ? sub(config.url.s, config.url.o) : config.url,
                "data": config.param || {},
                "beforeSend": function (request, setting) {
                    if (config.mask) {
                        mask.show();
                    }
                    config.beforeSend(request, setting);
                },
                "complete": function (xhr, status) {
                    if (config.mask) {
                        mask.hide();
                    }
                    redirect302(xhr);
                    redirect401(xhr);
                    redirect403(xhr);
                }
            };
            if (config.contentType) {
                settings.contentType = config.contentType;
            }
            if (config.dataType) {
                settings.dataType = config.dataType;
            }
            var $ajax = $.ajax(settings);
            $ajax.success(function () {
                deferred.resolve.apply(deferred, arguments);
            }).error(function () {
                deferred.reject.apply(deferred, arguments);
            });
            return deferred.promise;
        };

        this.post = function (config) {
            var deferred = $q.defer();
            var settings = {
                "type": "POST",
                "contentType": "application/json; charset=UTF-8",
                "timeout": config.timeout || timeOut,
                "url": !angular.isString(config.url) ? sub(config.url.s, config.url.o) : config.url,
                "data": typeof config.param === "string" ? config.params : JSON.stringify(config.params),
                "beforeSend": function (request, setting) {
                    if (config.mask) {
                        mask.show();
                    }
                    config.beforeSend(request, setting);
                },
                "complete": function (xhr, status) {
                    if (config.mask) {
                        mask.hide();
                    }
                    redirect302(xhr);
                    redirect401(xhr);
                    redirect403(xhr);
                }
            };
            if (config.contentType) {
                settings.contentType = config.contentType;
            }
            if (config.dataType) {
                settings.dataType = config.dataType;
            }
            var $ajax = $.ajax(settings);
            $ajax.success(function () {
                deferred.resolve.apply(deferred, arguments);
            }).error(function () {
                deferred.reject.apply(deferred, arguments);
            });
            return deferred.promise;
        };

        this.deleter = function (config) {
            var deferred = $q.defer();
            var settings = {
                "type": "DELETE",
                "contentType": "application/json; charset=UTF-8",
                "timeout": config.timeout || timeOut,
                "url": !angular.isString(config.url) ? sub(config.url.s, config.url.o) : config.url,
                "data": !config.params ? null : (typeof config.param === "string" ? config.params : JSON.stringify(config.params || {})),
                "beforeSend": function (request, setting) {
                    if (config.mask) {
                        mask.show();
                    }
                    config.beforeSend(request, setting);
                },
                "complete": function (xhr, status) {
                    if (config.mask) {
                        mask.hide();
                    }
                    redirect302(xhr);
                    redirect401(xhr);
                    redirect403(xhr);
                }
            };
            if (config.contentType) {
                settings.contentType = config.contentType;
            }
            if (config.dataType) {
                settings.dataType = config.dataType;
            }
            var $ajax = $.ajax(settings);
            $ajax.success(function () {
                deferred.resolve.apply(deferred, arguments);
            }).error(function () {
                deferred.reject.apply(deferred, arguments);
            });
            return deferred.promise;
        };

        this.deleter = function (config) {
            var deferred = $q.defer();
            var settings = {
                "type": "DELETE",
                "contentType": "application/json; charset=UTF-8",
                "timeout": config.timeout || timeOut,
                "url": !angular.isString(config.url) ? sub(config.url.s, config.url.o) : config.url,
                "data": !config.params ? null : (typeof config.param === "string" ? config.params : JSON.stringify(config.params || {})),
                "beforeSend": function (request, setting) {
                    if (config.mask) {
                        mask.show();
                    }
                    config.beforeSend(request, setting);
                },
                "complete": function (xhr, status) {
                    if (config.mask) {
                        mask.hide();
                    }
                    redirect302(xhr);
                    redirect401(xhr);
                    redirect403(xhr);
                }
            };
            if (config.contentType) {
                settings.contentType = config.contentType;
            }
            if (config.dataType) {
                settings.dataType = config.dataType;
            }
            var $ajax = $.ajax(settings);
            $ajax.success(function () {
                deferred.resolve.apply(deferred, arguments);
            }).error(function () {
                deferred.reject.apply(deferred, arguments);
            });
            return deferred.promise;
        };

        this.put = function (config) {
            var deferred = $q.defer();
            var settings = {
                "type": "PUT",
                "contentType": "application/json; charset=UTF-8",
                "timeout": config.timeout || timeOut,
                "url": !angular.isString(config.url) ? sub(config.url.s, config.url.o) : config.url,
                "data": typeof config.param === "string" ? config.params : JSON.stringify(config.params || {}),
                "beforeSend": function (request, setting) {
                    if (config.mask) {
                        mask.show();
                    }
                    config.beforeSend(request, setting);
                },
                "complete": function (xhr, status) {
                    if (config.mask) {
                        mask.hide();
                    }
                    redirect302(xhr);
                    redirect401(xhr);
                    redirect403(xhr);
                }
            };
            if (config.contentType) {
                settings.contentType = config.contentType;
            }
            if (config.dataType) {
                settings.dataType = config.dataType;
            }
            var $ajax = $.ajax(settings);
            $ajax.success(function () {
                deferred.resolve.apply(deferred, arguments);
            }).error(function () {
                deferred.reject.apply(deferred, arguments);
            });
            return deferred.promise;
        };

        this.patch = function (config) {
            var deferred = $q.defer();
            var settings = {
                "type": "PATCH",
                "contentType": "application/json; charset=UTF-8",
                "timeout": config.timeout || timeOut,
                "url": !angular.isString(config.url) ? sub(config.url.s, config.url.o) : config.url,
                "data": typeof config.param === "string" ? config.params : JSON.stringify(config.params || {}),
                "beforeSend": function (request, setting) {
                    if (config.mask) {
                        mask.show();
                    }
                    config.beforeSend(request, setting);
                },
                "complete": function (xhr, status) {
                    if (config.mask) {
                        mask.hide();
                    }
                    redirect302(xhr);
                    redirect401(xhr);
                    redirect403(xhr);
                }
            };
            if (config.contentType) {
                settings.contentType = config.contentType;
            }
            if (config.dataType) {
                settings.dataType = config.dataType;
            }
            var $ajax = $.ajax(settings);
            $ajax.success(function () {
                deferred.resolve.apply(deferred, arguments);
            }).error(function () {
                deferred.reject.apply(deferred, arguments);
            });
            return deferred.promise;
        };

        this.ajax = function (config) {
            var settings = {
                "type": config.type,
                "contentType": "application/json; charset=UTF-8",
                "timeout": config.timeout || timeOut,
                "url": !angular.isString(config.url) ? sub(config.url.s, config.url.o) : config.url,
                "data": typeof config.param === "string" ? config.params : JSON.stringify(config.params),
                "beforeSend": function (request, setting) {
                    if (config.mask) {
                        mask.show();
                    }
                    config.beforeSend(request, setting);
                },
                "complete": function (xhr, status) {
                    if (config.mask) {
                        mask.hide();
                    }
                    redirect302(xhr);
                    redirect401(xhr);
                    redirect403(xhr);
                }
            };
            if (config.contentType) {
                settings.contentType = config.contentType;
            }
            if (config.dataType) {
                settings.dataType = config.dataType;
            }
            var $ajax = $.ajax(settings);
            return $ajax;
        };
    };
    service.$injector = ["mask", "$q", "storage", "$rootScope"];
    return service;
})