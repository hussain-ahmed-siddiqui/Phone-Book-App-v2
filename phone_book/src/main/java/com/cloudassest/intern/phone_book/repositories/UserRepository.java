package com.cloudassest.intern.phone_book.repositories;

import com.cloudassest.intern.phone_book.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
    User findByPhoneNum(String phoneNum);
}
