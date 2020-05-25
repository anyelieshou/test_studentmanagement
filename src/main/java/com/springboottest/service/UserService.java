package com.springboottest.service;

import cn.hutool.json.JSONObject;
import com.springboottest.model.User;

import java.util.List;

/**
 * @author LJS
 * @data 2020/5/14 23:14
 */
public interface UserService {
    public User findUserById(Integer id);
    //用户注册
    public String regist(User user);
    //用户登录
    public User login(String idcard,String password);
    public JSONObject queryUser(String keyword,String orderby,boolean asc,Integer pageNo,Integer pageSize);
    //查询所有用户
    public List<User> findAllUser();
    //修改用户
    public String updateUserById(User user);
    //修改密码
    public String updatePass(String password1,String password2,Integer id);
    //删除用户
    public String deleteUserById(String userIds);
    //重置密码
    public String resetPassword(Integer id);

}
