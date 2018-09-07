/*
作者：ypl
创建时间：2018/9/2-17:10-2018
*/

import java.io.UnsupportedEncodingException;

public class Test2 {
    public static void main(String[] args) {
        try {
            System.out.println(java.net.URLEncoder.encode("测试","UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
