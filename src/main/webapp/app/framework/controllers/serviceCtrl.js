define(['i18n/keyId'], function (i18n) {
    "use strict";
    var ctrl = function ($rootScope, $state, $stateParams, servicePlugin, frameworkService, mask) {
        $rootScope.i18n = i18n;
        mask.pageInitShow();
        $rootScope.menus = {
            url: "src/app/framework/views/menus.html"
        };
        $rootScope.footer = {
            url: "src/app/framework/views/footer.html"
        };
        $rootScope.$state = $state;
        $rootScope.$stateParams = $stateParams;
        $rootScope.closeMenusFavoriteError = function () {
            $rootScope.favoriteError = false;
        };
        angular.element(window).bind("resize", setContentMinHeight);
        $rootScope.$on("$viewContentLoaded", function () {
            setContentMinHeight();
            mask.pageInitHide();
        });
        function setContentMinHeight() {
            var height1 = $(window).height();
            if (height1 < 600) {
                height1 = 600;
            }
            var height2 = $("#service-menus").height() || 60;
            var height3 = $("#console-top-footer").height() || 101;
            $("#service-content").css("min-height", height1 - height2 - height3);
        }

        $rootScope.rootHref = function (href) {
            if (!href || href === "" || href === "#") {
                return href;
            }
            href = $rootScope.addOrReplaceUrlParam(href, "agencyId", $rootScope.getUrlParam("agencyId", true));

            var region = $rootScope.getUrlParam("region", true);
            if (region && region !== "" && region !== "null") {
                href = $rootScope.addOrReplaceUrlParam(href, "region", region);
            }

            var indexHash = href.indexOf("#/");
            if (indexHash === -1) {
                return href;
            }
            var indexQuery = href.indexOf("?");
            if (indexQuery === -1) {
                return href.replace("#/", "?root_url=");
            } else if (indexHash < indexQuery) {
                href = href.replace("?", "&");
                return href.replace("#/", "?root_url=");
            } else {
                var tmpHrefs = href.split("#/");
                tmpHrefs[1] = tmpHrefs[1].replace("?", "&");
                return tmpHrefs[0] + "&root_url=" + tmpHrefs[1];
            }

            $rootScope.addOrReplaceUrlParam = function (href, key, value) {
                if (!href || !key) {
                    return href;
                }
                var hrefs = href.split("#/");
                var hrefPostfix = "";
                if (hrefs.length > 1) {
                    hrefPostfix = "#/" + hrefs[1];
                }
                hrefs[0] = $rootScope.delUrlParam(hrefs[0], key);
                if (value) {
                    if (hrefs[0].indexOf("?") !== -1) {
                        hrefs[0] = hrefs[0] + "&" + key + "=" + value;
                    } else {
                        hrefs[0] = hrefs[0] + "?" + key + "=" + value;
                    }
                }
                return hrefs[0] + hrefPostfix;
            };

            $rootScope.getUrlParam = function (paramKey, scopeFlag) {
                var pageUrl = window.location.search.substring(1);
                if (pageUrl) {
                    var urlVariables = pageUrl.split("&");
                    for (var i = 0; i < urlVariables.length; i++) {
                        var paramName = urlVariables[i].split("=");
                        if (paramName[0] === paramKey) {
                            return paramName[1];
                        }
                    }
                }
                if (scopeFlag) {
                    if (paramKey === "agencyId") {
                        return $rootScope.userId;
                    } else if (paramKey === "region") {
                        return encodeURIComponent($rootScope.projectName || "");
                    }
                } else {
                    return null;
                }
            };

            $rootScope.delUrlParam = function (url, name) {
                return url.replace(new RegExp("[?&]" + name + "=[^&#]*(#.*)?$"), "$1")
                    .replace(new RegExp("[?&]" + name + "=[^&]*&"), "$1");
            };
        };
        ctrl.$injector = ["$rootScope", "$state", "$stateParams", "servicePlugin", "frameworkService"];
        return ctrl;
    }
});
