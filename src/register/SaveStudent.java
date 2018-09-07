package register;
/*
用户:ypl
TIME:9:46,2018/8/31
*/

import BusinessTier.StudentServletDao;
import MyException.MyException;
import net.sf.json.JSONObject;
import until.Constant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/saveStudent")
public class SaveStudent extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");
        String text = request.getParameter("studentDate");
        String tableName = (String) request.getSession().getAttribute(Constant.TABLE_NAME);
        System.out.println(text);
        StudentServletDao studentServletDao = new StudentServletDao();
        JSONObject jsonObject = new JSONObject();
        try {
            studentServletDao.insert(text, tableName);
            jsonObject.put(Constant.CODE, 7001);
            jsonObject.put(Constant.MESSAGE, "学生数据库跟新成功");
        } catch (MyException e) {
            e.printStackTrace();
            jsonObject.put(Constant.CODE, 7002);
            jsonObject.put(Constant.MESSAGE, "学生数据库跟新失败,原因：" + e.getMessage());
        } finally {
            response.getWriter().print(jsonObject.toString());
        }
    }
}