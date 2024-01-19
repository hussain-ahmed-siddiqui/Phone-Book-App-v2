package com.cloudassest.intern.phone_book.controller;

import com.cloudassest.intern.phone_book.model.Contact;
import com.cloudassest.intern.phone_book.service.PhoneServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class ApiController {

    private final PhoneServices phoneServices;
    public ApiController(PhoneServices phoneServices){
        this.phoneServices=phoneServices;
    }



    @Operation(summary =  "Get all contacts of a user")
    @ApiResponse(responseCode = "200",description = "Successful operation", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Contact.class, type = "array")
    ))
    @GetMapping("/contacts")
    public ResponseEntity<?> listUserContacts() {
//        System.out.println(phoneServices.currentSession().getId());

        return phoneServices.findByUser();
    }
    @Operation(summary = "Save new contact to database")
    @ApiResponse(responseCode = "303",description = "Redirect to Contacts list page")
    @PostMapping("/contacts/new")
    public ResponseEntity<?> newContact(@RequestParam String first_name, @RequestParam String middle_name, @RequestParam String last_name, @RequestParam String email, @RequestParam String phone){

        return phoneServices.addContact(first_name,middle_name,last_name,phone,email);
    }
    @PostMapping("/register")
    public ResponseEntity<?> signDownxD(@RequestParam String name,@RequestParam String password, @RequestParam String phoneNum, @RequestParam String email){

        return phoneServices.registerNewUser(name,password,phoneNum,email);
    }

    @Operation(summary = "logs in the user")
    @ApiResponse(responseCode = "200",description = "Redirect to Contacts list page")
    @PostMapping("/login")
    public ResponseEntity<?> getForm(@RequestParam String phoneNum, @RequestParam String password){
//        System.out.println(name+" "+password);
//        System.out.println(phoneServices.currentSession().getId());
        return phoneServices.AuthenticateUser(phoneNum,password);
    }

    @PutMapping("/contacts/edit")
    public ResponseEntity<?> saveEdits(@RequestParam String _id, @RequestParam String name, @RequestParam String phoneNum, @RequestParam String email){
        System.out.println("blyat");
        return phoneServices.updateContact(_id,name,phoneNum,email);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> sendMail(@RequestParam String phoneNum){
//        System.out.println("hell yeah");
        return phoneServices.sendOtp(phoneNum);
    }
    @PostMapping("/OTP")
    public ResponseEntity<?> checkOTP(@RequestParam String otp,@RequestParam String phoneNum){
        return phoneServices.verifyOtp(otp,phoneNum);
    }
    @PostMapping("/accounts/password-reset")
    public ResponseEntity<?> reset(@RequestParam String password, @RequestParam String phoneNum){
        return phoneServices.resetPass(password,phoneNum);
    }
    @GetMapping("/contacts/search")
    public ResponseEntity<?> searchContacts(@RequestParam String query){
        return phoneServices.searchContact(query);
    }

    @GetMapping("/sessionValidation")
    public ResponseEntity<?> check(HttpServletRequest request){
       return phoneServices.check(request);
    }

    @GetMapping("/performLogout")
    public ResponseEntity<?> logout(){
        return phoneServices.checkOut();
    }
}
