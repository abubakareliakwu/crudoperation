package com.crudOperation.def;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseObject {


    public ResponseEntity<?> returnResponseBody(Object code, String message, Object data) {
        Map<String, Object> jsonResponse = new HashMap();
        jsonResponse.put(Constants.STATUS, code);
        jsonResponse.put(Constants.MESSAGE, message);
        jsonResponse.put("data", data);

        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
    }

}
