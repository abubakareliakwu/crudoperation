package com.crudOperation.imp;

import com.crudOperation.def.Constants;
import com.crudOperation.def.ResponseObject;
import com.crudOperation.def.ResponseStatus;
import com.crudOperation.domain.User;
import com.crudOperation.dto.UserDto;
import com.crudOperation.repository.UserRepository;
import com.crudOperation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserImp implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity save(UserDto user) {

        Map<Object, Object> jsonResponse = new HashMap();
        Pattern pattern = Pattern.compile("^[A-Za-z]*$");

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String userDate = formatter.format(user.getDateOfBirth());

        if (user.getUserName().matches(String.valueOf(pattern))==false) {
            return new ResponseObject().returnResponseBody(ResponseStatus.NO_CONTENT.getStatus(),"Username Must contain only letters", jsonResponse);

        }
        if (userDate.matches(formatter.format(date))==true){
            return new ResponseObject().returnResponseBody(ResponseStatus.NO_CONTENT.getStatus(),"Date of Birth must be before today", jsonResponse);
        }
           User user1 = new User();
           user1.setName(user.getName());
           user1.setDateOfBirth(user.getDateOfBirth());
           user1.setUserName(user.getUserName());

        try {
            jsonResponse.put(Constants.CREATED, userRepository.save(user1));
            return new ResponseObject().returnResponseBody("200", "Record successfully created",user);
        } catch (DataIntegrityViolationException exception) {
            return new ResponseObject().returnResponseBody(ResponseStatus.SQL_ERROR.getStatus(), exception.getRootCause().getLocalizedMessage(),"");
        }
    }

    @Override
    public ResponseEntity update(UserDto userDto) {

        Map<Object, Object> jsonResponse = new HashMap();


        User user = userRepository.findByUserName(userDto.getUserName());



        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());

        if (user != null){

            Pattern pattern = Pattern.compile("^[A-Za-z]*$");
            Matcher matcher = pattern.matcher(user.getUserName());
            if (user.getUserName().matches(String.valueOf(pattern))==false) {

                return new ResponseObject().returnResponseBody(ResponseStatus.NO_CONTENT.getStatus(),"Username Must contain only letters", jsonResponse);

            }
            if (user.getDateOfBirth().equals(formatter.format(date))){
                return new ResponseObject().returnResponseBody(ResponseStatus.NO_CONTENT.getStatus(),"Date of Birth must be before today", jsonResponse);
            }

             user.setUserName(userDto.getUserName());
             user.setDateOfBirth(userDto.getDateOfBirth());

            try {
                jsonResponse.put(Constants.UPDATED, userRepository.save(user));
                return new ResponseObject().returnResponseBody("200", "Record successfully Updated",user);
            } catch (DataIntegrityViolationException exception) {
                return new ResponseObject().returnResponseBody(ResponseStatus.SQL_ERROR.getStatus(), exception.getRootCause().getLocalizedMessage(),"");
            }
        }

        else {
            return new ResponseObject().returnResponseBody(ResponseStatus.ENTITY_NOT_FOUND.getStatus(), "Record not found","");
        }
        
    }

    @Override
    public ResponseEntity checkUser(String username) throws ParseException {

        Map<Object, Object> jsonResponse = new HashMap();

        System.out.println(username);
        User user = userRepository.findByUserName(username);

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());

        if (user != null){

            if (user.getDateOfBirth().equals(formatter.format(date))){
                String message = "Hello " + user.getUserName() + "Happy Birthday";
                jsonResponse.put("message", message);
                return new ResponseObject().returnResponseBody(ResponseStatus.NO_CONTENT.getStatus(),"", jsonResponse);
            }
            else {

                Date inputString1 = user.getDateOfBirth();
                String inputString2 = formatter.format(date);

                Date date1 = inputString1;
                Date date2 = formatter.parse(inputString2);
                long diff = date2.getTime() - date1.getTime();

                String days= "Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                String message1 = "Hello " + user.getUserName() + " your Birthday is in " + days + "days";
                jsonResponse.put("message", message1);

                return new ResponseObject().returnResponseBody(ResponseStatus.NO_CONTENT.getStatus(),"", jsonResponse);

            }
        }else {
            return new ResponseObject().returnResponseBody(ResponseStatus.NO_CONTENT.getStatus(),"Record not found", "");

        }


    }
}
