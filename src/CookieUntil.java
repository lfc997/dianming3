/*
作者：ypl
创建时间：2018/9/5-17:54-2018
*/

public class CookieUntil {
    private String cookie;

    //    ID=1589111347; Max-Age=3600; Expires=Wed, 05-Sep-2018 09:42:35 GMT;
    //    name=15708989332; Max-Age=3600; Expires=Wed, 05-Sep-2018 09:42:35 GMT;
    //    JSESSIONID=127A07EC03F7299AAF0EA33E369ECE33; Path=/dianming2; HttpOnly;


    public CookieUntil(String cookie) {
        this.cookie = cookie;

    }

    public String getValue(String key) {
        String[] a = this.cookie.split(";");
        for (String b : a) {
            if (b.indexOf(key + "=") != -1) {
                return b.substring(key.length() + 1);
            }
        }
        return null;
    }


}