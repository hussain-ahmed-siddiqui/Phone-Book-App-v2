package com.cloudassest.intern.phone_book.repositories;

import com.cloudassest.intern.phone_book.model.Otp;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OtpRepository extends MongoRepository<Otp,String> {
    Otp findByOtp(String otp);

}
