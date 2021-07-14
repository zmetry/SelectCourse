package com.select.SelectCourse.service.impl;

import com.select.SelectCourse.entity.Student;
import com.select.SelectCourse.mapper.StudentMapper;
import com.select.SelectCourse.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<Student> getAllStudent() {
        return studentMapper.getAllStudent();
    }

    @Override
    public void insertStudent(Student student) {
        studentMapper.insertStudent(student);
    }

    @Override
    public void removeStudent(String sName) {
        studentMapper.removeStudent(sName);
    }

    @Override
    public void modifyStudent(Student student) {
        studentMapper.modifyStudent(student);
    }

    @Override
    public Student getStudentByName(String name) {
        return studentMapper.getStudentByName(name);
    }

    @Override
    public Student getStudentById(int sId) {
        return studentMapper.getStudentById(sId);
    }

}
