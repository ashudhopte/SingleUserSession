package com.project.singleusersessionspring.repo;

import com.project.singleusersessionspring.entity.EmailLoginCodeEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailLoginCodeRepo extends JpaRepository<EmailLoginCodeEntity, Integer>{
    
    EmailLoginCodeEntity findByEmail(String email);
}
