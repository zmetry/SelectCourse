package com.select.SelectCourse.entity;

import lombok.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Repository
@Builder

/**
 * @cid: 主键
 * @Name: 课程名称
 * @score： 课程的学分
 * @startLesson： 每天课程开始是第几节课
 * @endLession： 每天课程结束是第几节课
 * @startTime： 课程的开始时间
 * @endTime： 课程的结束时间
 */
public class Course {

    private int cid;
    private String Name;
    private float score;
    private int startLesson;
    private int endLesson;
    private int startWeek;
    private int endWeek;

}
