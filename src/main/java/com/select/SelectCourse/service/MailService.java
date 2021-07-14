package com.select.SelectCourse.service;


import com.select.SelectCourse.entity.Email;

public interface MailService {

    public String getMailById(int uid);
    public void changeMailById(Email email);

}
