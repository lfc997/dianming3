/*
作者：ypl
创建时间：2018/8/26-22:34-2018
*/

import MyException.MyException;
import StudentClassDao.StudentClassDao;
import javabean.Student;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Test1 {
    public static void main(String[] args) {
//        UseDao useDao = new UseDao();
//        try {
//            Use use = useDao.findByUid(1234123);
//            System.out.println(use == null);
//            if (use != null) {
//                System.out.println(use.toString());
//            }
//        } catch (MyException e) {
//            System.out.println("不存在");
//        }
//        UseDao useDao1 = new UseDao();
//        try {
//            System.out.println(useDao1.findByName("yang").toString());
//            System.out.println(useDao1.findByEmail("1306961052").toString());
//        } catch (MyException e) {
//            System.out.println("不存在");
//        }
//    }
//        Use use = new Use();
//        use.setUid("杨沛霖".hashCode());
//        try {
//            use.setName(new String("杨沛霖".getBytes(), "utf-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        use.setPhone("15708989110");
//        use.setEmail("1306961052");
//        use.setPassword("123224");
//        use.setHeadUrl("http://");
//        use.setDate(new Date());
//        try {
//            System.out.println(useDao1.add(use));
//        } catch (MyException e) {
//            e.printStackTrace();
//        }
//        OperationSqlImpl operationSql = new OperationSqlImpl();
//
//        System.out.println(operationSql.getConnection() == null);
//        Student student = new Student();
//        student.setStudentId("201524070143");
//        student.setLate(80);
//        try {
//            studentClassDao.insertStudentText(student, "stu");
////        } catch (MyException e) {
////
////            e.printStackTrace();
////        }
//        try {
//            List<Student> students = studentClassDao.getBySql("stu");
//            for (Student student : students) {
//                System.out.print("id:" + student.getStudentId() + "\t");
//                System.out.println("late:" + student.getLate());
//            }
//        } catch (MyException e) {
//            e.printStackTrace();
//        }
        System.out.println("15708989110".hashCode());
        System.out.println("1306961052".hashCode());
        String text = "[{\"studentId\":\"201524070103\",\"late\":0,\"truancy\":0,\"leave\":1},{\"studentId\":\"201524070104\",\"late\":1,\"truancy\":0,\"leave\":0},{\"studentId\":\"201524070106\",\"late\":1,\"truancy\":0,\"leave\":0},{\"studentId\":\"201524070108\",\"late\":0,\"truancy\":1,\"leave\":0}]\n";
        JSONArray jsonArray = JSONArray.fromObject(text);
//        List<Student> mStudents = JSONArray.fromObject(text);
//        System.out.println(mStudents.size());
//        for (int i = 0; i < mStudents.size(); i++) {
//            Student student = mStudents.get(i);
//            String studentId = student.getStudentId();
//            System.out.println(studentId);
//        }


//
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = (JSONObject) jsonArray.get(i);
            Student student = new Student();
            student.setStudentId(object.getString("studentId"));
            student.setLate(object.getInt("late"));
            student.setTruancy(object.getInt("truancy"));
            student.setLeave(object.getInt("leave"));
            students.add(student);
            object.getString("studentId");
        }
        System.out.println(students.size());
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            String studentId = student.getStudentId();
            System.out.print(studentId + "\t");
            System.out.print("late:" + student.getLate() + "\t");
            System.out.print("truancy" + student.getTruancy() + "\t");
            System.out.println("leave" + student.getLeave() + "\t");
        }
        StudentClassDao studentClassDao = new StudentClassDao();
        try {
            studentClassDao.insertStudentAll(students, "stu");
        } catch (MyException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
