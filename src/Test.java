/*
作者：ypl
创建时间：2018/9/4-21:19-2018
*/

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test {
    public static void main(String[] args) {
//        try {
//            FileInputStream fileInputStream = new FileInputStream(new File("D:\\work\\dianming3\\data"));
//            byte[] bytes = new byte[1024];
//            StringBuffer stringBuffer = new StringBuffer();
//            while (fileInputStream.read(bytes) != -1) {
//                stringBuffer.append(bytes);
//            }
//            System.out.println(stringBuffer.toString());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String text = "name=ypl;ypl=9090;n=op";
//        String[] s = text.split(";");
//        for (int i = 0; i < s.length; i++) {
//            String a = s[i];
//            System.out.println(a.substring(a.indexOf("=") + 1));
//            System.out.println(s[i]);
//        }
//        CookieUntil cookieUntil = new CookieUntil("ID=1589111347; Max-Age=3600; Expires=Wed, 05-Sep-2018 09:42:35 GMT;\n" +
//                "name=15708989332; Max-Age=3600; Expires=Wed, 05-Sep-2018 09:42:35 GMT;\n" +
//                "JSESSIONID=127A07EC03F7299AAF0EA33E369ECE33; Path=/dianming2; HttpOnly;");
//        System.out.println(cookieUntil.getValue("name"));
//        String cookieText = "ID=1589111347; Max-Age=3600; Expires=Wed, 05-Sep-2018 09:42:35 GMT;" +
//                "name=15708989332; Max-Age=3600; Expires=Wed, 05-Sep-2018 09:42:35 GMT;" +
//                "JSESSIONID=127A07EC03F7299AAF0EA33E369ECE33; Path=/dianming2; HttpOnly;";
//        CookieUntil cookieUntil = new CookieUntil(cookieText);
//        System.out.println(cookieUntil.getValue("name"));
//        System.out.println(cookieUntil.getValue("ID"));
        String text = " {\"code\":4001,\"message\":\"用户注册，获得数据\",\"head\":{\"name\":\"刘云飞\",\"signature\":\"我们漂亮\"},\"use\":{\"code\":3001,\"message\":\"请求数据成功\",\"class_number\":2," +
                "\"classes\":[{\"classId\":\"1114097773\",\"className\":\"软件工程\",\"classNumber\":58},{\"classId\":\"asdfawerq3r1234\",\"className\":\"软件\",\"classNumber\":54}]}}\n";
        JSONObject jsonObject = JSONObject.fromObject(text);
        JSONObject object = jsonObject.getJSONObject("use");
        //显示数据；
        JSONArray jsonArray = object.getJSONArray("classes");
        System.out.println(jsonArray.toString());

    }
}
