package com.select.SelectCourse.controller;

import com.alibaba.fastjson.JSON;
import com.select.SelectCourse.entity.Course;
import com.select.SelectCourse.service.impl.AdminstratorServiceImpl;
import com.select.SelectCourse.service.impl.CourseSelectorServiceImpl;
import com.select.SelectCourse.service.impl.CourseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
public class CourseController {

    @Autowired
    private CourseServiceImpl courseService;

    @Autowired
    private CourseSelectorServiceImpl courseSelectorService;

    @Autowired
    private AdminstratorServiceImpl adminstratorService;

    @RequestMapping("/MainPage/CourseList")
    public String showAllCourse(HttpServletRequest request){

        List<Course> courseList = courseService.getAllCourse();
        request.getSession().setAttribute("courseList",courseList);

        return "courseList";
    }


    @ResponseBody
    @RequestMapping("/admin/CourseList/delCour")
    public String deleteCourseById(@RequestParam("cid") int cid){

        String res;

        try{
            courseSelectorService.delCourse(courseService.getCourseById(cid).getName());
            adminstratorService.DelCourse(courseService.getCourseById(cid).getName());
            courseService.removeCourseById(cid);
            res = "true";
        }catch (Exception e){
            e.printStackTrace();
            res = "false";
        }

        return JSON.toJSONString(res);
    }

    @ResponseBody
    @RequestMapping("/admin/CourseList/insertCourse")
    public String insertCourse(@RequestParam("name") String name,
                               @RequestParam("score") float score,
                               @RequestParam("startlesson") int startlesson,
                               @RequestParam("endlesson") int endlesson,
                               @RequestParam("startweek") int startweek,
                               @RequestParam("endweek") int endweek){

        String res;

        log.info("进入课程添加系统");

        if (startlesson >= endlesson || startweek >= endweek){
            res = "false";
        }else{

            Course course = Course.builder().Name(name).score(score)
                    .startLesson(startlesson).endLesson(endlesson)
                    .startWeek(startweek).endWeek(endweek).build();

            try{
                courseService.insertCourse(course);
                courseSelectorService.InsertCourse(courseService.getCourseByName(name));
                adminstratorService.AddCourse(course.getName());
                res = "true";
            }catch (Exception e){
                e.printStackTrace();
                res = "false";
            }

        }

        return JSON.toJSONString(res);
    }

    @ResponseBody
    @RequestMapping("/admin/CourseList/modifyCourse")
    public String modigyCourse(@RequestParam("cid") int cid,
                               @RequestParam("name") String name,
                               @RequestParam("score") float score,
                               @RequestParam("startlesson") int startlesson,
                               @RequestParam("endlesson") int endlesson,
                               @RequestParam("startweek") int startweek,
                               @RequestParam("endweek") int endweek){

        String res;

        log.info("进入课程修改系统");

        if (startlesson >= endlesson || startweek >= endweek){
            res = "false";
        }else{

            Course tmp = courseService.getCourseById(cid);

            Course course = Course.builder().cid(cid).Name(name).score(score)
                    .startLesson(startlesson).endLesson(endlesson)
                    .startWeek(startweek).endWeek(endweek).build();

            try{
                if (!tmp.getName().equals(name)){
                    adminstratorService.ChangeCourName(tmp.getName(),name);
                }
                adminstratorService.ModifyCourseMsg(course);
                courseService.modifyCourse(course);
                courseSelectorService.ChangeCourse(course);
                res = "true";
            }catch (Exception e){
                e.printStackTrace();
                res = "false";
            }

        }

        return JSON.toJSONString(res);

    }


}
