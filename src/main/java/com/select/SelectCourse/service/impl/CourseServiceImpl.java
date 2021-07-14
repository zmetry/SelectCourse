package com.select.SelectCourse.service.impl;

import com.select.SelectCourse.entity.Course;
import com.select.SelectCourse.mapper.CourseMapper;
import com.select.SelectCourse.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> getAllCourse() {
        return courseMapper.getAllCourse();
    }

    @Override
    public void removeCourseById(int cid) {
        courseMapper.removeCourseById(cid);
    }

    @Override
    public void insertCourse(Course course) {
        courseMapper.insertCourse(course);
    }

    @Override
    public void modifyCourse(Course course) {
        courseMapper.modifyCourse(course);
    }

    @Override
    public Course getCourseByName(String cname) {
        return courseMapper.getCourseByName(cname);
    }

    @Override
    public Course getCourseById(int cid) {
        return courseMapper.getCourseById(cid);
    }
}
