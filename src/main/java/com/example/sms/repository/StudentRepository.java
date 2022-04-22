package com.example.sms.repository;

import com.example.sms.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

// We don't add @Repository and @Transactional here
// Type of entity, Type of Primary Key
public interface StudentRepository extends JpaRepository <Student, Long> {

}
