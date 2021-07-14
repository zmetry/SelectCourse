package com.select.SelectCourse.service;

import com.select.SelectCourse.entity.Course;
import com.select.SelectCourse.entity.Teacher;

import java.util.List;

public interface TeacherService {
    public List<Teacher> getAllTeacherList();
    public void deleteTech(String tName);
    public void modifyTech(Teacher teacher);
    public void insertTech(Teacher teacher);
    public Teacher getTeacherByName(String tName);
    public Teacher getTeacherById(int tid);
}
