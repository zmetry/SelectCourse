package com.select.SelectCourse.controller;

import com.alibaba.fastjson.JSON;
import com.select.SelectCourse.entity.Email;
import com.select.SelectCourse.entity.Student;
import com.select.SelectCourse.entity.User;
import com.select.SelectCourse.service.impl.AdminstratorServiceImpl;
import com.select.SelectCourse.service.impl.MailServiceImpl;
import com.select.SelectCourse.service.impl.StudentServiceImpl;
import com.select.SelectCourse.service.impl.UserServiceImpl;
import com.select.SelectCourse.util.JsoupUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Random;

@Slf4j
@Controller
@CrossOrigin
public class UserController {

    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private AdminstratorServiceImpl adminstratorService;

    @Autowired
    private UserServiceImpl userService;
    private JsoupUtil jsoupUtil = new JsoupUtil();

    @Autowired
    private MailServiceImpl mailService;

    @Autowired
    private JavaMailSenderImpl mailSender;

    @ResponseBody
    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @ResponseBody
    @RequestMapping("/login")
    public String login(@RequestParam("uname") String uname,
                        @RequestParam("password") String password,
                        HttpServletRequest request) throws IOException {

        log.info("进入登录管理，uname:" + uname + ",password:" + password);
        //用构造器获得到变量
        User flag = userService.checkUser(uname, password);

        HttpSession session = request.getSession(true);

        //配置新闻信息
        String dlutNews = (String) session.getAttribute("dlutNews");

        if (dlutNews == null || dlutNews.equals("")){
            dlutNews = jsoupUtil.getDLUTNews();
        }

        //配置用户信息
        session.setAttribute("mail",mailService.getMailById(flag.getUid()));
        session.setAttribute("user", flag);
        session.setAttribute("dlutNews", dlutNews);

        String res = (flag == null ? "false" : "MainPage");

        return JSON.toJSONString(res);
    }

    @ResponseBody
    @RequestMapping("/register")
    public String register(@RequestParam String uname,
                           @RequestParam String password) {

        log.info("进入注册管理，uname：" + uname + "，password：" + password);
        String res;
        User user = User.builder().uname(uname).password(password).power(3).build();
        if (userService.getUserByName(uname) == null) {
            userService.InsertUser(user);

            int sid = userService.getUserByName(uname).getUid();
            Student student = Student.builder().sId(sid).Grade("")
                    .classMsg("").sName(uname).build();
            studentService.insertStudent(student);

            adminstratorService.InsertStudent(uname);
            res = "true";
        } else
            res = "false";
        return JSON.toJSONString(res);
    }

    @ResponseBody
    @RequestMapping("/login/checkUser")
    public String checkUser(HttpServletRequest request,
                            @RequestParam("uname") String uname) {

        log.info("检查用户是否存在");
        String res;
        if (userService.getUserByName(uname) == null) {
            request.getSession().setAttribute("uname",uname);
            res = "false";
        }else{
            res = "true";
        }
        return JSON.toJSONString(res);
    }

    @ResponseBody
    @RequestMapping("/login/changePwd")
    public String changePwd(HttpServletRequest request,
                            @RequestParam("uname") String uname,
                            @RequestParam("pwd") String pwd){

        log.info("开始修改密码，uname：" + uname + "，pwd: " + pwd);
        String res;

        //判断是否直接进入
        if (request.getSession().getAttribute("uname") == null){
            log.info("修改密码失败");
            res = "false";
        }else{
            log.info("修改密码成功");
            User user = userService.getUserByName(uname);
            user.setPassword(pwd);
            userService.modifyPassword(user);
            res = "true";
        }

        return JSON.toJSONString(res);
    }

    @ResponseBody
    @RequestMapping("/login/SendMail/{mail}")
    public String SendMail(@PathVariable("mail") String mail) {

        //获取随机数
        String res = getRandomString(6);

        log.info("开始发送邮箱");
        //发送邮件
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("2236913183@qq.com");
        simpleMailMessage.setSubject("找回密码");
        simpleMailMessage.setText("您的验证码为：" + res);
        simpleMailMessage.setTo(mail);
        mailSender.send(simpleMailMessage);

        log.info("res = " + res);

        return JSON.toJSONString(res);
    }

    @ResponseBody
    @RequestMapping("/login/checkMail")
    public String checkMail(HttpServletRequest request,
                            @RequestParam("uname") String uname,
                            @RequestParam("mail") String mail) {

        log.info("检查邮箱是否正确");
        String res;
        String str = mailService.getMailById(userService.getUserByName(uname).getUid());

        request.getSession().setAttribute("uname",uname);

        if (str != null && str.equals(mail)){
            res = "/login/SendMail/"+mail;
        }else
            res = "false";

        return JSON.toJSONString(res);
    }

    @ResponseBody
    @RequestMapping("/user/changeMail")
    public String changeMail(HttpServletRequest request,
                            @RequestParam("uname") String uname,
                            @RequestParam("email") String email) {

        log.info("检查邮箱是否正确");
        String res;


        if (request.getSession().getAttribute("user") != null){
            mailService.changeMailById(Email.builder().uid(userService.getUserByName(uname).getUid()).mail(email).build());
            request.getSession().setAttribute("mail",email);
            res = "true";
        }else
            res = "false";



        return JSON.toJSONString(res);
    }

    @ResponseBody
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        String res = "index";
        request.getSession().removeAttribute("user");
        return JSON.toJSONString(res);
    }

    //生成随机数函数
    private String getRandomString(int length) {
        String str = "abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ1234567890";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length; i++) {
            // 从1-62的字符中随机取出一位
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}
