package com.cloudassest.intern.phone_book.repositories;

import com.cloudassest.intern.phone_book.model.Contact;
import com.cloudassest.intern.phone_book.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ContactRepository extends MongoRepository<Contact,String> {
    List<Contact> findByUser(User user);

    @Query("{ 'name': { $regex: ?0, $options: 'i' }, 'user': ?1 }")
    List<Contact> findByUserNameRegex(String regex, String user);
}
