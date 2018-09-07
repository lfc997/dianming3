package register;
/*
用户:ypl
TIME:16:28,2018/8/27
*/

import BusinessTier.UseServlet;
import javabean.Use;
import net.sf.json.JSONObject;
import until.Constant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //业务逻辑：
        //1.判断用户的cooki是否存在；
        //2不存在：直接跳转到用户的登陆界面：code:3002,message:该用户还没有登陆
        //存在。在通过cookkie值直接查询
        //带cookie的跨域请求
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        HttpSession httpSession = request.getSession();
        try {
            String cookieValue = getCookieValue(Constant.COOKIE_KEY, request);
            System.out.println("id:" + cookieValue);
            String id = (String) httpSession.getAttribute(Constant.COOKIE_KEY);
            if (cookieValue == null) {
                //没有从浏览器中拿到对应的cookie:
                // 需要转到登陆页面；
                jsonObject.put(Constant.CODE, 4002);
                jsonObject.put(Constant.MESSAGE, "用户没有登陆");
            }
            if (id == null) {
                //之前的会话结束
            }
            //判断cookie是否在数据库存在
            UseServlet useServlet = new UseServlet();
            Use use = useServlet.findByCookie(cookieValue);
            System.out.println("useuid" + use.getUid());
            if (use == null) {
                jsonObject.put(Constant.CODE, 4004);
                jsonObject.put(Constant.MESSAGE, "用户根本没有注册");
            }
            System.out.println(use.getUid() + "uid");
            String text = useServlet.getHomeString(cookieValue);
            jsonObject.put(Constant.CODE, 4001);
            jsonObject.put(Constant.MESSAGE, "用户注册，获得数据");
            //封装头部信息
            JSONObject head = new JSONObject();
            head.put(Constant.HEAD_URL, use.getHeadUrl());
            //处理下逻辑，如有用户的usename不存在，则取用户的手机号码。若手机号码不存在，则取邮箱；
            String name = "";
            if (use.getName() == null && "".equals(use.getName())) {
                if (use.getPhone() == null && "".equals(use.getPhone())) {
                    name = use.getEmail();
                } else {
                    name = use.getPhone();
                }
            } else {
                name = use.getName();
            }
            head.put(Constant.NAME, name);
            head.put(Constant.SIGNATURE, use.getSignature());
            jsonObject.put(Constant.CLASS_HEAD, head);
            jsonObject.put(Constant.CLASS_USE, text);
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put(Constant.CODE, 4005);
            jsonObject.put(Constant.MESSAGE, "不知名原因" + e.getMessage());
        } finally {
            response.getWriter().print(jsonObject.toString());
        }
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