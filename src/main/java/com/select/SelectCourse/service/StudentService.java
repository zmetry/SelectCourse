package com.select.SelectCourse.service;

import com.select.SelectCourse.entity.Student;

import java.util.List;

public interface StudentService {
    public List<Student> getAllStudent();
    public void insertStudent(Student student);
    public void removeStudent(String sName);
    public void modifyStudent(Student student);
    public Student getStudentByName(String name);
    public Student getStudentById(int sId);
}
