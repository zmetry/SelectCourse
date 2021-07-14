package com.select.SelectCourse.service.impl;

import com.alibaba.fastjson.JSON;
import com.select.SelectCourse.entity.Course;
import com.select.SelectCourse.entity.CourseSelector;
import com.select.SelectCourse.service.CourseSelectorService;
import com.select.SelectCourse.util.JsonUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
@Service
public class CourseSelectorServiceImpl implements CourseSelectorService {

    @Autowired
    private CourseServiceImpl courseService;

    private static JsonUtil jsonUtil = new JsonUtil();

    private static boolean flag = false;

    private static List<CourseSelector> courseSelectors;
    private static CourseSelectorServiceImpl courseSelectorService = new CourseSelectorServiceImpl();


    public synchronized void init(){
        log.info("Selector进行初始化");
        //对courseSelectors进行初始化
        if (jsonUtil.GetSelector() == null || jsonUtil.GetSelector().equals("")){
            courseSelectors = new ArrayList<>();
            List<Course> courseList = courseService.getAllCourse();
            for (Course course : courseList) {
                //该课程的老师等初始话的时候在进行添加
                CourseSelector selector = CourseSelector.builder().course(course).stock(0).capacity(40).build();
                courseSelectors.add(selector);
            }
            jsonUtil.WriterSelectorJson(JSON.toJSONString(courseSelectors));
        }else{
            String selector = jsonUtil.GetSelector();
            courseSelectors = JSON.parseArray(selector, CourseSelector.class);
        }
        flag = true;
        log.info("Selector初始化结束");
    }

    @Override
    public List<CourseSelector> getAllCourseSelector() {
        if (!flag){
            init();
        }
        return courseSelectors;
    }

    @Override
    public synchronized void InsertCourseSelector(CourseSelector courseSelector) {
        if (!flag){
            init();
        }
        courseSelectors.add(courseSelector);
        jsonUtil.WriterSelectorJson(JSON.toJSONString(courseSelectors));
    }

    /**
     *
     * @param name 课程名
     * @param tName 老师名称
     */
    @Override
    public synchronized void AddTeacher(String name, String tName) {
        if (!flag){
            init();
        }
        log.info("selector开始设置教师");

        for (CourseSelector selector : courseSelectors) {
            if (selector.getCourse().getName().equals(name)){
                selector.setTName(tName);
                jsonUtil.WriterSelectorJson(JSON.toJSONString(courseSelectors));
                break;
            }
        }

        log.info("selector设置教师结束");
    }

    @Override
    public synchronized void SelectCourse(String name) {
        if (!flag){
            init();
        }
        for (CourseSelector courseSelector : courseSelectors) {
            if (courseSelector.getCourse().getName().equals(name)){
                int stock = courseSelector.getStock() + 1;
                courseSelector.setStock(stock);
                jsonUtil.WriterSelectorJson(JSON.toJSONString(courseSelectors));
                break;
            }
        }
    }

    @Override
    public synchronized void dropCourse(String name) {
        if (!flag){
            init();
        }
        for (CourseSelector courseSelector : courseSelectors) {
            if (courseSelector.getCourse().getName().equals(name)){
                int stock = courseSelector.getStock() - 1;
                courseSelector.setStock(stock);
                jsonUtil.WriterSelectorJson(JSON.toJSONString(courseSelectors));
                break;
            }
        }
    }

    @Override
    public void InsertCourse(Course course) {
        if (!flag){
            init();
        }
        CourseSelector courseSelector = CourseSelector.builder().course(course).stock(0).capacity(40).build();
        InsertCourseSelector(courseSelector);
    }

    @Override
    public void delCourse(String name) {
        if (!flag){
            init();
        }
        for (CourseSelector courseSelector : courseSelectors) {
            if (courseSelector.getCourse().getName().equals(name)){
                courseSelectors.remove(courseSelector);
                break;
            }
        }
        jsonUtil.WriterSelectorJson(JSON.toJSONString(courseSelectors));
    }

    @Override
    public void delTech(String tname) {
        if (!flag){
            init();
        }
        for (CourseSelector courseSelector : courseSelectors) {
            if (courseSelector.getTName().equals(tname)){
                courseSelector.setStock(0);
                courseSelector.setTName("");
            }
        }
        jsonUtil.WriterSelectorJson(JSON.toJSONString(courseSelectors));
    }

    @Override
    public void ModifyTech(String bName, String cName) {
        if (!flag){
            init();
        }
        for (CourseSelector courseSelector : courseSelectors) {
            if (courseSelector.getTName().equals(bName)){
                courseSelector.setStock(0);
                courseSelector.setTName(cName);
            }
        }
        jsonUtil.WriterSelectorJson(JSON.toJSONString(courseSelectors));
    }

    @Override
    public void ChangeCourse(Course course) {
        if (!flag){
            init();
        }
        for (CourseSelector courseSelector : courseSelectors) {
            if (courseSelector.getCourse().getCid() == course.getCid()){
                courseSelector.setCourse(course);
            }
        }
        jsonUtil.WriterSelectorJson(JSON.toJSONString(courseSelectors));
    }
}
