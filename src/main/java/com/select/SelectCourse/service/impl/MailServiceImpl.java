package com.select.SelectCourse.service.impl;

import com.select.SelectCourse.entity.Email;
import com.select.SelectCourse.mapper.MailMapper;
import com.select.SelectCourse.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private MailMapper mailMapper;

    @Override
    public String getMailById(int uid) {
        return mailMapper.getMailById(uid);
    }

    @Override
    public void changeMailById(Email email) {
        mailMapper.changeMailById(email);
    }
}
