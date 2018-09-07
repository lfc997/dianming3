package uploadServlet;

import MyException.MyException;
import StudentClassDao.StudentClassDao;
import UUId.myUUID;
import classDao.ClassDao;
import com.fc.ExcelParseUtils.ExcelParseUtils;
import javabean.ClassInformation;
import javabean.Student;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sql.OperationSqlImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class UploadService {

    private String path;

    public UploadService(String path) {
        this.path = path;
    }


    public void saveData(String className, String cookie) throws Exception {
        JSONArray list = ExcelParseUtils.parse(path);
        System.out.println(list.size());
        System.out.println(list.toString());
        //首先：创建一个数据库，数据库名字：时间+path;
        //创建数据名字
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            JSONObject jsonObject = (JSONObject) list.get(i);
            Student student = new Student();
            if (jsonObject.getString("学号") != null||!"".equals(jsonObject.getString("学号"))) {
                student.setStudentId(jsonObject.getString("学号"));
                student.setStudentName(jsonObject.getString("姓名"));
                student.setSex((String) jsonObject.get("男"));
                students.add(student);
            }
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-hh dd-mm");
        Date date = new Date();
        String dateTime = dateFormat.format(date);
        System.out.println(dateTime);
        String tableName = dateTime + className;
        System.out.println(tableName);
        OperationSqlImpl operationSql = new OperationSqlImpl();
        if (operationSql.createTable(tableName, null)) {
            System.out.println("创建成功");
            //然后向该表单插入数据；
            StudentClassDao studentClassDao = new StudentClassDao();
            System.out.println(students.size());
            studentClassDao.insertStudent(students, tableName);
            //cookie/classId/className/classNumber/classSqlName;
            ClassDao classDao = new ClassDao();
            ClassInformation classInformation = new ClassInformation();
            classInformation.setCookie(cookie);
            classInformation.setClassNumber(students.size());
            classInformation.setClassSqlName(tableName);
            classInformation.setClassName(className);
            classInformation.setClassId(myUUID.getUUid(className) + "");
            List<ClassInformation> classInformations = new ArrayList<>();
            classInformations.add(classInformation);
            classDao.addClasses(classInformations);
            //最后通过cookie插入到classes

        } else {
            throw new MyException("数据库创建失败");
        }


    }


}
