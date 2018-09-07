/**
 Created by ypl on 2018/9/2;
 */
'use strict';
;(function () {
    window.requestStudent = requestStudent;

    function requestStudent(classId, classIdVale, fun) {
        var students = null;
        var mXmlHttp = createXMlHttp();
        if (mXmlHttp != null) {
            mXmlHttp.open("GET", url_student, true);
            mXmlHttp.setRequestHeader(classId, classIdVale);
            mXmlHttp.withCredentials = true;
            mXmlHttp.onreadystatechange = function () {
                if (mXmlHttp.status == 200 && mXmlHttp.readyState == 4) {
                    var date = JSON.parse(mXmlHttp.responseText);
                    if (date.code == 6001) {
                        students = date.students;
                        fun(students);
                        window.parent.sudents = students;
                    }
                    if (date.code == 7001) {
                        alert(date.message);
                    }

                } else {
                    console.log("8989")
                }
            };
            mXmlHttp.send();
        } else {
            alert(mXmlHttp);
        }
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
})()
