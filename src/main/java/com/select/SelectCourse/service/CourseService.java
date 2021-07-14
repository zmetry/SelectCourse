package com.select.SelectCourse.service;

import com.select.SelectCourse.entity.Course;

import java.util.List;

public interface CourseService {
    public List<Course> getAllCourse();
    public void removeCourseById(int cid);
    public void insertCourse(Course course);
    public void modifyCourse(Course course);
    public Course getCourseByName(String cname);
    public Course getCourseById(int cid);
}
