package com.select.SelectCourse.entity;

import lombok.*;
import org.springframework.stereotype.Repository;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Repository
@Builder
public class User {

    private int uid;
    private String uname;
    private String password;
    /**
     * 用以区分 1表示管理员，2表示教师，3表示学生
     */
    private int power;



}
