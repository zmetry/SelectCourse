package com.select.SelectCourse.service.impl;

import com.alibaba.fastjson.JSON;
import com.select.SelectCourse.entity.Administrator;
import com.select.SelectCourse.entity.Course;
import com.select.SelectCourse.entity.Student;
import com.select.SelectCourse.service.AdministratorService;
import com.select.SelectCourse.util.JsonUtil;
import com.select.SelectCourse.util.RedisUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.*;

/**
 * @eadme: 由于Adminstrator要存的数据过多，所以直接存入到json数据，方便读取r
 */
@Slf4j
@Data
@Service
public class AdminstratorServiceImpl implements AdministratorService {

    @Autowired
    private CourseServiceImpl courseService;

    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private JsonUtil jsonUtil;
    private static Administrator administrator;
    //选课管理界面
    private static HashMap<String,HashMap<String,List<Student>>> courTechStuMap = new HashMap<>();
    //学生选课清单
    //需要联合两个hashmap进行查找
    private static HashMap<String,List<Course>> stuCourMap = new HashMap<>();

    //是否进行初始化的判断
    private static boolean flag = false;

    //在开始就初始化
    private void init() {

        if (jsonUtil.getSelectCourseJson() != null && !jsonUtil.getSelectCourseJson().equals("")) {
            log.info("json中存在Administrator");
            String adminStr = jsonUtil.getSelectCourseJson();
            administrator = JSON.parseObject(adminStr,Administrator.class);
            courTechStuMap = administrator.getCourTechStuMap();
            stuCourMap = administrator.getStuCourMap();
        } else {
            log.info("json中不存在Administrator");

            //处理选课管理界面
            List<Course> courseList = courseService.getAllCourse();
            administrator = new Administrator();
            //将所有的课程进行赋值给administrator,对courTechStuMap进行初始化
            for (Course course : courseList)
                courTechStuMap.put(course.getName(),new HashMap<>());
            administrator.setCourTechStuMap(courTechStuMap);

            //处理学生选课的列表
            List<Student> students = studentService.getAllStudent();
            for (Student student : students) {
                stuCourMap.put(student.getSName(),new ArrayList<>());
            }
            administrator.setStuCourMap(stuCourMap);

            //持久化
            jsonUtil.WriteSelectCourseJson(JSON.toJSONString(administrator));
        }
        flag = true;
    }


    @Override
    public HashMap getCourTechStuMap() {
        if (!flag){
            init();
        }
        return courTechStuMap;
    }

    @Override
    public HashMap getTechStuMap(String courName) {
        if (!flag){
            init();
        }
        return courTechStuMap.get(courName);
    }


    /**
     *
     * @param name 课程名
     * @param tName 老师名
     */
    @Override
    public void ChangeCoureTech(String name, String tName) {
        log.info("进入选课管理修改系统");
        if (!flag){
            init();
        }
        HashMap<String, List<Student>> map = courTechStuMap.get(name);
        if (map.size() == 0){
            log.info("map为空");
            map.put(tName, new LinkedList<>());
        }else{
            log.info("map不为空");
            String firstKey = map.keySet().iterator().next();
            List<Student> students = map.get(firstKey);
            map.remove(firstKey);
            map.put(tName,students);
        }
        administrator.setCourTechStuMap(courTechStuMap);
        jsonUtil.WriteSelectCourseJson(JSON.toJSONString(administrator));
    }

    @Override
    public void ChangeCourName(String bName, String aName) {
        if (!flag){
            init();
        }
        HashMap<String, List<Student>> map = courTechStuMap.get(bName);
        courTechStuMap.remove(bName);
        courTechStuMap.put(aName,map);

        administrator.setCourTechStuMap(courTechStuMap);
        jsonUtil.WriteSelectCourseJson(JSON.toJSONString(administrator));
    }

    @Override
    public void ChangeStuName(String bName, String aName) {
        if (!flag){
            init();
        }
        for (String stuName : stuCourMap.keySet()) {
            if (stuName.equals(bName)){
                List<Course> courseList = stuCourMap.get(stuName);
                stuCourMap.remove(stuName);
                stuCourMap.put(aName,courseList);
            }
        }

        administrator.setStuCourMap(stuCourMap);
        jsonUtil.WriteSelectCourseJson(JSON.toJSONString(administrator));
    }

    @Override
    public void ModifyStudent(Student student) {
        for (String courStr : courTechStuMap.keySet()) {
            HashMap<String, List<Student>> map = courTechStuMap.get(courStr);
            for (String techStr : map.keySet()) {
                List<Student> students = map.get(techStr);
                for (Student stu : students) {
                    if (stu.getSId() == student.getSId()){
                        stu.setClassMsg(student.getClassMsg());
                        stu.setGrade(student.getGrade());
                        stu.setSName(stu.getSName());
                    }
                }
            }
        }

        administrator.setCourTechStuMap(courTechStuMap);
        jsonUtil.WriteSelectCourseJson(JSON.toJSONString(administrator));
    }

