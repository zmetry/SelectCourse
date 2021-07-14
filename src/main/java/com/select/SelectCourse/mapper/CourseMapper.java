package com.select.SelectCourse.mapper;

import com.select.SelectCourse.entity.Course;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper {

    public List<Course> getAllCourse();
    public void removeCourseById(int cid);
    public void insertCourse(Course course);
    public void modifyCourse(Course course);
    public Course getCourseByName(String cname);
    public Course getCourseById(int cid);

}
