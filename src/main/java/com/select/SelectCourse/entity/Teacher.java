package com.select.SelectCourse.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Repository;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Repository
@Builder
@ToString
/**
 * @tId： 主键
 * @tName：教师的姓名
 */
public class Teacher {

    private int tId;
    private String tName;
    private String Office;
    private String Station;

}
