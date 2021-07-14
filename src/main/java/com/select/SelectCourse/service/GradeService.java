package com.select.SelectCourse.service;

import com.select.SelectCourse.entity.Grade;

public interface GradeService {

    public void  insertGrade(Grade grade);
    public void ModifyScore(Grade grade);
    public void deleteGrade(Grade grade);
    public Grade getGrade(int sid,int cid,int tid);

}
