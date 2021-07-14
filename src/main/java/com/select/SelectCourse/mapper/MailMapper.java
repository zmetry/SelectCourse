package com.select.SelectCourse.mapper;

import com.select.SelectCourse.entity.Email;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MailMapper {

    public String getMailById(int uid);
    public void changeMailById(Email email);

}
