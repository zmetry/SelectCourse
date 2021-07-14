package com.select.SelectCourse.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Data
@Repository
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CourseSelector {

    private int capacity;
    private int stock;
    private Course course;
    private String tName;

}
