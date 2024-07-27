package com.shyam.services;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.shyam.entities.UserEntity;
import com.shyam.utils.RequestUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class EmailService {
    
    @Autowired
    RequestUtils requestUtils;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    Configuration configuration;

    public void sendSampleMail()  {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            Writer out = new StringWriter();  

            HashMap<String, Object> map = new HashMap<>(); 
            map.put("activationLink", "http://localhost:8080/");
            map.put("userName", "KarnamShyam1947");

            helper.setFrom("shyam@gmail.com", "KarnamShyam1947");
            helper.setTo("karnamshyam9009@gmail.com");
            helper.setSubject("test mail");

            Template template = configuration.getTemplate("activate.ftl");

            template.process(map, out);
            
            helper.setText(out.toString(), true);

            javaMailSender.send(mimeMessage);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("null")
    public void sendActivationEmail(UserEntity user) {
        System.out.println("Sending activation mail.........");
        
        HttpServletRequest request = requestUtils.getHttpRequest();
        HashMap<String, Object> map = new HashMap<>();
        Writer out = new StringWriter();

        String link = request.getRequestURL().toString().replace(request.getServletPath(), "");
        link = link + "/activate-user/"+user.getUniqueToken();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            helper.setFrom("vitap.library@gmail.com", "Admin");
            helper.setTo(user.getEmail());
            helper.setSubject("User activation email");

            Template template = configuration.getTemplate("activate.ftl");

            map.put("userName", user.getUsername());
            map.put("activationLink", link);
            template.process(map, out);

            helper.setText(out.toString(), true);

            javaMailSender.send(mimeMessage);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("null")
    public void sendForgotPasswordEmail(UserEntity user) {
        HttpServletRequest request = requestUtils.getHttpRequest();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            Writer out = new StringWriter();
            HashMap<String, Object> map = new HashMap<>();
            map.put("userName", user.getUsername());

            String link = request.getRequestURL().toString().replace(request.getServletPath(), "");
            link = link + "/set-password/"+user.getUniqueToken();
            map.put("link", link);

            helper.setFrom("vitap.library@gmail.com", "Admin");
            helper.setTo(user.getEmail());
            helper.setSubject("Forgot password email");

            Template template = configuration.getTemplate("forgot-password-email.ftl");
            template.process(map, out);

            helper.setText(out.toString(), true);

            javaMailSender.send(mimeMessage);

        } 
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}