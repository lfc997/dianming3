package register;
/*
用户:ypl
TIME:13:48,2018/9/1
*/

import until.Constant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/loginout")
public class LoginOutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");
        Cookie cookie = null;
        try {
            cookie = getCookieValue(Constant.COOKIE_KEY, request);
            if (cookie == null) {
                //根本没有登陆
                response.sendRedirect("/dianming2/Login.html");
            }
            cookie.setMaxAge(0);
            cookie.setValue(null);
            //重定向与转发
            response.addCookie(cookie);
            response.sendRedirect("/dianming2/loginout.html");
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put(Constant.CODE, 8001);
//            jsonObject.put(Constant.MESSAGE, "退出成功");
//            response.getWriter().print();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //转发
//        request.getRequestDispatcher().forward(, );
    }

    private Cookie getCookieValue(String key, HttpServletRequest request) throws Exception {
        if (key == null || "".equals(key)) {
            throw new Exception("cookie of key is null");
        }
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            if (key.equals(cookie.getName())) {
                return cookie;
            }
        }
        return null;
    }
}