package UUId;
/*
作者：ypl
创建时间：2018/8/27-10:01-2018
*/

public class myUUID {
    public static long getUUid(String u) throws Exception {
        if (u == null || "".equals(u)) {
            throw new Exception("字符为空");
        }
        return u.hashCode();
    }
}
