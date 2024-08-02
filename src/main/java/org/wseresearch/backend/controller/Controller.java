package org.wseresearch.backend.controller;

import org.springframework.web.bind.annotation.*;
import org.wseresearch.backend.helper.Understandability_Metric;
import org.wseresearch.backend.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
public class Controller {

    @Autowired
    private Service service;
    
    @PostMapping("/login/{userId}")
    public ResponseEntity<?> loginAndReturnInitialData(@PathVariable String userId) {
        try {
            return new ResponseEntity<>(service.login(userId), HttpStatus.OK);
        } catch(RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>("User doesn't exist", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/createuser")
    public ResponseEntity<?> createUser() {
        try {
            return new ResponseEntity<>(service.createUser(), HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>("Error while creating new user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/exist/{id}")
    public ResponseEntity<?> checkUserExist(@PathVariable String id) {
        boolean userExist = service.checkUserExist(id);
        if(userExist) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Other status code
        }
    }

    @PostMapping("/storecorrectness/{id}/{hash}/{errors}")
    public ResponseEntity<?> storeCorrectness(@PathVariable String hash, @PathVariable Integer errors ,@PathVariable String id) {
        try {
            service.storeCorrectnessEntry(hash, errors, id);
            return new ResponseEntity<>("Entry updated successfully", HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>("Failed to update entry", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/storeunderstandability/{id}/{tuple}", consumes = "application/json")
    public ResponseEntity<?> storeUnderstandability(@PathVariable String id, @PathVariable String tuple, @RequestBody Understandability_Metric understandabilityMetric) {
        try {
            service.storeUnderstandabilityEntry(id, tuple, understandabilityMetric);
            return new ResponseEntity<>("Entry updated successfully", HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>("Failed to update entry", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
