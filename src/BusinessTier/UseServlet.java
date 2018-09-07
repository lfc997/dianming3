package BusinessTier;
/*
作者：ypl
创建时间：2018/8/26-21:24-2018
*/

import MyException.MyException;
import UUId.myUUID;
import UseDao.UseDao;
import classDao.ClassDao;
import javabean.ClassInformation;
import javabean.Use;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import until.Constant;

import java.util.List;

public class UseServlet {


    public boolean register(Use use) throws MyException {

        UseDao useDao = new UseDao();
        try {
            if (!useDao.add(use))
                throw new MyException("注册失败");
        } catch (MyException e) {
            throw new MyException("注册失败");
        }

        return true;
    }

    public Use login(Use use) throws MyException {
        //通过手机号码/邮箱查找用户的是否存在
        //不存在则抛出异常
        //存在：比对密码是否一样：一样则登陆成功


        UseDao useDao = new UseDao();
        Use myUse = null;
        String uuid = null;
        try {
            uuid = myUUID.getUUid(use.getName()) + "";
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("用户名字为空");
        }
        myUse = useDao.findByUid(uuid);
        if (myUse == null) {
            throw new MyException("登陆失败");
        }
        if (!myUse.getPassword().equals(use.getPassword())) {
            throw new MyException("密码错误");
        }
        return myUse;
    }

    //验证用户存在不
    public Use findByCookie(String cookie) throws MyException {
        Use use = null;
        UseDao useDao = new UseDao();
        try {
            use = useDao.findByUid(cookie);
            if (use == null) throw new MyException("cookie 不存在");
        } catch (MyException e) {
            throw new MyException("cookie 不存在");
        }
        return use;
    }

    public String getHomeString(String cookie) throws MyException {
        UseDao useDao = new UseDao();
        JSONObject jsonObject = new JSONObject();
        try {
            Use use = useDao.findByUid(cookie);
            System.out.println(use.getUid() + "uid::::");
            if (use == null) {
                throw new MyException("cookie不存在");
            }
            jsonObject.put(Constant.CODE, 3001);
            jsonObject.put(Constant.MESSAGE, "请求数据成功");
            JSONArray jsonArray = new JSONArray();
            //添加班级信息：
            //通过cookie获取班级信息：
            ClassDao classDao = new ClassDao();
            List<ClassInformation> classesInformation = classDao.getClassInformation(cookie);
            System.out.println(classesInformation.size());
            for (int i = 0; i < classesInformation.size(); i++) {
                ClassInformation classInformation = classesInformation.get(i);
                JSONObject object = new JSONObject();
                object.put(Constant.CLASS_ID, classInformation.getClassId());
                object.put(Constant.CLASS_NAME, classInformation.getClassName());
                object.put(Constant.CLASS_NUMBER, classInformation.getClassNumber());
                jsonArray.add(i, object);
            }
            jsonObject.put(Constant.CLASSES_NUMBER, classesInformation.size());
            jsonObject.put(Constant.CLASSES, jsonArray);
        } catch (MyException e) {
            throw e;
        }
        return jsonObject.toString();
    }


}
