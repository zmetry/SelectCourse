package com.select.SelectCourse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Repository
@ToString
public class Administrator {

    //参数。课程名，教师名，学生列表
    private HashMap<String,HashMap<String,List<Student>>> courTechStuMap;
    private HashMap<String,List<Course>> stuCourMap ;

}
