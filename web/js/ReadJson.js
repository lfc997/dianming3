/**
 Created by ypl on 2018/9/3;
 */
'use strict';
;(function (window, undefined) {


    function readTextFile(file, callback) {
        var rawFile = new createXMlHttp();

        rawFile.overrideMimeType("application/json");
        rawFile.open("GET", file, true);
        rawFile.onreadystatechange = function () {
            console.log("222")
            if (rawFile.readyState === 4 && rawFile.status == 200) {
                callback(rawFile.responseText);
                console.log("333")
            }
        }
        rawFile.send(null);
    }

    function createXMlHttp() {
        try {
            return new XMLHttpRequest();
        } catch (e) {
            try {
                return new ActiveXObject("Msxml2.XMLHTTP");
            } catch (e) {
                try {
                    return new ActiveXObject("Microsoft.XMLHTTP");

                } catch (e) {
                    return "该浏览器不知请求ajax";
                }
            }
        }

    }
    window.readTextFile = readTextFile;
})(window, undefined)
