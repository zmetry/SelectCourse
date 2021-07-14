package com.select.SelectCourse.controller;

import com.alibaba.fastjson.JSON;
import com.select.SelectCourse.entity.Administrator;
import com.select.SelectCourse.entity.Course;
import com.select.SelectCourse.entity.Student;
import com.select.SelectCourse.service.impl.AdminstratorServiceImpl;
import com.select.SelectCourse.service.impl.CourseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
public class HtmlController {

    @Autowired
    private CourseServiceImpl courseService;

    @RequestMapping(value = {"/", "/index"})
    public String index() {
        return "index";
    }

    @RequestMapping("/registerPage")
    public String register(){
        return "registerPage";
    }

    @RequestMapping("/MainPage")
    public String toMainPage() {
        return "MainPage";
    }

    @ResponseBody
    @RequestMapping("/text")
    public String test(){
        log.info("进入显示结果");
        List<Course> courseList = courseService.getAllCourse();
        //将所有的课程进行赋值给administrator,对courTechStuMap进行初始化
        HashMap<String, HashMap<String, List<Student>>> temp = new HashMap<>();
        for (Course course : courseList)
            temp.put(course.getName(),new HashMap<String, List<Student>>());
        return JSON.toJSONString(temp);
    }

}
