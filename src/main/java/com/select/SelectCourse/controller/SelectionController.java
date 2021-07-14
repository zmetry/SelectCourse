package com.select.SelectCourse.controller;

import com.alibaba.fastjson.JSON;
import com.select.SelectCourse.entity.*;
import com.select.SelectCourse.service.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

//选课管理中心
@Controller
@Slf4j
public class SelectionController {

    @Autowired
    private GradeServiceImpl gradeService;

    @Autowired
    private TeacherServiceImpl teacherService;

    @Autowired
    private CourseSelectorServiceImpl courseSelectorService;

    @Autowired
    private AdminstratorServiceImpl adminstratorService;

    @Autowired
    private CourseServiceImpl courseService;

    @Autowired
    private StudentServiceImpl studentService;

    @RequestMapping("/student/SelectCourse")
    public String toStudentSelectCourse(HttpServletRequest request){

        HashMap<Course,String> courseTechMap = new HashMap<>();

        User user = (User) request.getSession().getAttribute("user");

        String uname = user.getUname();
        int uid = user.getUid();

        HashMap<String,Float> courGradeMap = new HashMap<>();

        //获取选课的学生的信息;学生，课程信息
        HashMap map = adminstratorService.getstuCourMap();

        //获取该学生的选课信息
        List<Course> courseList = (List<Course>) map.get(uname);
        for (Course course : courseList) {
            HashMap temp = adminstratorService.getTechStuMap(course.getName());
            if (temp != null){
                String tname = (String) temp.keySet().iterator().next();
                Grade grade = gradeService.getGrade(uid,course.getCid(),teacherService.getTeacherByName(tname).getTId());
                float score;
                if (grade == null){
                    score = 0.0f;
                }else{
                    score = grade.getScore();
                }

                courGradeMap.put(course.getName(),score);
                courseTechMap.put(course, tname);
            }

        }

        //传入学生成绩信息
        request.getSession().setAttribute("courGradeMap",courGradeMap);

        //传入该学生的选课信息
        request.getSession().setAttribute("courseTechMap",courseTechMap);
        //传入所有选课信息
        List<CourseSelector> Selector = courseSelectorService.getAllCourseSelector();
        request.getSession().setAttribute("Selector",Selector);

        return "/student/SelectCourse";
    }

    /**
     * 学生选课
     * @param uname 学生名
     * @param cname 课程名
     * @param tname 教师名
     * @return
     */
    @ResponseBody
    @RequestMapping("/student/SelectCourseTech")
    public String selectCourseTech(@RequestParam("uname") String uname,
                                   @RequestParam("cname") String cname,
                                   @RequestParam("tname") String tname){

        String res;

        log.info("学生选课：" + uname + " " + cname + " " + tname);

        try {
            gradeService.insertGrade(Grade.builder()
                    .cid(courseService.getCourseByName(cname).getCid())
                    .tid(teacherService.getTeacherByName(tname).getTId())
                    .sid(studentService.getStudentByName(uname).getSId())
                    .score(0.0f)
                    .build());
            adminstratorService.AddStudent(uname,tname,cname);
            courseSelectorService.SelectCourse(cname);
            res = "true";
        } catch (Exception e) {
            e.printStackTrace();
            res = "false";
        }


        return JSON.toJSONString(res);
    }

    @ResponseBody
    @RequestMapping("/student/DropCourseTech")
    public String dropCourseTech(@RequestParam("uname") String uname,
                                   @RequestParam("cname") String cname,
                                   @RequestParam("tname") String tname){

        String res;

        log.info("学生选课：" + uname + " " + cname + " " + tname);

        try {
            adminstratorService.dropStudent(uname,tname,cname);
            courseSelectorService.dropCourse(cname);
            res = "true";
        } catch (Exception e) {
            e.printStackTrace();
            res = "false";
        }


        return JSON.toJSONString(res);
    }

    /**
     * 获取学生选课信息
     * @param cname 课程名
     * @param tname 教师名
     * @return
     */
    @ResponseBody
    @RequestMapping("/admin/SelectCourseTech/getStudentList")
    public String getStudentList(@RequestParam("cname") String cname,
                                   @RequestParam("tname") String tname){

        String res;

        log.info("学生选课列表：" + " " + cname + " " + tname);

        try {
            HashMap map = (HashMap) adminstratorService.getCourTechStuMap().get(cname);
            List list = (List) map.get(tname);
            res = JSON.toJSONString(list);
        } catch (Exception e) {
            e.printStackTrace();
            res = "false";
        }


        return JSON.toJSONString(res);
    }

    @RequestMapping("/student/CourList")
    public String toStuCourList(HttpServletRequest request){

        HashMap<Course,String> courseTechMap = new HashMap<>();
        User user = (User) request.getSession().getAttribute("user");
        String uname = user.getUname();
        int uid = user.getUid();

        HashMap<String,Float> courGradeMap = new HashMap<>();

        //获取选课的学生的信息;学生，课程信息
        HashMap map = adminstratorService.getstuCourMap();

        //获取该学生的选课信息
        List<Course> courseList = (List<Course>) map.get(uname);
        for (Course course : courseList) {
            HashMap temp = adminstratorService.getTechStuMap(course.getName());
            if (temp != null){
                String tname = (String) temp.keySet().iterator().next();
                Grade grade = gradeService
                        .getGrade(uid,course.getCid(),teacherService.getTeacherByName(tname).getTId());
                float score;
                if (grade == null){
                    score = 0.0f;
                }else{
                    score = grade.getScore();
                }

                courGradeMap.put(course.getName(),score);
                courseTechMap.put(course, tname);
            }

        }

        //传入学生成绩信息
        request.getSession().setAttribute("courGradeMap",courGradeMap);

        request.getSession().setAttribute("courseTechMap",courseTechMap);

        return "/student/CourList";
    }

}
