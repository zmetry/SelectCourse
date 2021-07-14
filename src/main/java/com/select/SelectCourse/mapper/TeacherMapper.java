package com.select.SelectCourse.mapper;

import com.select.SelectCourse.entity.Course;
import com.select.SelectCourse.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeacherMapper {

    public List<Teacher> getAllTeacherList();

    public void deleteTech(String tName);

    public void modifyTech(Teacher teacher);

    public void insertTech(Teacher teacher);

    public Teacher getTeacherByName(String tName);

    public Teacher getTeacherById(int tid);

}
