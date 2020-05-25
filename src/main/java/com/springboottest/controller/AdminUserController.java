package com.springboottest.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import com.springboottest.constant.Constants;
import com.springboottest.service.StudentService;
import com.springboottest.model.User;
import com.springboottest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 管理员用户控制器
 * @author LJS
 * @data 2020/5/19 8:19
 */
@Controller
@RequestMapping("/admin")
public class AdminUserController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private UserService userService;

    @RequestMapping("/viewUserInfo")
    @ResponseBody
    public JSONObject findUserById(String id){
        JSONObject json=new JSONObject();
        User user=userService.findUserById(Integer.parseInt(id));
        if (user==null){
            json.set("status", Constants.EMPTY_DATA);
            json.set("info","无法查找指定用户");
        }else {
            json.set("status",Constants.SUCCESS);
            json.set("data",user);
        }
        return json;
    }

    //查询用户结果并分页
    @RequestMapping("/query")
    @ResponseBody
    public JSONObject queryUser(String keyword,String orderby,boolean asc,Integer pageNo, Integer pageSize){
        JSONObject stuJson = userService.queryUser(keyword,orderby,asc,pageNo,pageSize);
        return stuJson;
    }

    //进入管理员界面
    @RequestMapping("/index")
    public String adminIndex(HttpServletRequest request, HttpServletResponse response){
        List<String> classList = studentService.findAllClass();
        request.setAttribute("classList",classList);
        return "admin-index";
    }

    //进入用户管理
    @RequestMapping("/user")
    public String adminUser(){
        return "admin-user";
    }
    //进入个人中心
    @RequestMapping("/personal")
    public String personal(){
        return "admin-personal";
    }

    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateUser(User user){
        JSONObject json=new JSONObject();
        String result = userService.updateUserById(user);
        if (result==null){
            json.set("status",-1);
            json.set("error","修改失败！");
        }else{
            json.set("status",0);
            json.set("info","修改成功!");
        }
        return json;
    }

    //删除用户
    @RequestMapping("/deleteUsers")
    @ResponseBody
    public JSONObject deleteUsers(String userIds){
        JSONObject json=new JSONObject();
        String error = userService.deleteUserById(userIds);
        if (error==null){
            json.set("status",0);
        }else {
            json.set("status",-1);
            json.set("error",error);
        }
        return json;
    }
    //进入修改密码
    @RequestMapping("/updatePass")
    public String updatePass(){
        return "admin-updatePass";
    }

    @RequestMapping("/update")
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
    //重置密码
    @RequestMapping("/resetPassword")
    @ResponseBody
    public JSONObject resetPassword(String userId){
        JSONObject json=new JSONObject();
        String result = userService.resetPassword(Integer.parseInt(userId));
        if (result!=null){
            json.set("status",0);
            json.set("info","重置成功！");
        }else {
            json.set("status",-1);
            json.set("error","重置失败，不允许重置管理员的密码！");
        }
        return json;
    }

    //上传图片
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject upload(MultipartFile file){
        JSONObject result=new JSONObject();
        if (file.isEmpty()){
            result.set("status",-1).set("error","文件为空");
            return result;
        }
        String fileName=file.getOriginalFilename();
        String filePath="D:/fileupload/";
        String uuid= IdUtil.fastSimpleUUID();
        File dir=new File(filePath+uuid+"/");
        if (!dir.exists()){
            dir.mkdirs();
        }
        File dest=new File(filePath+uuid+"/"+fileName);
        try{
            file.transferTo(dest);
            result.set("status",0).set("url","/fileupload/"+uuid+"/"+fileName).set("filename",fileName);
            return result;
        }catch (IOException e){
            e.printStackTrace();
            result.set("status",-1).set("error",e.getMessage());
            return  result;
        }
    }
}
