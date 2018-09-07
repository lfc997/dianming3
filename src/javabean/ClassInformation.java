package javabean;
/*
作者：ypl
创建时间：2018/8/27-18:41-2018
*/

public class ClassInformation {
    private String cookie;
    private String classId;
    private String className;
    private int classNumber;
    private String classSqlName;

    public int getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(int classNumber) {
        this.classNumber = classNumber;
    }


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


    public String getClassSqlName() {
        return classSqlName;
    }

    public void setClassSqlName(String classSqlName) {
        this.classSqlName = classSqlName;
    }


    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}
