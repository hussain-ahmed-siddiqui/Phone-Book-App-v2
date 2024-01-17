package com.cloudassest.intern.phone_book.service;

import com.cloudassest.intern.phone_book.model.EmailDetails;
import com.cloudassest.intern.phone_book.model.Otp;
import com.cloudassest.intern.phone_book.model.User;
import com.cloudassest.intern.phone_book.model.Contact;
import com.cloudassest.intern.phone_book.repositories.ContactRepository;
import com.cloudassest.intern.phone_book.repositories.OtpRepository;
import com.cloudassest.intern.phone_book.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

@Service
public class PhoneServices {
@Autowired
    ContactRepository contactRepository;
@Autowired
    UserRepository userRepository;
@Autowired
    OtpRepository otpRepository;
@Autowired
EmailServiceImpl emailService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public HttpSession currentSession(){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(false);
    }
    public User getCurrentUser() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false); // true == allow create
        String phoneNum = (String) session.getAttribute("phoneNum");

        return findUser(phoneNum);
    }
    //for fetching user account
    public User findUser(String phoneNum) {
        return userRepository.findByPhoneNum(phoneNum);
    }
    //for fetching the contacts associated with the user that is found by the findByUsername() function
    public List<Contact> findByUser(User currentUser) {
        return contactRepository.findByUser(currentUser);
    }

    public ResponseEntity<?> registerNewUser(String userName,String password, String phoneNum, String email){
        if(userRepository.findByPhoneNum(phoneNum)!=null){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Registration failed: An account with that phone number already exists.");
        }
        String hashedPassword = passwordEncoder.encode(password);
        User newUser = new User(userName,hashedPassword,phoneNum);
        if(email!=null) {
            newUser.setEmail(email);
        }
        userRepository.save(newUser);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/login");
        return new ResponseEntity<>(headers,HttpStatus.FOUND);
    }

    public ResponseEntity<?> AuthenticateUser(String phoneNum, String password){
        User user = userRepository.findByPhoneNum(phoneNum);
        HttpHeaders headers = new HttpHeaders();


        if(!passwordEncoder.matches(password, user.getPassword())){
            headers.add("Location", "/login");
            return new ResponseEntity<>(headers,HttpStatus.FOUND);
        }
        if(user==null){
            headers.add("Location", "/login");
            return new ResponseEntity<>(headers,HttpStatus.FOUND);
        }
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        session.setAttribute("phoneNum", phoneNum);
        session.setAttribute("userId",user.getId());
        session.setMaxInactiveInterval(30*60);
        headers.add("Location", "/contacts/list");
        return new ResponseEntity<>(headers,HttpStatus.FOUND);
    }

    public ResponseEntity<?> addContact(String firstName, String middleName, String lastName, String phone, String email) {
        Contact contact = new Contact(firstName,phone);
        contact.setName(middleName,lastName);
        contact.setEmail(email);
        contact.setUser(getCurrentUser());
        contactRepository.save(contact);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/contacts/list");
        return new ResponseEntity<>(headers,HttpStatus.FOUND);

    }


    public void updateContact(String id, String name, String phoneNum, String email) {
        contactRepository.findById(id).map(
                existingContact -> {
                    existingContact.setName(name);
                    existingContact.setPhoneNum(phoneNum);
                    existingContact.setEmail(email);
                    return contactRepository.save(existingContact);
                })
                .orElseThrow(()->new RuntimeException("Contact not found with id " + id));


    }

    public List<Contact> searchContact(String query) {

        String regex = "^" + Pattern.quote(query);
        return contactRepository.findByUserNameRegex(regex, (String) currentSession().getAttribute("userId"));
    }

    public ResponseEntity<?> sendOtp(String phoneNum) {
        User user = findUser(phoneNum);
        HttpHeaders headers = new HttpHeaders();
        if(user!=null) {
            String email = user.getEmail();
            Random random = new Random();
            int otp = 1000 + random.nextInt(9000);
            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setRecipient(email);
            emailDetails.setSubject("Password reset code from Phone Book App");
            emailDetails.setMsgBody("Your One Time Password is" + otp);
            Otp otpObject = new Otp();

            otpObject.setOtp(String.valueOf(otp));
            if (emailService.sendSimpleMail(emailDetails) == "1") {
                otpRepository.save(otpObject);

                headers.add("Location", "/accounts/verify-otp");

            } else {
                headers.add("Location", "/forgot-password");

            }
        }
        return new ResponseEntity<>(headers, HttpStatus.FOUND);

    }

    public ResponseEntity<?> verifyOtp(String otp) {
        Otp otpObject = new Otp();
        otpObject.setOtp(otp);

        try {
            otpObject = otpRepository.findByOtp(otp);

        HttpHeaders headers= new HttpHeaders();
        if(otpObject!=null){
            otpRepository.delete(otpObject);

            headers.add("Location","/accounts/password-reset");
            return new ResponseEntity<>(headers,HttpStatus.FOUND);
        }
        else{
            headers.add("Location","/accounts/verify-otp");
            return new ResponseEntity<>(headers,HttpStatus.FOUND);
        }
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }
        return null;
    }

    public ResponseEntity<?> resetPass(String newPassword,String phoneNum) {
        String hashedPassword = passwordEncoder.encode(newPassword);

        HttpHeaders headers = new HttpHeaders();
        User user = userRepository.findByPhoneNum(phoneNum);
            userRepository.findById(user.getId()).map(
                    existing_user-> {
                        existing_user.setPassword(hashedPassword);
                        return userRepository.save(existing_user);
                    }
            )
                    .orElseThrow(()->new RuntimeException("Contact not found with id " + user.getId()));

        System.out.println("*********************************");

        headers.add("Location","/login");
        return new ResponseEntity<>(headers,HttpStatus.FOUND);
    }
}
