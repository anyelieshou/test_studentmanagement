package com.springboottest.model;

/**
 * @author LJS
 * @data 2020/5/15 11:37
 */
public class Student {
    private Integer id;
    private String stuNo;
    private String stuName;
    private String stuBirthday;
    private String stuPhoto;
    private Integer classFk;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuBirthday() {
        return stuBirthday;
    }

    public void setStuBirthday(String  stuBirthday) {
        this.stuBirthday = stuBirthday;
    }

    public String getStuPhoto() {
        return stuPhoto;
    }

    public void setStuPhoto(String stuPhoto) {
        this.stuPhoto = stuPhoto;
    }

    public Integer getClassFk() {
        return classFk;
    }

    public void setClassFk(Integer classFk) {
        this.classFk = classFk;
    }
}
