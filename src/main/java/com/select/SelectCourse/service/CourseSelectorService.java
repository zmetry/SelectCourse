package com.select.SelectCourse.service;

import com.select.SelectCourse.entity.Course;
import com.select.SelectCourse.entity.CourseSelector;

import java.util.List;

public interface CourseSelectorService {

    public List<CourseSelector> getAllCourseSelector();
    public void InsertCourseSelector(CourseSelector courseSelector);
    public void AddTeacher(String name,String tName);
    public void SelectCourse(String name);
    public void dropCourse(String name);
    public void InsertCourse(Course course);
    public void delCourse(String name);
    public void delTech(String tname);
    public void ModifyTech(String bName,String cName);
    public void ChangeCourse(Course course);

}
