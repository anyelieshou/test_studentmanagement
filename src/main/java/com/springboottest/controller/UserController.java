package com.springboottest.controller;

import cn.hutool.json.JSONObject;
import com.springboottest.constant.Constants;
import com.springboottest.service.StudentService;
import com.springboottest.model.User;
import com.springboottest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * 普通用户控制器
 * @author LJS
 * @data 2020/5/17 10:26
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private UserService userService;

    //进入登录页面
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    //用户登录
    @ResponseBody
    @RequestMapping("/loginUser")
    public JSONObject login(String idcard, String password, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(true);
        JSONObject json=new JSONObject();
        User user= userService.login(idcard, password);
        if (user==null){//登录失败
            json.set("status",-1);
            json.set("error","用户名或密码错误");
            return json;
        }else {
            //登录成功
            json.set("status",0);
            json.set("userJson",user);
            json.set("roleId",user.getRoleid());
            session.setAttribute(Constants.CURRNT_USER_KEY,json);
            return json;
        }
    }

    //进入主页
    @RequestMapping("/index")
    public String index(Model model, HttpServletRequest request, HttpServletResponse response){
        List<String> classList = studentService.findAllClass();
        request.setAttribute("classList",classList);
        return "index";
    }
    //进入注册页面
    @RequestMapping("/regist")
    public String regist(){
        return "regist";
    }

    //用户注册
    @RequestMapping(value = "/registUser",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject regist(User user){
        JSONObject json=new JSONObject();
        String result = userService.regist(user);
        if (result!=null){
           json.set("status",0);
           json.set("info","注册成功");
        }else{
            json.set("status",-1);
            json.set("error","注册失败");
        }
        return json;
    }
    //查询所有用户
    @RequestMapping("/findAllUser")
    @ResponseBody
    public List<User> findAllUser(){
        return userService.findAllUser();
    }

    //进入个人中心
    @RequestMapping("/personal")
    public String persional(){
        return "personal";
    }

    //完成个人信息修改
    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateUser(User user){
        JSONObject json=new JSONObject();
        String result = userService.updateUserById(user);
        if (result==null){
            json.set("status",-1);
            json.set("error","修改失败！");
        }
        json.set("info","修改成功");
        return json;
    }

    //进入修改密码
    @RequestMapping("/passView")
    public String passView(){
        return "updatePass_view";
    }

    @RequestMapping("/updatePass")
    @ResponseBody
    public JSONObject updatePass(String password1,String password2,Integer id){
        JSONObject json=new JSONObject();
        String result = userService.updatePass(password1, password2, id);
        if (result==null){
            json.set("status",-1);
            json.set("error","初始密码输入错误");
        }else {
            json.set("status",0);
            json.set("info","修改成功");
        }
        return json;
    }

    //注销
    @RequestMapping("/logout")
    public void logout(HttpServletRequest request,HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute(Constants.CURRNT_USER_KEY);
        session.invalidate();
        response.sendRedirect("/user/login");
    }

}
