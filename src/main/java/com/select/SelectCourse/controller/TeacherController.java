package com.select.SelectCourse.controller;

import com.alibaba.fastjson.JSON;
import com.select.SelectCourse.entity.*;
import com.select.SelectCourse.service.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
public class TeacherController {

    @Autowired
    private CourseSelectorServiceImpl courseSelectorService;

    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private CourseServiceImpl courseService;

    @Autowired
    private GradeServiceImpl gradeService;

    @Autowired
    private TeacherServiceImpl teacherService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AdminstratorServiceImpl adminstratorService;

    @ResponseBody
    @RequestMapping("/admin/userList/deleteTech/{tName}")
    public String deleteStuByName(@PathVariable("tName") String tName) {

        log.info("即将删除老师:", tName);
        String res;
        try {
            courseSelectorService.delTech(tName);
            adminstratorService.DelTech(tName);
            userService.deleteUserByName(tName);
            teacherService.deleteTech(tName);
            res = "true";
        } catch (Exception e) {
            res = "false";
            e.printStackTrace();
        }
        return JSON.toJSONString(res);

    }

    @ResponseBody
    @RequestMapping("/admin/modifyTech")
    public String modifyStu(@RequestParam("tid") int tid,
                            @RequestParam("tname") String tname,
                            @RequestParam("office") String office,
                            @RequestParam("station") String station) {

        String res;

        log.info("开始进行更改，信息为——tname:" + tname + ",office:" + office + ",station:" + station);

        User user = User.builder().uid(tid).uname(tname).build();

        Teacher teacher = Teacher.builder().tName(tname).tId(tid).Office(office).Station(station).build();

        try {
            if (!teacherService.getTeacherById(tid).equals(tname)){
                courseSelectorService.ModifyTech(teacherService.getTeacherById(tid).getTName(),tname);
            }
            teacherService.modifyTech(teacher);
            userService.modifyUname(user);
            res = "true";
        } catch (Exception e) {
            e.printStackTrace();
            res = "false";
        }

        return JSON.toJSONString(res);
    }

    @ResponseBody
    @RequestMapping("/admin/insertTech")
    public String insertTech(@RequestParam("tname") String tname,
                            @RequestParam("office") String office,
                            @RequestParam("station") String station) {

        String res;

        log.info("开始进行消息的插入，信息为——tname:" + tname + ",office:" + office + ",station:" + station);

        User user = User.builder().uname(tname).password(tname).power(2).build();

        try {
            //先插入user，自动获取id
            userService.InsertUser(user);
            int tid = userService.getUserByName(tname).getUid();
            log.info("系统自动分配的id为：" + tid);
            Teacher teacher = Teacher.builder().tId(tid).tName(tname).Office(office).Station(station).build();
            teacherService.insertTech(teacher);
            res = "true";

        } catch (Exception e) {
            e.printStackTrace();
            res = "false";
        }

        return JSON.toJSONString(res);
    }


    @RequestMapping("/teacher/courseManager")
    public String toTechManager(HttpServletRequest request){

        HttpSession session = request.getSession();
        HashMap<String,HashMap<String, List<Student>>> TcourTechStuMap = new HashMap<>();
        HashMap<String,HashMap<String,Float>> courseStuScoreMap = new HashMap<>();

        //教师名
        String uname = ((User) session.getAttribute("user")).getUname();

        //HashMap<String,HashMap<String,List<Student>>> 课程名，教师名，学生列表
        HashMap courTechStuMap = adminstratorService.getCourTechStuMap();
        for (Object k1 : courTechStuMap.keySet()) {
            HashMap<String,Float> stuScoreMap = new HashMap<>();
            HashMap map = (HashMap) courTechStuMap.get(k1);
            if (map.containsKey(uname)){
                TcourTechStuMap.put((String) k1, map);
                List<Student>  studentList = (List<Student>) map.get(uname);
                //存入相应的信息
                for (Student student : studentList) {
                    Grade build = Grade.builder()
                            .cid(courseService.getCourseByName((String) k1).getCid())
                            .tid(teacherService.getTeacherByName(uname).getTId())
                            .sid(student.getSId())
                            .build();
                    Grade grade = gradeService.getGrade(student.getSId(), build.getCid(), build.getTid());
                    float score;
                    if (grade == null){
                        score = 0.0f;
                    }else {
                        score = grade.getScore();
                    }

                    stuScoreMap.put(student.getSName(),score);
                }
                courseStuScoreMap.put((String) k1,stuScoreMap);
            }
        }

        session.setAttribute("courseStuScoreMap",courseStuScoreMap);
        session.setAttribute("TcourTechStuMap",TcourTechStuMap);

        return "/teacher/courseManager";
    }

    /**
     * 获取学生选课信息
     * @param cname 课程名
     * @param tname 教师名
     * @return
     */
    @ResponseBody
    @RequestMapping("/teacher/SelectCourseTech/getStudentList")
    public String getStudentList(@RequestParam("cname") String cname,
                                 @RequestParam("tname") String tname,
                                 HttpServletRequest request){

        String res;

        log.info("学生选课列表：" + " " + cname + " " + tname);

        try {

            List<Float> scoreList = new ArrayList<>();

            HashMap map = (HashMap) adminstratorService.getCourTechStuMap().get(cname);
            List<Student> list = (List) map.get(tname);

            for (Student student : list) {

                Grade build = Grade.builder()
                        .sid(student.getSId())
                        .cid(courseService.getCourseByName(cname).getCid())
                        .tid(teacherService.getTeacherByName(tname).getTId())
                        .build();

                Grade grade = gradeService.getGrade(student.getSId(), build.getCid(), build.getTid());
                if (grade == null)
                    scoreList.add(0.0f);
                else
                    scoreList.add(grade.getScore());

            }
            log.info("scoreList:" + scoreList);
            request.getSession().setAttribute("scoreList",scoreList);

            res = JSON.toJSONString(list);
        } catch (Exception e) {
            e.printStackTrace();
            res = "false";
        }


        return JSON.toJSONString(res);
    }

    @ResponseBody
    @RequestMapping("/teacher/SelectCourseTech/getScoreList")
    public String getScoreList(HttpServletRequest request){

        List<Float> scoreList = (List<Float>) request.getSession().getAttribute("scoreList");

        return JSON.toJSONString(scoreList);

    }

    @ResponseBody
    @RequestMapping("/teacher/courseMapper/changeScore")
    public String changeScore(HttpServletRequest request,
                              @RequestParam("cname") String cname,
                              @RequestParam("tname") String tname,
                              @RequestParam("uname") String uname,
                              @RequestParam("score") float score,
                              @RequestParam("row") int row){

        String res;

        Grade grade = Grade.builder()
                .tid(teacherService.getTeacherByName(tname).getTId())
                .sid(studentService.getStudentByName(uname).getSId())
                .cid(courseService.getCourseByName(cname).getCid())
                .score(score)
                .build();

        try {

            Grade tmp = gradeService.getGrade(grade.getSid(), grade.getCid(), grade.getTid());

            if (tmp == null){
                gradeService.insertGrade(grade);
            }else{
                gradeService.ModifyScore(grade);
            }

            res = "true";

        } catch (Exception e) {
            res = "false";
            e.printStackTrace();
        }

        List<Float> scoreList = (List<Float>) request.getSession().getAttribute("scoreList");
        scoreList.set(row,score);
        request.getSession().setAttribute("scoreList",scoreList);


        return JSON.toJSONString(res);
    }

}
