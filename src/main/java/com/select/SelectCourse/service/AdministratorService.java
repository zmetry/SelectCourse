package com.select.SelectCourse.service;

import com.select.SelectCourse.entity.Administrator;
import com.select.SelectCourse.entity.Course;
import com.select.SelectCourse.entity.Student;

import java.util.HashMap;
import java.util.List;

public interface AdministratorService {

    public HashMap getCourTechStuMap();

    public HashMap getTechStuMap(String courName);

    public void ChangeCoureTech(String name,String tName);

    public void ChangeCourName(String bName,String aName);

    /*学生操作*/

    public void ChangeStuName(String bName,String aName);

    public void ModifyStudent(Student student);

    public void InsertStudent(String sName);

    public void DelStudent(String sName);

    public void AddStudent(String uname,String tname,String cname);

    public void dropStudent(String uname,String tname,String cname);

    /*课程操作*/
    public void AddCourse(String cname);

    public void DelCourse(String cname);

    public void ModifyCourseMsg(Course course);

    public void DelTech(String tName);

    public HashMap getstuCourMap();

}
