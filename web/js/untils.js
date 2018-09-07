/**
 Created by ypl on 2018/9/1;
 */
'use strict';

<!--请求头部数据-->
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