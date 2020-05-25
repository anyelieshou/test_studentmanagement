package com.springboottest.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboottest.dao.StudentDao;
import com.springboottest.model.Class;
import com.springboottest.model.Student;
import com.springboottest.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LJS
 * @data 2020/5/15 11:53
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentDao studentDao;
    @Override
    public List<String> findAllClass() {
        List<String> classList = studentDao.findAllClass();
        return classList;
    }

    /**
     * 根据主键查询学生信息
     */
    @Override
    public Student findStudentById(Integer id) {
        return studentDao.findStudentById(id) ;
    }

    @Override
    public JSONObject queryStudent(String keyword,String classId,String orderby,boolean asc,Integer pageNo,Integer pageSize) {
        Map<String,Object> parm=new HashMap<>();
        parm.put("keyword",keyword);
        String ascStr= asc ? "asc":"desc";
        parm.put("orderby",orderby);
        parm.put("asc",ascStr);
        parm.put("classId",classId);
        PageHelper.startPage(pageNo,pageSize);
        List<Student> stuList= studentDao.queryStudent(parm);
        PageInfo<Student> pageInfo=new PageInfo<>(stuList);
        JSONObject ret=new JSONObject();
        ret.set("stuArray", JSONUtil.parseArray(stuList));
        ret.set("recCount",pageInfo.getTotal());
        ret.set("pageCount",pageInfo.getPages());
        return ret;
    }

    @Transactional
    @Override
    public String deleteStudentByIds(String studentIds) {
        final StringBuffer error=new StringBuffer();
        String[] idArray=studentIds.split(",");
        for (String id:idArray){
            if (Integer.parseInt(id)<79 && Integer.parseInt(id)>5){
                error.append("不允许删除id为6-78之间的记录！");
            }
            studentDao.deleteStudentById(Integer.parseInt(id));
        }
       if (error.length()==0){
           return null;
       }
       return error.toString();
    }

    //新增学生
    @Transactional
    @Override
    public String addStudent(Student student) {
        Class exitsClass = studentDao.findClassById(student.getClassFk());
        if (exitsClass==null){
            return "班级不存在，新增学生失败";
        }
        Student exitsStudent = studentDao.findStudentByStuNo(student.getStuNo());
        if (exitsStudent!=null){
            return "学号已存在，新增学生失败";
        }else {
            studentDao.addStudent(student);
            return null;
        }
    }

    //修改学生
    @Transactional
    @Override
    public String  updateStudent(Student student,Integer id) {
        Map<String, Object> parm=new HashMap<>();
        Class exitsClass = studentDao.findClassById(student.getClassFk());
        if (exitsClass==null){
            return "班级不存在，修改学生失败";
        }
        Map<String,Object> rmpa=new HashMap<>();
        rmpa.put("id",id);
        rmpa.put("stuNo",student.getStuNo());
        int count = studentDao.findByIdandStuNo(rmpa);
        if (count>0){
            return "学号已存在，修改学生失败";
        }else {
            parm.put("student",student);
            parm.put("id",id);
            studentDao.updateStudent(parm);
            return null;
        }
    }

}
