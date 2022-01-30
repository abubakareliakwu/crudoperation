package com.crudOperation.controller;

import com.crudOperation.dto.UserDto;
import com.crudOperation.imp.UserImp;
import com.crudOperation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {


    @Autowired
    UserService userService ;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody UserDto userDto) throws Exception {
        return userService.save(userDto);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> update(@RequestBody UserDto userDto) throws Exception {
        return userService.update(userDto);
    }

    @RequestMapping(value = "/hello/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> checkUser(@PathVariable String username) throws Exception {

        return userService.checkUser(username);
    }

}
