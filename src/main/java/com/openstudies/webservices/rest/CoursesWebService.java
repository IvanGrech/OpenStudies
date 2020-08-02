package com.openstudies.webservices.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoursesWebService {

    @RequestMapping(value = "/courses/create")
    public ResponseEntity<?> create() {


        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

}
