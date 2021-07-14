package com.select.SelectCourse.service.impl;

import com.select.SelectCourse.entity.Grade;
import com.select.SelectCourse.mapper.GradeMapper;
import com.select.SelectCourse.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    private GradeMapper gradeMapper;


    @Override
    public void insertGrade(Grade grade) {
        gradeMapper.insertGrade(grade);
    }

    @Override
    public void ModifyScore(Grade grade) {
        gradeMapper.ModifyScore(grade);
    }

    @Override
    public void deleteGrade(Grade grade) {
        gradeMapper.deleteGrade(grade);
    }

    @Override
    public Grade getGrade(int sid, int cid, int tid) {
        return gradeMapper.getGrade(sid,cid,tid);
    }

}
