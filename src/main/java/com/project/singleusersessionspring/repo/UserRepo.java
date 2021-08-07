package com.project.singleusersessionspring.repo;

import com.project.singleusersessionspring.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Integer>{
    
    UserEntity findByEmail(String email);
}
