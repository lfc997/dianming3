package sql;
/*
作者：ypl
创建时间：2018/8/26-21:55-2018
*/

import java.sql.Connection;

public interface OperationSql {
    Connection getConnection();

    Object findByName(String name);

    Object findByEmail(String email);

    Object findByUid(String uid);


}
