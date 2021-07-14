package com.select.SelectCourse.mapper;

import com.select.SelectCourse.entity.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {

    public List<Student> getAllStudent();
    public void insertStudent(Student student);
    public void removeStudent(String sName);
    public void modifyStudent(Student student);
    public Student getStudentByName(String name);
    public Student getStudentById(int sId);

}
