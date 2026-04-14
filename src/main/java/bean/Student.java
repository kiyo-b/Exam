package bean;

import java.io.Serializable;

public class Student implements Serializable {
    private String no;
    private String name;
    private int entYear;
    private String class_num;
    private boolean isAttend;
    private School school;

    public String getNo(){
        return no;
    }
    public void setNo(String no){
        this.no = no;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public int getEntYear(){
        return entYear;
    }
    public void setEntYear(int entYear){
        this.entYear = entYear;
    }

    public void setClassNum(String class_num){
        this.class_num = class_num;
    }public String getClassNum(){
        return class_num;
    }
            
    public boolean isAttend(){
        return isAttend;
    }
    public void setAttend(boolean isAttend){
        this.isAttend = isAttend;
    }

    public School getSchool(){
        return school;
    }
    public void setSchool(School school){
        this.school = school;
    }    
}
