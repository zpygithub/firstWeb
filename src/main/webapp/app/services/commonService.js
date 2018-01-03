define([], function () {
    "use strict";
    var commonService = function () {
        this.downLoadToPage = function (url, downloadUrl) {
            var tempForm = document.createElement("form");
            tempForm.id = "tempFormId";
            tempForm.method = "post";
            tempForm.action = url;

            var hideInput = document.createElement("input");
            hideInput.type = "hidden";
            hideInput.name = "downloadUrl";
            hideInput.value = downloadUrl;

            tempForm.appendChild(hideInput);
            document.body.appendChild(tempForm);
            tempForm.submit();
            document.body.removeChild(tempForm);
        }

        this.setFormatTime = function (d) {
            if (d === null || d === "") {
                return;
            }
            d = new Date(d);
            var timeZone = -d.getTimezoneOffset() / 60;
            d = new Date(d.getTime() - (timeZone * 60 * 60 * 1000));
            var timeStr = d.getFullYear() + "-"
                + appendZero(d.getMonth() + 1) + "-"
                + appendZero(d.getDate()) + " "
                + appendZero(d.getHours()) + ":"
                + appendZero(d.getMinutes()) + ":"
                + appendZero(d.getSeconds());
            return timeStr;
        }

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
