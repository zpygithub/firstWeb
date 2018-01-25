define(['i18n/keyId'], function (i18n) {
    "use strict";
    var initDataService = function () {
        this.initDistrict = function ($scope) {
            $scope.province = {
                id: "provinceId",
                value: "",
                disabled: false,
                selectedOptions: "",
                select: function () {
                    $scope.city.value = "";
                    $scope.district.value = "";
                    $scope.city.disabled = false;
                    $scope.district.disabled = false;
                    var selectedValue = $scope.province.value;
                    if (selectedValue) {
                        initDistrictData($scope, selectedValue);
                    }
                }
            };
            $scope.city = {
                id: "cityId",
                value: "",
                disabled: true,
                selectedOptions: "",
                select: function () {
                    var selectedValue = $scope.city.value;
                    if (selectedValue) {
                        initDistrictData($scope, selectedValue);
                    }
                }
            }
            $scope.district = {
                id: "districtId",
                label: i18n.district,
                value: "",
                disabled: true,
                selectedOptions: ""
            }
            initDistrictData($scope, "init");
        };

        function initDistrictData($scope, code) {
            $.ajax({
                type: "get",
                url: "system/getDistricts/" + code,
                dataType: "json",
                async: false
            }).done(function (data) {
                var valuesArr = [];
                if (data.code === "00000") {
                    if (data.value && data.value.length > 0) {
                        data.value.forEach(function (item) {
                            var obj = {
                                value: item.id,
                                text: item.name
                            };
                            valuesArr.push(obj);
                        });
                        if (code == "init") {
                            $scope.province.selectedOptions = valuesArr;
                        } else if (code.indexOf("0000") > -1) {
                            $scope.city.selectedOptions = valuesArr;
                        } else {
                            $scope.district.selectedOptions = valuesArr;
                        }
                    }
                }
            });
        };

    };
    return initDataService;
});
