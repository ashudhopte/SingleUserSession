package com.project.singleusersessionspring.service;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import com.project.singleusersessionspring.ProjectConst;
import com.project.singleusersessionspring.dto.UserDto;
import com.project.singleusersessionspring.entity.EmailLoginCodeEntity;
import com.project.singleusersessionspring.entity.UserEntity;
import com.project.singleusersessionspring.repo.EmailLoginCodeRepo;
import com.project.singleusersessionspring.repo.UserRepo;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    EmailLoginCodeRepo emailLoginCodeRepo;

    public Boolean logOutUser(String email){

        UserEntity userEntity = userRepo.findByEmail(email);

        if(userEntity != null){

            userEntity.setSessionActive(false);
            userRepo.save(userEntity);
            return true;
        }

        return false;
    }

    public Boolean checkLoginCode(UserDto userDto){

        EmailLoginCodeEntity emailLoginCodeEntity = emailLoginCodeRepo.findByEmail(userDto.getEmail());

        if(emailLoginCodeEntity != null && emailLoginCodeEntity.getNewLoginCode() == userDto.getLoginCode()){

            return true;
        }

        return false;
    }

    public UserDto removeSessionNewLogin(UserDto userDto){

        if(!checkLoginCode(userDto)){

            userDto.setMessage("Code entered is incorrect.");
            userDto.setSessionActive(false);

            return userDto;
        }

        UserEntity userEntity = userRepo.findByEmail(userDto.getEmail());

        if(userEntity != null){

            userEntity.setLastLoginDate(new Date());
            userEntity.setLoginCode(userDto.getLoginCode());
            userEntity.setSessionActive(true);
            
            userRepo.save(userEntity);

            userDto.setLastLoginDate(new Date());
            userDto.setSessionActive(true);
            userDto.setMessage("Login Successful.");
        }

        return userDto;
    }

    public UserDto checkSession(UserDto userDto){

        // if(!checkLoginCode(userDto)){

        //     userDto.setMessage("Code entered is incorrect.");
        //     userDto.setSessionActive(false);

        //     return userDto;
        // }

        UserEntity userEntity = userRepo.findByEmail(userDto.getEmail());

        if(userEntity == null){
            userDto = loginEmail(userDto);
        }
        
        else if(userEntity != null && !userEntity.getSessionActive()){
            userDto.setUserId(userEntity.getUserId());
            userDto = loginEmail(userDto);
        }
        
        else if(userEntity != null && userEntity.getSessionActive()){
            userDto.setLastLoginDate(userEntity.getLastLoginDate());
            userDto = sessionActiveResponse(userDto);
        }

        if(userEntity != null && userEntity.getSessionActive() && userEntity.getLoginCode() == userDto.getLoginCode()){
            userDto.setLastLoginDate(userEntity.getLastLoginDate());
            userDto.setSessionActive(true);
        }

        return userDto;
    }

    public UserDto sessionActiveResponse(UserDto userDto){

        userDto.setMessage("Session is active on another device.");
        userDto.setSessionActive(false);

        return userDto;
    }

    public UserDto loginEmail(UserDto userDto){

        UserEntity userEntity = new UserEntity();
    
        userEntity.setEmail(userDto.getEmail());
        userEntity.setLoginCode(userDto.getLoginCode());
        userEntity.setUserId(userDto.getUserId());
        userEntity.setLastLoginDate(new Date());
        userEntity.setSessionActive(true);
        
        userRepo.save(userEntity);

        userDto.setLastLoginDate(new Date());
        userDto.setMessage("Login Successful.");
        userDto.setSessionActive(true);
    
        return userDto;
    }


    public UserDto sendEmail(String email) {

        UserDto userDto = new UserDto();
        userDto.setEmail(email);

        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");

            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(ProjectConst.MYEMAIL, ProjectConst.MYPASS);
                }
            });

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(ProjectConst.MYEMAIL, false));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            msg.setSubject("4 Digit Login Code.");

            int randomNumber = 1000 + new Random().nextInt(8999);

            msg.setContent("Your 4 digit login code is: " + randomNumber, "text/html");
            msg.setSentDate(new Date());

            Transport.send(msg);

            EmailLoginCodeEntity emailLoginCodeEntity = emailLoginCodeRepo.findByEmail(email);

            if(emailLoginCodeEntity == null){

                emailLoginCodeEntity = new EmailLoginCodeEntity();
            
            }
            emailLoginCodeEntity.setEmail(email);
            emailLoginCodeEntity.setNewLoginCode(randomNumber);
            emailLoginCodeEntity.setLoginCodeSendDate(new Date());

            emailLoginCodeRepo.save(emailLoginCodeEntity);

            userDto.setEmailSent(true);
            userDto.setMessage("Email Sent Successfully to "+ email);
            

        } catch (Exception e) {
            userDto.setEmailSent(true);
            userDto.setMessage("Email Not Sent.");
            e.printStackTrace();
        }

        return userDto;

    }
}
