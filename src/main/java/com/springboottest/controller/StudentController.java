package com.springboottest.controller;

import cn.hutool.json.JSONObject;
import com.springboottest.constant.Constants;
import com.springboottest.model.Student;
import com.springboottest.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author LJS
 * @data 2020/5/15 11:54
 */
@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @RequestMapping("/view")
    public String  index(){
        return "student_vue";
    }

    @RequestMapping("/viewStudentInfo")
    @ResponseBody
    public JSONObject findStudentById(String id){
        JSONObject json=new JSONObject();
        Student student= studentService.findStudentById(Integer.parseInt(id));
        if (student==null){
            json.set("status", Constants.EMPTY_DATA);
            json.set("info","无法查找指定用户");
        }else {
            json.set("status",Constants.SUCCESS);
            json.set("data",student);
        }
        return json;
    }

    //查询学生结果并分页
    @RequestMapping("/query")
    @ResponseBody
    public JSONObject queryStudent(String keyword,String classId,String orderby,boolean asc,Integer pageNo, Integer pageSize){
        JSONObject stuJson = studentService.queryStudent(keyword,classId,orderby,asc,pageNo,pageSize);
        return stuJson;
    }

    //删除学生
    @RequestMapping("/deleteStudents")
    @ResponseBody
    public JSONObject deleteStudents(String studentIds){
        JSONObject json=new JSONObject();
        String error = studentService.deleteStudentByIds(studentIds);
        if (error==null){
            json.set("status",0);
        }else {
            json.set("status",-1);
            json.set("error",error);
        }
        return json;
    }

    //新增学生
    @RequestMapping(value = "/addStudent",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addStudent(Student student){
        JSONObject json=new JSONObject();
        String result = studentService.addStudent(student);
       if (result!=null){
           json.set("status",-1);
           json.set("error",result);
       }else{
           json.set("status",0);
           json.set("sccuess","成功");
       }
       return json;
    }

    //修改学生
    @RequestMapping("/updateStudent")
    @ResponseBody
    public JSONObject updateStudent(Student student,String id){
        JSONObject json=new JSONObject();
        String result=studentService.updateStudent(student, Integer.parseInt(id));
        if (result!=null){
            json.set("status",-1);
            json.set("error",result);
        }else{
            json.set("status",0);
            json.set("sccuess","成功");
        }
        return json;
    }
}
