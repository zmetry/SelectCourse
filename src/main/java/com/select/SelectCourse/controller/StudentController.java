package com.select.SelectCourse.controller;

import com.alibaba.fastjson.JSON;
import com.select.SelectCourse.entity.Student;
import com.select.SelectCourse.entity.User;
import com.select.SelectCourse.service.impl.AdminstratorServiceImpl;
import com.select.SelectCourse.service.impl.StudentServiceImpl;
import com.select.SelectCourse.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class StudentController {

    @Autowired
    private AdminstratorServiceImpl adminstratorService;

    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private UserServiceImpl userService;

    /**
     * 管理员删除学生
     * @param sName
     * @return
     */
    @ResponseBody
    @RequestMapping("/admin/userList/deleteStu/{sName}")
    public String deleteStuByName(@PathVariable("sName") String sName) {

        log.info("即将删除学生:", sName);
        String res;
        try {
            userService.deleteUserByName(sName);
            studentService.removeStudent(sName);
            adminstratorService.DelStudent(sName);
            res = "true";
        } catch (Exception e) {
            res = "false";
            e.printStackTrace();
        }
        return JSON.toJSONString(res);
    }

    /**
     * 管理员修改学生
     * @param sid
     * @param sname
     * @param grade
     * @param classMsg
     * @return
     */
    @ResponseBody
    @RequestMapping("/admin/modifystu")
    public String modifyStu(@RequestParam("sid") int sid,
                            @RequestParam("sname") String sname,
                            @RequestParam("grade") String grade,
                            @RequestParam("classmsg") String classMsg) {

        String res;

        log.info("开始进行更改，信息为——sname:" + sname + ",grade:" + grade + ",classmsg:" + classMsg);

        Student student = Student.builder().sId(sid).Grade(grade)
                .classMsg(classMsg).sName(sname).build();

        User user = User.builder().uid(sid).uname(sname).build();

        try {
            if (!studentService.getStudentById(sid).getSName().equals(sname)){
                adminstratorService.ChangeStuName(studentService.getStudentById(sid).getSName(),sname);
            }

            adminstratorService.ModifyStudent(student);
            studentService.modifyStudent(student);
            userService.modifyUname(user);
            res = "true";
        } catch (Exception e) {
            e.printStackTrace();
            res = "false";
        }

        return JSON.toJSONString(res);
    }


    /**
     * 管理员插入学生
     * @param sname
     * @param grade
     * @param classMsg
     * @return
     */
    @ResponseBody
    @RequestMapping("/admin/insertStu")
    public String insertStu(@RequestParam("sname") String sname,
                            @RequestParam("grade") String grade,
                            @RequestParam("classmsg") String classMsg) {

        String res;

        log.info("开始进行消息的插入，信息为——sname:" + sname + ",grade:" + grade + ",classmsg:" + classMsg);

        User user = User.builder().uname(sname).password(sname).power(3).build();

        try {
            //先插入user，自动获取id
            userService.InsertUser(user);
            int sid = userService.getUserByName(sname).getUid();

            Student student = Student.builder().sId(sid).Grade(grade)
                    .classMsg(classMsg).sName(sname).build();
            studentService.insertStudent(student);

            adminstratorService.InsertStudent(sname);

            res = "true";

        } catch (Exception e) {
            e.printStackTrace();
            res = "false";
        }

        return JSON.toJSONString(res);
    }




}
