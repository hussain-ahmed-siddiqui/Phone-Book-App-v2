package com.cloudasset.intern.phonebookappfrontend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {

public FrontendController(){

}


    @GetMapping("/error")
    public ResponseEntity<?> error(){

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/")
    public String redirect(){

            return "redirect:/login.html";

    }
    @GetMapping("/login")
    public String redirectToLogin(){

        return "redirect:/login.html";
    }



    @GetMapping("/register")
    public String signUp(){

        return "redirect:/signup.html";
    }
    @GetMapping("/accounts/verify-otp")
    public String otpPage(){
    return "redirect:/otp.html";
    }

    @GetMapping("/accounts/password-reset")
    public String reset(){
    return "redirect:/reset.html";
    }

    @GetMapping("/contacts/new")
    public String createContact(){


        return "redirect:/createContact.html";
    }

}
