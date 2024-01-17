package com.cloudassest.intern.phone_book.repositories;

import com.cloudassest.intern.phone_book.model.EmailDetails;

public interface EmailService {
    // Method
    // To send a simple email
    String sendSimpleMail(EmailDetails details);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
}
