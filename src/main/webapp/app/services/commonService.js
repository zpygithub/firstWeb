define([], function () {
    "use strict";
    var commonService = function ($scope) {
        this.getFormatTime = function (d) {
            if (d === null || d === "" || !d) {
                return '';
            }
            d = new Date(d);
            var timeZone = -d.getTimezoneOffset() / 60;
            d = new Date(d.getTime() + (timeZone * 60 * 60 * 1000));
            var timeStr = d.getFullYear() + "-"
                + appendZero(d.getMonth() + 1) + "-"
                + appendZero(d.getDate()) + " "
                + appendZero(d.getHours()) + ":"
                + appendZero(d.getMinutes()) + ":"
                + appendZero(d.getSeconds());
            // + " GMT" + (timeZone >= 0 ? "+" : "-")
            // + appendZero(Math.abs(timeZone)) + ":00";
            return timeStr;
        };

        function appendZero(s) {
            s = s + "";
            if (s.length >= 2) {
                return s;
            }
            return ("00" + s).substr((s + "").length);
        }

    };
    return commonService;
});
