package log;
/*
作者：ypl
创建时间：2018/8/26-21:42-2018
*/

public class myLog {
    public static boolean isXianshi = false;

    public static void log(String name, String log) {
        if (isXianshi) {
            System.out.println("name:" + name + "\t values:" + log);
        }
    }

    public static void log(String log) {
        if (isXianshi) {
            System.out.println("name:myJava" + "\t values:" + log);
        }
    }
}
