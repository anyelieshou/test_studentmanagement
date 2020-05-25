package com.springboottest.model;

import java.util.Date;

/**
 * @author LJS
 * @data 2020/5/14 18:45
 */
public class User {
    //用户id
    private Integer id;
    //用户账号
    private String idcard;
    //用户姓名
    private String username;
    //用户密码
    private String password;
    //用户生日
    private String birthday;
    //用户电话
    private String tel;
    //用户角色（1代表管理员，2代表普通用户）
    private Integer roleid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
