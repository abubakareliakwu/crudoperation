package com.crudOperation.repository;

import com.crudOperation.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {


 User findByUserName(String username);

}
