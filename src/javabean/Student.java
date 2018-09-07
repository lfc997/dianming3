package javabean;
/*
作者：ypl
创建时间：2018/8/29-10:52-2018
*/

public class Student {

    private String studentId;
    private String studentName;
    private String sex;
    private int late = 0;//迟到
    private int truancy = 0;//旷课
    private int leave = 0;//早退
    private String text;//json存储：该学学生的迟到情况
    //[{"date":"","",""},{}]

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getLate() {
        return late;
    }

    public void setLate(int late) {
        this.late = late;
    }

    public int getTruancy() {
        return truancy;
    }

    public void setTruancy(int truancy) {
        this.truancy = truancy;
    }

    public int getLeave() {
        return leave;
    }

    public void setLeave(int leave) {
        this.leave = leave;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
