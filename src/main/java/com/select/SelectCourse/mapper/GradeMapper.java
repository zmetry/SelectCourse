package com.select.SelectCourse.mapper;

import com.select.SelectCourse.entity.Grade;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GradeMapper {

    public void  insertGrade(Grade grade);
    public void ModifyScore(Grade grade);
    public void deleteGrade(Grade grade);
    public Grade getGrade(int sid,int cid,int tid);

}
