package com.springboottest.dao;

import com.springboottest.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author LJS
 * @data 2020/5/14 18:51
 */
@Mapper
public interface UserDao {
    //用户注册
    public void regist(User user);
    //用户登录(账号和密码)
    public User login(Map<String,Object> param);
    //根据学号和姓名查询结果分页并排序（三表关联）
    public List<User> queryUser(Map<String,Object> parm);
    //查询所有用户
    public List<User> findAllUser();
    //通过id查找用户
    public User findUserById(Integer id);
    //通过用户账号idcard查找用户
    public User findUserByIdcard(String idcard);
    ////根据id,用户编号查询信息
    public int findByIdandUserNo(Map<String,Object> param);
    //修改用户
    public void updateUserById(Map<String,Object> param);
    //修改密码
    public void updatePass(Map<String,Object> param);
    //删除用户
    public void deleteUserById(Integer ids);

}
