package com.example.demo.service;

import com.example.demo.entities.Record;

public interface RecordService {

    Record saveRecord(Record operation) throws IllegalAccessException;
}
