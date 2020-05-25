package com.springboottest.service;

import cn.hutool.json.JSONObject;
import com.springboottest.model.Student;
import java.util.List;


/**
 * @author LJS
 * @data 2020/5/15 11:52
 */
public interface StudentService {
    public List<String> findAllClass();
    public Student findStudentById(Integer id);
    public JSONObject queryStudent(String keyword,String classId,String orderby,boolean asc,Integer pageNo,Integer pageSize);
    public String deleteStudentByIds(String studentIds);
    public String addStudent(Student student);
    public String updateStudent(Student student,Integer id);
}
