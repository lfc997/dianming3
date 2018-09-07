package classDao;
/*
作者：ypl
创建时间：2018/8/27-18:41-2018
*/

import MyException.MyException;
import javabean.ClassInformation;
import sql.OperationSqlImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassDao {
    public List<ClassInformation> getClassInformation(String cookie) throws MyException {
        List<ClassInformation> classesInformation = new ArrayList<>();
        //获得数据库；
        Connection connection = null;
        System.out.println("www");
        OperationSqlImpl operationSql = new OperationSqlImpl();
        PreparedStatement preparedStatement = null;
        String sql = "SELECT * FROM `classes` where uid=?";
        ResultSet resultSet = null;
        try {
            connection = operationSql.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cookie);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ClassInformation classInformation = new ClassInformation();
                classInformation.setCookie(resultSet.getString(1));
                classInformation.setClassId(resultSet.getString(2));
                classInformation.setClassName(resultSet.getString(3));
                classInformation.setClassNumber(resultSet.getInt(4));
                classInformation.setClassSqlName(resultSet.getString(5));
                classesInformation.add(classInformation);
            }
//            if (classesInformation.size() == 0) {
//                throw new MyException("数据没有查询到结果");
//            } else {
//                System.out.println("classes查询成功");
//            }
        } catch (SQLException e) {
            System.out.println("classes数据库查询错误");
            throw new MyException("数据库查询错误");
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        System.out.println("数据获取成功");
        return classesInformation;
    }

    public void addClasses(List<ClassInformation> classesInformation) throws MyException {
        if (classesInformation == null || classesInformation.size() == 0) {
            throw new MyException("插入班级数据为null");
        }
        //获得链接数据库：
        Connection connection = null;
        OperationSqlImpl operationSql = new OperationSqlImpl();
        String sql = "insert into `classes` values(?,?,?,?,?)";
        PreparedStatement preparedStatement = null;
        try {
            connection = operationSql.getConnection();
            //开启事务
            connection.setAutoCommit(false);
            for (int i = 0; i < classesInformation.size(); i++) {
                ClassInformation classInformation = classesInformation.get(i);
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, classInformation.getCookie());
                preparedStatement.setString(2, classInformation.getClassId());
                preparedStatement.setString(3, classInformation.getClassName());
                preparedStatement.setInt(4, classInformation.getClassNumber());
                preparedStatement.setString(5, classInformation.getClassSqlName());
                preparedStatement.executeUpdate();
            }
            connection.commit();
            //提交事务
        } catch (SQLException e) {
            //回滚事务
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        } finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new MyException("数据库中保存出现问题");
            }
        }
        //通过链接获取预处理statement;
    }

    //通过cookie查找该老师管理多少个班
    public int findByCookie(long cookie) throws MyException {
        //打开链接
        OperationSqlImpl operationSql = new OperationSqlImpl();
        Connection connection = null;
        int number = -1;
        PreparedStatement preparedStatement = null;
        String sql = "select  COUNT(*) as number FROM `classes`";
        ResultSet resultSet = null;
        try {
            connection = operationSql.getConnection();
            //获取预处理面板：
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                number = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new MyException("数据查询出错，类名：" + this.getClass().getName());
        }
        return number;
    }

    public ClassInformation getByClassId(String id) throws Exception {
        ClassInformation classInformation = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        OperationSqlImpl operationSql = new OperationSqlImpl();
        String sql = "select * from `classes` where classid=?";
        ResultSet resultSet = null;
        try {
            connection = operationSql.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                classInformation = new ClassInformation();
                classInformation.setCookie(resultSet.getString(1));
                classInformation.setClassId(resultSet.getString(2));
                classInformation.setClassName(resultSet.getString(3));
                classInformation.setClassNumber(resultSet.getInt(4));
                classInformation.setClassSqlName(resultSet.getString(5));
            }
        } catch (SQLException e) {
            throw new Exception("查询数据库出现问题————" + this.getClass().getName());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();

                }
                if (preparedStatement != null) {

                    preparedStatement.close();
                }
                if (connection != null) {

                    connection.close();
                }
            } catch (SQLException e) {
                throw new MyException("关闭数据库出现问题" + this.getClass().getName());
            }
        }
        return classInformation;
    }



}
