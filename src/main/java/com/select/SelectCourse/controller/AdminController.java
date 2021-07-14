package com.select.SelectCourse.controller;

import com.alibaba.fastjson.JSON;
import com.select.SelectCourse.entity.Course;
import com.select.SelectCourse.entity.Student;
import com.select.SelectCourse.entity.Teacher;
import com.select.SelectCourse.entity.User;
import com.select.SelectCourse.service.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@Controller
@Slf4j
public class AdminController {

    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private CourseSelectorServiceImpl courseSelectorService;

    @Autowired
    private AdminstratorServiceImpl adminstratorService;

    @Autowired
    private TeacherServiceImpl teacherService;

    @RequestMapping("/admin/userList/{power}")
    public String getStudentList(@PathVariable("power") int power,
                                 HttpServletRequest request) {

        log.info("获取学生列表中");
        String resHtml = (power == 2 ? "TeacherManager" : "StudentManager");

        if (power == 3) {
            List<Student> studentList = studentService.getAllStudent();
            studentList.sort((o1, o2) -> {
                if (o1.getSId() < o2.getSId())
                    return -1;
                else
                    return 1;
            });
            request.getSession().setAttribute("studentList", studentList);
        } else if (power == 2){
            List<Teacher> teacherList = teacherService.getAllTeacherList();
            teacherList.sort((o1, o2) -> {
                if (o1.getTId() < o2.getTId())
                    return -1;
                else
                    return 1;
            });
            request.getSession().setAttribute("teacherList", teacherList);
        }


        return "admin/" + resHtml;
    }

    @RequestMapping("/admin/courseManager")
    public String toCourseManager(HttpServletRequest request){


        HashMap courTechStuMap = adminstratorService.getCourTechStuMap();
        System.out.println(courTechStuMap);
        request.getSession().setAttribute("courTechStuMap",courTechStuMap);
        request.getSession().setAttribute("teacherListJSON",JSON.toJSONString(teacherService.getAllTeacherList()));
        return "/admin/CourseManager";
    }

    @ResponseBody
    @RequestMapping("/admin/courseManager/modifyTech")
    public String modifyCourTech(@RequestParam("name") String name,
                                 @RequestParam("tname") String tname){

        String res;
        log.info("添加,name:" + name + ",tname:" + tname);
        try {
            adminstratorService.ChangeCoureTech(name,tname);
            courseSelectorService.AddTeacher(name,tname);
            res = "true";
        } catch (Exception e) {
            res = "false";
            e.printStackTrace();
        }
        log.info("添加教师信息结束");
        return JSON.toJSONString(res);
    }

}
