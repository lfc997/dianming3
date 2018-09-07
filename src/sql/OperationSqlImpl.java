package sql;
/*
作者：ypl
创建时间：2018/8/26-21:36-2018
*/

import MyException.MyException;
import javabean.Use;
import log.myLog;

import java.sql.*;

public class OperationSqlImpl {
    //1.获取数据库的链接：
    public Connection getConnection() {
        //四大参数
        Connection connection = null;
        String url = "jdbc:mysql://localhost:3306/dianming?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
        //        //加载驱动
        String name = "root";
        String password = "YPL123456ypl";
        try {
            Class.forName("com.mysql.jdbc.Driver");
//            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(url, name, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            myLog.log("数据库驱动没有加载");
            connection = null;
        }
        return connection;
    }

    //创建一个数据库表单
    public boolean createTable(String table, String filedNameJson) throws MyException {
        //以后把sql 语句中改写成json中的数据格式的数据，从而达到了解耦
        //获取一个连接；
        boolean isSc = true;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = "CREATE TABLE `" + table + "` (\n" +
                "`studentId`  varchar(100) DEFAULT NULL,\n" +
                "`name` varchar(255) DEFAULT NULL,\n" +
                "`sex` varchar(255) DEFAULT NULL,\n" +
                "`late` tinyint(10) unsigned zerofill DEFAULT NULL,\n" +
                "`leave` tinyint(10) unsigned zerofill DEFAULT NULL,\n" +
                "`Truancy` tinyint(10) unsigned zerofill DEFAULT NULL,\n" +
                "`text` text\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            isSc = false;
            throw new MyException("创建数据中失败");
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new MyException("关闭数据库错误");
            }
        }
        return isSc;
    }

    public Connection getConnection(boolean pool) {
        Connection connection = null;
        if (pool) {
//配置连接池
            //后面书写


        } else {
            connection = getConnection();
        }

        return connection;
    }

    //2：
//    public
    public Use findByName(String name, String value, String table) throws MyException {

        Connection connection = getConnection();
        Use use = null;
        if (connection != null) {
            PreparedStatement preparedStatement = null;
            String sql = "select * from `" + table + "` where " + name + "=?";
            ResultSet resultSet = null;
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, value);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    use = new Use();
                    use.setUid(resultSet.getString(1));
                    use.setName(resultSet.getString(2));
                    use.setPhone(resultSet.getString(3));
                    use.setEmail(resultSet.getString(4));
                    use.setPassword(resultSet.getString(5));
                    use.setEmail(resultSet.getString(6));
                    use.setDate(resultSet.getDate(7));
                    use.setSignature(resultSet.getString(8));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                myLog.log("数据库问题,类名：" + this.getClass().getName());
                throw new MyException("通过名字查找数据错误");
            } finally {
                try {
                    resultSet.close();
                    preparedStatement.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return use;
    }

//    public Use findByName(String name, long value, String table) throws MyException {
//
//        Connection connection = getConnection();
//        Use use = null;
//        if (connection != null) {
//            PreparedStatement preparedStatement = null;
//            String sql = "select * from `use` " + "where " + name + "=?";
//            ResultSet resultSet = null;
//            try {
//                preparedStatement = connection.prepareStatement(sql);
//                preparedStatement.setLong(1, value);
//                resultSet = preparedStatement.executeQuery();
//                while (resultSet.next()) {
//                    use = new Use();
//                    use.setUid(resultSet.getInt(1));
//                    use.setName(resultSet.getString(2));
//                    use.setPhone(resultSet.getString(3));
//                    use.setEmail(resultSet.getString(4));
//                    use.setPassword(resultSet.getString(5));
//                    use.setEmail(resultSet.getString(6));
//                    use.setDate(resultSet.getDate(7));
//                }
//                if (use == null) throw new MyException("用户不存在");
//            } catch (SQLException e) {
//                e.printStackTrace();
//                myLog.log("数据库问题,类名：" + this.getClass().getName());
//                throw new MyException("通过名字查找数据错误");
//            } finally {
//                try {
//                    resultSet.close();
//                    preparedStatement.close();
//                    connection.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return use;
//    }

    public void addUse(Use use, String table) throws MyException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        myLog.log(use.toString());
//        insert into `use` values
        String sql = "insert into `" + table + "` values(?,?,?,?,?,?,?,?)";
        try {
            connection = getConnection();
            System.out.println(connection == null);
            preparedStatement = connection.prepareStatement(sql);
            //开启事务
            connection.setAutoCommit(false);
            //逻辑代码
            preparedStatement.setString(1, use.getUid());
            preparedStatement.setString(2, use.getName());
            preparedStatement.setString(3, use.getPhone());
            preparedStatement.setString(4, use.getEmail());
            preparedStatement.setString(5, use.getPassword());
            preparedStatement.setString(6, use.getHeadUrl());
            preparedStatement.setDate(7, new Date(use.getDate().getTime()));
            preparedStatement.setString(8, use.getSignature());
            int a = preparedStatement.executeUpdate();
            myLog.log(a + "");
            if (a < 0) {
                throw new MyException("添加数据失败");
            }
            ;
            //提交事务
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new MyException("添加数据行出错");
        }
    }
}
