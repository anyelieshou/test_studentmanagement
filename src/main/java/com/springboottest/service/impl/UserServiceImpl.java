package com.springboottest.service.impl;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboottest.dao.UserDao;
import com.springboottest.model.User;
import com.springboottest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LJS
 * @data 2020/5/15 10:10
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 根据主键查询用户信息
     * @param id
     * @return
     */
   public User findUserById(Integer id){
       return userDao.findUserById(id);
   }
    //用户注册
    @Override
    public String regist(User user) {
        User exitsUser = userDao.findUserByIdcard(user.getIdcard());
        if (exitsUser==null){
            //获取密码
            String password=user.getPassword();
            //进行密码加密
            user.setPassword(SecureUtil.md5(password));
            userDao.regist(user);
            return "success";
        }
        return null;
    }

    //用户登录
    @Override
    public User login(String idcard, String password) {
        String newPass=SecureUtil.md5(password);
        Map<String,Object> param=new HashMap<>();
        param.put("idcard",idcard);
        param.put("password",newPass);
        User loginUser= userDao.login(param);
        if (loginUser==null){
            return null;
        }
        return loginUser;
    }

    @Override
    public JSONObject queryUser(String keyword, String orderby, boolean asc, Integer pageNo, Integer pageSize) {
        Map<String,Object> parm=new HashMap<>();
        parm.put("keyword",keyword);
        String ascStr=asc?"asc":"desc";
        parm.put("orderby",orderby);
        parm.put("asc",ascStr);
        PageHelper.startPage(pageNo,pageSize);
        List<User> userList= userDao.queryUser(parm);
        PageInfo<User> pageInfo=new PageInfo<>(userList);
        JSONObject ret=new JSONObject();
        ret.set("userArray", JSONUtil.parseArray(userList));
        ret.set("recCount",pageInfo.getTotal());
        ret.set("pageCount",pageInfo.getPages());
        return ret;
    }


    //查询所有用户
    @Override
    public List<User> findAllUser() {
        return userDao.findAllUser();
    }

    //修改用户
    @Override
    public String updateUserById(User user) {
       Map<String,Object> rmpa=new HashMap<>();
       rmpa.put("id",user.getId());
       rmpa.put("idCard",user.getIdcard());
        int count = userDao.findByIdandUserNo(rmpa);
        if (count>0){
            return null;
        }
        Map<String,Object> param=new HashMap<>();
        param.put("user",user);
        param.put("id",user.getId());
        userDao.updateUserById(param);
        return "success";
    }

    //修改密码
    @Override
    public String  updatePass(String password1,String password2,Integer id) {
        System.out.println("原始密码："+password1);
        System.out.println("新密码："+password2);
        //给密码加密
        String oldPassword=SecureUtil.md5(password1);
        //根据id获取用户
        User user = userDao.findUserById(id);
        //用户获取密码
        String userpass=user.getPassword();
        if (oldPassword.equalsIgnoreCase(userpass)){
            String newPass=SecureUtil.md5(password2);
            Map<String,Object> param=new HashMap<>();
            param.put("password",newPass);
            param.put("id",id);
            userDao.updatePass(param);
            return "success";
        }
        return null;
    }

    //删除用户
    @Override
    public String deleteUserById(String userIds) {
        final StringBuffer error=new StringBuffer();
        String[] idArray=userIds.split(",");
        for (String id:idArray){
            if (userDao.findUserById(Integer.parseInt(id)).getRoleid()==1){
                error.append("不允许删除管理员用户");
            }else {
                userDao.deleteUserById(Integer.parseInt(id));
            }
        }
        if (error.length()==0){
            return null;
        }
        return error.toString();
    }

    //重置密码
    @Override
    public String resetPassword(Integer id) {
        User user = userDao.findUserById(id);
        if (user.getRoleid()!=1){
            Map<String,Object> parm=new HashMap<>();
            parm.put("id",id);
            String oldePass= SecureUtil.md5("123456");
            parm.put("password",oldePass);
            userDao.updatePass(parm);
            return "success";
        }
        return null;
    }
}
