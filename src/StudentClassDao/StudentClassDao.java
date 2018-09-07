package StudentClassDao;
/*
作者：ypl
创建时间：2018/8/29-10:52-2018
*/

import MyException.MyException;
import javabean.Student;
import sql.OperationSqlImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentClassDao {
    public List<Student> getBySql(String sqlName) throws MyException {
        List<Student> students = new ArrayList<>();
        //打开数据库
        //获得链接
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        OperationSqlImpl operationSql = new OperationSqlImpl();
        String sql = "select * from `" +
                sqlName + "`";
        try {
            connection = operationSql.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getString(1));
                student.setStudentName(resultSet.getString(2));
                student.setSex(resultSet.getString(3));
                student.setLate(resultSet.getInt(4));
                student.setLeave(resultSet.getInt(5));
                student.setTruancy(resultSet.getInt(6));
                student.setText(resultSet.getString(7));
                students.add(student);
            }
        } catch (SQLException e) {
            throw new MyException("数据查询问题" + this.getClass().getName());

        }

        return students;
    }

    //test通过
    public void insertStudentText(Student student, String table) throws MyException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        OperationSqlImpl operationSql = new OperationSqlImpl();
        String sql = "update  `" +
                table + "` set late=late+?,`leave`=`leave`+?,truancy=truancy+? where studentId=?";
        try {
            connection = operationSql.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            System.out.println(student.getLate());
            preparedStatement.setInt(1, student.getLate());
            preparedStatement.setInt(2, student.getLeave());
            preparedStatement.setInt(3, student.getTruancy());
            preparedStatement.setString(4, student.getStudentId());
            System.out.println(student.getStudentName());
            int a = preparedStatement.executeUpdate();
            if (a < 0) {
                throw new MyException("更新学生错误");
            }
            System.out.println("跟新成功");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MyException("更新学生错误");
        }
    }

    public void insertStudentAll(List<Student> students, String table) throws MyException, SQLException {
        //开启事务
        if (students == null) {
            throw new MyException(this.getClass().getName() + "为空");
        }
        if (students.size() == 0) {
            throw new MyException("没有数据可以更新");
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        OperationSqlImpl operationSql = new OperationSqlImpl();
        String sql = "update  `" +
                table + "` set late=late+?,`leave`=`leave`+?,truancy=truancy+? where studentId=?";
        try {

            connection = operationSql.getConnection();

            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(sql);
            for (Student student : students) {
                preparedStatement.setInt(1, student.getLate());
                preparedStatement.setInt(2, student.getLeave());
                preparedStatement.setInt(3, student.getTruancy());
                preparedStatement.setString(4, student.getStudentId());
                preparedStatement.addBatch();//批处理
            }
            int a[] = preparedStatement.executeBatch();//批执行
            if (a.length < 0) {
                throw new MyException("更新学生错误");
            }
            //提交事务
            connection.commit();
            System.out.println("跟新数据库成功" + this.getClass().getName());
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            throw new MyException("更新学生错误");
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }


    //像数据库中插入数据:初始化数据库数据；
    public void insertStudent(List<Student> students, String table) throws MyException {
        //由于数据较多，因此插入事务。避免数据重复插入的情况
        //获取数据库链接；
        //获得链接板
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        OperationSqlImpl operationSql = new OperationSqlImpl();
        String sql = "insert  into `" + table + "` values (?,?,?,?,?,?,?)";
        try {
//开启事务
            connection = operationSql.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < students.size(); i++) {
                Student student = students.get(i);
                preparedStatement.setString(1, student.getStudentId());
                preparedStatement.setString(2, student.getStudentName());
                preparedStatement.setString(3, student.getSex());
                preparedStatement.setInt(4, 0);
                preparedStatement.setInt(5, 0);
                preparedStatement.setInt(6, 0);
                preparedStatement.setString(7, null);
                preparedStatement.addBatch();
            }
            int[] ints = preparedStatement.executeBatch();
            //执行事务
            System.out.println(ints.length);
            for (int j = 0; j < ints.length; j++) {
                System.out.println(ints[j]);
            }
            connection.commit();
        } catch (SQLException e) {
//回滚事务
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
                throw new MyException("插入数据失败");
            }
        }

    }

}
