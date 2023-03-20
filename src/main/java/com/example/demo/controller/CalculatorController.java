package com.example.demo.controller;
import com.example.demo.entities.Record;
import com.example.demo.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/calculator")
public class CalculatorController {
    RecordService recordService;
    @Autowired
    public CalculatorController(RecordService recordService) {
        this.recordService = recordService;
    }


    @RequestMapping(value="v1/operation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
    public ResponseEntity<String> operation(@RequestBody Record record){
        try{
            recordService.saveRecord(record);
        } catch (IllegalAccessException e){
            return new ResponseEntity<>("User’s balance isn’t enough to cover the request cost", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("There was a problem executing your request"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().body("Operation executed successfully");
    }
}
