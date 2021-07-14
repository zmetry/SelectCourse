package com.select.SelectCourse.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Repository
@Builder
public class Student {

    @Id
    private int sId;
    private String sName;
    private String Grade;
    private String classMsg;

}
