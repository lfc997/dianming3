package register;
/*
用户:ypl
TIME:0:34,2018/9/2
*/

import net.sf.json.JSONObject;
import until.Constant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/a")
public class A extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        System.out.println("get a");
        System.out.println("student");
        String origin = request.getHeader("Origin");

        response.addHeader("Access-Control-Allow-Origin", "http://localhost:63342");
        String headrs = request.getHeader("Access-Control-Request-Headers");
        System.out.println(headrs);
        response.addHeader("Access-Control-Request-Headers", headrs);
        response.addHeader("Access-Control-Allow-Methods", "*");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.setContentType("text/html;charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constant.CODE, 6001);
        response.getWriter().print(jsonObject.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("post A");

    }
}