package MyException;
/*
作者：ypl
创建时间：2018/8/26-22:13-2018
*/

public class MyException extends Exception {
    public MyException(String message) {
        super(message);
    }
    public MyException(String message, Throwable cause) {
        super(message, cause);
    }
}
