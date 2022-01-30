package com.crudOperation.service;

import com.crudOperation.dto.UserDto;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;

public interface UserService {

    ResponseEntity save(UserDto user);

    ResponseEntity  update(UserDto userDto);

    ResponseEntity checkUser(String username) throws ParseException;
}
