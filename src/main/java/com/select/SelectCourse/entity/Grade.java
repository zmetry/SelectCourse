package com.select.SelectCourse.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Repository
public class Grade {

    private int gid;
    private int cid;
    private int sid;
    private int tid;
    private float score;

}
