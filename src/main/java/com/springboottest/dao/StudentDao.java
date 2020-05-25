package com.springboottest.dao;

import com.springboottest.model.Class;
import com.springboottest.model.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author LJS
 * @data 2020/5/15 11:43
 */
@Mapper
public interface StudentDao {
    //查询班级信息
    public List<String> findAllClass();
    //根据id查询学生信息
    public Student findStudentById(Integer id);
    //根据学号和姓名查询结果分页并排序（三表关联）
    public List<Student> queryStudent(Map<String,Object> parm);
    //根据id删除学生
    public void deleteStudentById(Integer ids);
    //根据id查询班级
    public Class findClassById(Integer id);
    //根据id,学号查询信息
    public int findByIdandStuNo(Map<String,Object> param);
    //根据学号查询学生
    public Student findStudentByStuNo(String stuNo);
    //增加学生
    public void addStudent(Student student);
    //修改学生
    public void updateStudent(Map<String,Object> parm);


}
