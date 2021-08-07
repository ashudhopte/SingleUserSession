package com.project.singleusersessionspring;

import com.project.singleusersessionspring.dto.UserDto;
import com.project.singleusersessionspring.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "login-api")
@CrossOrigin
public class SingleUserRestController {
    
    @Autowired
    EmailService emailService;

    @GetMapping(value = "logout")
    public ResponseEntity<Boolean> logout(@RequestParam("email") String email){

        Boolean response = emailService.logOutUser(email);

        return new ResponseEntity<Boolean>(response, HttpStatus.OK);
    }

    @PostMapping(value="check-code")
    public ResponseEntity<Boolean> checkCode(@RequestBody UserDto userDto){

        Boolean response = emailService.checkLoginCode(userDto);

        return new ResponseEntity<Boolean>(response, HttpStatus.OK);
    }

    @GetMapping(value = "send-login-code")
    public ResponseEntity<UserDto> sendLoginCode(@RequestParam("email") String email){

        UserDto response = emailService.sendEmail(email);

        return new ResponseEntity<UserDto>(response, HttpStatus.OK);
    }

    @PostMapping(value = "check-session")
    public ResponseEntity<UserDto> checkEmailSession(@RequestBody UserDto userDto){

        UserDto response = emailService.checkSession(userDto);

        return new ResponseEntity<UserDto>(response, HttpStatus.OK);
    }

    @PostMapping(value = "remove-session-new-login")
    public ResponseEntity<UserDto> removeSessionNewLoginApi(@RequestBody UserDto userDto){

        UserDto response = emailService.removeSessionNewLogin(userDto);

        return new ResponseEntity<UserDto>(response, HttpStatus.OK);
    }
}
