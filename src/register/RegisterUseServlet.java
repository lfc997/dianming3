package register;
/*
用户:ypl
TIME:21:25,2018/8/26
*/

import BusinessTier.UseServlet;
import MyException.MyException;
import UUId.myUUID;
import javabean.Use;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet("/register")
public class RegisterUseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        UseServlet useServlet = new UseServlet();
        Use use = new Use();
        //通过手机号码或者邮箱做唯一标识符；
        System.out.println(request.getParameter("name"));
        String name = request.getParameter("phone");
        String email = request.getParameter("email");
        System.out.println(name);
        System.out.println(email);
        long uid = 0;
        try {
            uid = myUUID.getUUid(name);

        } catch (Exception e) {
            try {
                uid = myUUID.getUUid(email);
            } catch (Exception e1) {
                //注册失败；
                uid = 0;
            }
        }
        JSONObject jsonObject = new JSONObject();
        if (uid != 0) {
            try {
                use.setUid(uid + "");
                use.setName(request.getParameter("name"));
                use.setPhone(request.getParameter("phone"));
                use.setEmail(request.getParameter("email"));
                use.setPassword(request.getParameter("password"));
                use.setHeadUrl(request.getParameter("headUrl"));
                use.setDate(new Date());
                if (useServlet.register(use)) {//添加用户
                    jsonObject.put("code", 1001);
                    jsonObject.put("message", "注册成功");
                    response.getWriter().print(jsonObject.toString());
                } else {
                    jsonObject.put("code", 1002);
                    jsonObject.put("message", "注册失败");
                    response.getWriter().print(jsonObject.toString());
                }
            } catch (MyException e2) {
                jsonObject.put("code", 1002);
                jsonObject.put("message", e2.getMessage());
                response.getWriter().print(jsonObject.toString());
            }
        } else {
            jsonObject.put("code", 1003);
            jsonObject.put("message", "注册失败");
            response.getWriter().print(jsonObject.toString());
        }

    }
}