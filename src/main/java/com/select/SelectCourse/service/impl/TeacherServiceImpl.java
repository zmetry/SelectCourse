package com.select.SelectCourse.service.impl;

import com.select.SelectCourse.entity.Course;
import com.select.SelectCourse.entity.Teacher;
import com.select.SelectCourse.mapper.TeacherMapper;
import com.select.SelectCourse.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public List<Teacher> getAllTeacherList() {
        return teacherMapper.getAllTeacherList();
    }

    @Override
    public void deleteTech(String tName) {
        teacherMapper.deleteTech(tName);
    }

    @Override
    public void modifyTech(Teacher teacher) {
        teacherMapper.modifyTech(teacher);
    }

    @Override
    public void insertTech(Teacher teacher) {
        teacherMapper.insertTech(teacher);
    }

    @Override
    public Teacher getTeacherByName(String tName) {
        return teacherMapper.getTeacherByName(tName);
    }

    @Override
    public Teacher getTeacherById(int tid) {
        return teacherMapper.getTeacherById(tid);
    }

}
