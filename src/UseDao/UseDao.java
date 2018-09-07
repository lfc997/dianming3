package UseDao;
/*
作者：ypl
创建时间：2018/8/26-21:24-2018
*/

import MyException.MyException;
import javabean.Use;
import log.myLog;
import sql.OperationSqlImpl;

public class UseDao {
    private static String TABLE = "use";

    //通过用户名查找
    public Use findByName(String name) throws MyException {
        Use use = null;
        OperationSqlImpl operationSql = new OperationSqlImpl();
        try {
            use = operationSql.findByName("name", name, TABLE);
        } catch (MyException e) {
            throw new MyException("用户名不存在");
        }
        return use;
    }

    public Use findByEmail(String email) throws MyException {
        Use use = null;
        OperationSqlImpl operationSql = new OperationSqlImpl();
        try {
            use = operationSql.findByName("email", email, TABLE);
        } catch (MyException e) {
            throw new MyException("邮箱不存在");
        }
        return use;
    }

    public Use findByPhone(String phone) throws MyException {
        Use use = null;
        OperationSqlImpl operationSql = new OperationSqlImpl();
        try {
            use = operationSql.findByName("phone", phone, TABLE);
        } catch (MyException e) {
            throw new MyException("邮箱不存在");
        }
        return use;
    }

    public Use findByUid(String uid) throws MyException {
        Use use = null;
        OperationSqlImpl operationSql = new OperationSqlImpl();
        try {
            use = operationSql.findByName("uid", uid, TABLE);
        } catch (MyException e) {
            myLog.log("uid 不存在");
            throw new MyException("uid不存在");
        }
        return use;
    }

    public boolean add(Use use) throws MyException {
        boolean a = false;
        OperationSqlImpl operationSql = new OperationSqlImpl();
        try {
            operationSql.addUse(use, TABLE);
        } catch (MyException e) {
            throw new MyException("用户添加一列失败");
        }
        a = true;
        return a;
    }
}
