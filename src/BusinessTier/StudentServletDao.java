package BusinessTier;
/*
作者：ypl
创建时间：2018/8/31-10:06-2018
*/

import MyException.MyException;
import StudentClassDao.StudentClassDao;
import javabean.Student;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentServletDao {
    //向数据库中插入数据
    public void insert(String s, String table) throws MyException {
        if ("".equals(s) || s == null) {
            throw new MyException("字符传入有误" + this.getClass().getName());
        }
        try {
            JSONArray jsonArray = JSONArray.fromObject(s);
            if (jsonArray.size() < 0) {
                throw new MyException("解析失败");
            }
            insert(jsonArray, table);
        } catch (Exception e) {
            throw new MyException("解析数据失败" + this.getClass().getName());
        }


    }

    public void insert(JSONArray s, String table) throws MyException, SQLException {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < s.size(); i++) {
            Student student = new Student();
            JSONObject jsonObject = (JSONObject) s.get(i);
            student.setStudentId(jsonObject.getString("studentId"));
            student.setLate(jsonObject.getInt("late"));
            student.setTruancy(jsonObject.getInt("truancy"));
            student.setLeave(jsonObject.getInt("leave"));
            students.add(student);
        }
        //已经转换成javaBean;
        StudentClassDao studentClassDao = new StudentClassDao();
        studentClassDao.insertStudentAll(students, table);


    }


    //向数据库中获得学生的数据
}