    @Override
    public void InsertStudent(String sName) {
        if (!flag){
            init();
        }
        stuCourMap.put(sName,new ArrayList<>());
        administrator.setStuCourMap(stuCourMap);

        /*持久化*/
        jsonUtil.WriteSelectCourseJson(JSON.toJSONString(administrator));
    }

    @Override
    public void DelStudent(String sName) {
        if (!flag)
            init();
        if (stuCourMap.containsKey(sName)){
            stuCourMap.remove(sName);

            administrator.setStuCourMap(stuCourMap);
            /*持久化*/
            jsonUtil.WriteSelectCourseJson(JSON.toJSONString(administrator));
        }
    }


    @Override
    public synchronized void AddStudent(String uname, String tname, String cname) {
        if (!flag){
            init();
        }
        //添加进多个表

        /*处理选课管理界面*/
        HashMap map = courTechStuMap.get(cname);
        List list = (List) map.get(tname);
        list.add(studentService.getStudentByName(uname));
        administrator.setCourTechStuMap(courTechStuMap);

        /*处理学生选课的列表*/
        List<Course> courseList = stuCourMap.get(uname);
        courseList.add(courseService.getCourseByName(cname));
        administrator.setStuCourMap(stuCourMap);

        /*持久化*/
        jsonUtil.WriteSelectCourseJson(JSON.toJSONString(administrator));
    }

    @Override
    public void AddCourse(String cname) {
        if (!flag){
            init();
        }
        //注册课程
        courTechStuMap.put(cname,new HashMap<>());
        administrator.setCourTechStuMap(courTechStuMap);

        /*持久化*/
        jsonUtil.WriteSelectCourseJson(JSON.toJSONString(administrator));
    }

    @Override
    public void DelCourse(String cname) {
        if (!flag)
            init();
        for (String str : courTechStuMap.keySet()) {
            if (str.equals(cname)){
                courTechStuMap.remove(cname);
                break;
            }
        }
        administrator.setCourTechStuMap(courTechStuMap);

        /*持久化*/
        jsonUtil.WriteSelectCourseJson(JSON.toJSONString(administrator));
    }

    @Override
    public void DelTech(String tName) {
        if (!flag)
            init();

        for (String cName : courTechStuMap.keySet()) {
            HashMap<String, List<Student>> map = courTechStuMap.get(cName);
            if (map.containsKey(tName)){
                List<Student> students = map.get(tName);
                map.remove(tName);
                for (Student student : students) {
                    //循环删除，学生相应的选课信息
                    if (stuCourMap.containsKey(student.getSName())){
                        List<Course> courseList = stuCourMap.get(student.getSName());
                        courseList.remove(courseService.getCourseByName(cName));
                    }
                }
            }
        }

        administrator.setStuCourMap(stuCourMap);
        administrator.setCourTechStuMap(courTechStuMap);

        /*持久化*/
        jsonUtil.WriteSelectCourseJson(JSON.toJSONString(administrator));

    }

    @Override
    public HashMap getstuCourMap() {
        if (!flag)
            init();
        return stuCourMap;
    }

    @Override
    public void dropStudent(String uname, String tname, String cname) {
        if (!flag){
            init();
        }

        //添加进多个表

        /*处理选课管理界面*/
        HashMap map = courTechStuMap.get(cname);
        List list = (List) map.get(tname);
        list.remove(studentService.getStudentByName(uname));
        administrator.setCourTechStuMap(courTechStuMap);

        /*处理学生选课的列表*/
        List<Course> courseList = stuCourMap.get(uname);
        courseList.remove(courseService.getCourseByName(cname));
        administrator.setStuCourMap(stuCourMap);

        /*持久化*/
        jsonUtil.WriteSelectCourseJson(JSON.toJSONString(administrator));

    }

    @Override
    public void ModifyCourseMsg(Course course) {
        if (!flag){
            init();
        }

        for (String stu : stuCourMap.keySet()) {
            List<Course> courseList = stuCourMap.get(stu);
            for (Course cour : courseList) {
                if (cour.getCid() == course.getCid()){
                    cour.setName(course.getName());
                    cour.setScore(course.getScore());
                    cour.setStartLesson(course.getStartLesson());
                    cour.setEndLesson(course.getEndLesson());
                    cour.setStartWeek(course.getStartWeek());
                    cour.setEndWeek(course.getEndWeek());
                }
            }
        }
        administrator.setStuCourMap(stuCourMap);
        /*持久化*/
        jsonUtil.WriteSelectCourseJson(JSON.toJSONString(administrator));
    }


}
