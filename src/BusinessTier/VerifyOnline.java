package BusinessTier;
/*
作者：ypl
创建时间：2018/8/28-19:04-2018
通过用户cookie判断用户登陆没；
*/

import MyException.MyException;
import com.sun.deploy.net.HttpRequest;
import until.Constant;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class VerifyOnline {
    private HttpRequest httpRequest;
    private String cookie;

    public String getCookie() {
        return cookie;
    }

    public VerifyOnline() {

    }

    public void verifyOnlineByCookie(HttpServletRequest httpRequest) throws Exception {
        cookie = getCookieValue(Constant.COOKIE_KEY, httpRequest);
        if (cookie == null) throw new MyException("用户没有登陆");
        UseServlet useServlet = new UseServlet();
        if (useServlet.findByCookie(cookie) == null) throw new MyException("用户不存在");
        //没有抛出异常，说明用户注册了，并且登陆
    }

    private String getCookieValue(String key, HttpServletRequest request) throws Exception {
        if (key == null || "".equals(key)) {
            throw new Exception("cookie of key is null");
        }
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            if (key.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
