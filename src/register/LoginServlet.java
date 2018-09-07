package register;
/*
用户:ypl
TIME:9:50,2018/8/27
*/

import BusinessTier.UseServlet;
import MyException.MyException;
import javabean.Use;
import net.sf.json.JSONObject;
import until.Constant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//封装use
        String origin = request.getHeader("Origin");
        if (origin != null && !"".equals(origin)) {
            System.out.println("ypl");
            response.addHeader("Access-Control-Allow-Origin", origin);
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        Use use = new Use();
        String name = request.getParameter("name");
        System.out.println(name);
        use.setName(name);
        use.setPassword(request.getParameter("password"));
        UseServlet useServlet = new UseServlet();
        JSONObject jsonObject = new JSONObject();
        //
        System.out.println(request.getServletContext().getRealPath("/") + "realPath");
        //
        try {
            Use myUse = useServlet.login(use);
            System.out.println(myUse == null);
            if (myUse != null) {
                jsonObject.put("code", 2001);
                jsonObject.put("message", "登陆成功");
                Cookie cookie = new Cookie(Constant.COOKIE_KEY, myUse.getUid() + "");
//                是120分钟
                cookie.setMaxAge(60 * 60);
                cookie.setComment("用户的唯一标识符");
                //
//                HttpSession httpSession = request.getSession();
//                httpSession.setAttribute("", );
                String useName = myUse.getPhone() == null || "".equals(myUse.getPhone()) ?
                        (myUse.getEmail() == null || "".equals(myUse.getEmail()) ? null : myUse.getEmail())
                        : myUse.getPhone();
                Cookie cookie1 = new Cookie(Constant.NAME, java.net.URLEncoder.encode(useName, "UTF-8"));
                cookie.setDomain(request.getServerName());
                cookie.setPath(request.getContextPath());
                cookie1.setDomain(request.getServerName());
                cookie1.setPath(request.getContextPath());
                cookie1.setMaxAge(60 * 60 * 24);
                response.addCookie(cookie);
                response.addCookie(cookie1);

                HttpSession httpSession = request.getSession();
                httpSession.setAttribute(Constant.COOKIE_KEY, myUse.getUid());
                httpSession.setAttribute("use", myUse);
            } else {
                jsonObject.put("code", 2002);
                jsonObject.put("message", "登陆失败");
            }
        } catch (MyException e) {
            e.printStackTrace();
            jsonObject.put("code", 2003);
            jsonObject.put("message", e.getMessage());
        } finally {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print(jsonObject.toString());

        }

    }
}