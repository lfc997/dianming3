package register;
/*
用户:ypl
TIME:18:45,2018/8/28
*/

import BusinessTier.VerifyOnline;
import MyException.MyException;
import StudentClassDao.StudentClassDao;
import classDao.ClassDao;
import javabean.ClassInformation;
import javabean.Student;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import until.Constant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

@WebServlet("/student")
public class studentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        doOptions(request, response);
        System.out.println("student");
        response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.setContentType("text/html;charset=utf-8");
        //验证用户在线：
        //判断用户的cookie:是否村子啊
        VerifyOnline verifyOnline = new VerifyOnline();
        System.out.println("classId:" + request.getHeader("classId"));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mess", "请求到位");
        try {
            verifyOnline.verifyOnlineByCookie(request);
            String classId = request.getHeader("classId");
            if (classId == null || "".equals(classId)) {
                System.out.println("班级查找出错");
                jsonObject.put(Constant.CODE, 5005);
                jsonObject.put(Constant.MESSAGE, "classId不存在");
                response.getWriter().print(jsonObject.toString());
                return;
            }
            //通过classId；拿到班级的classSqlName;
            ClassDao classDao = new ClassDao();
            ClassInformation classInformation = classDao.getByClassId(classId);
            if (classInformation == null) {
                jsonObject.put(Constant.CODE, 5002);
                jsonObject.put(Constant.MESSAGE, "查询班级失败");
                response.getWriter().print(jsonObject.toString());
                return;
            }
            String sqlName = classInformation.getClassSqlName();
            if (sqlName == null) {
                jsonObject.put(Constant.CODE, 5002);
                jsonObject.put(Constant.MESSAGE, "该班级根本不存在");
                response.getWriter().print(jsonObject.toString());
                return;
            }
            //保存sqlName到会话中
            request.getSession().setAttribute(Constant.TABLE_NAME, sqlName);
            //通过班级的数据库名字拿到所有的数据，返回json的格式
            StudentClassDao studentClassDao = new StudentClassDao();
            System.out.println(sqlName);
            List<Student> students = studentClassDao.getBySql(sqlName);
            if (students == null || students.size() == 0) throw new MyException("获取学生数据错误");
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < students.size(); i++) {
                Student student = students.get(i);
                JSONObject stu = new JSONObject();
                stu.put("studentId", student.getStudentId());
                stu.put("studentName", student.getStudentName());
                stu.put("sex", student.getSex());
                stu.put("late", student.getLate());//迟到
                stu.put("leave", student.getLeave());//早退
                stu.put("truancy", student.getTruancy());
                jsonArray.add(i, stu);
            }
            jsonObject.put(Constant.CODE, 6001);
            jsonObject.put(Constant.MESSAGE, "获取班级中的学生信息");
            jsonObject.put("students", jsonArray);
            System.out.println(jsonObject.toString());


        } catch (Exception e) {
            e.printStackTrace();
//            //抛出了异常说明用户：不在线或者没有注册
            if (e instanceof MyException) {
                jsonObject.put(Constant.CODE, 7001);
                jsonObject.put(Constant.MESSAGE, "用户不在线");
                response.getWriter().print(jsonObject.toString());
                return;
            }
        }

        response.getWriter().print(jsonObject.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("post");
    }

    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 指定允许其他域名访问
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:63342");
        // 响应类型
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        // 响应头设置
        Enumeration<String> stringEnumeration = request.getHeaderNames();
        StringBuffer stringBuffer = new StringBuffer();
        while (stringEnumeration.hasMoreElements()) {
            System.out.println(stringEnumeration.nextElement());
            stringBuffer.append(stringEnumeration.nextElement());
        }
        System.out.println(request.getHeader("Access-Control-Request-Headers"));
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        response.setStatus(204);
    }
}