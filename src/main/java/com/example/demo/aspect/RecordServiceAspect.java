package com.example.demo.aspect;

import com.example.demo.entities.Record;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RecordServiceAspect {

    @After(value = "execution(* com.example.demo.service.RecordService.*(..)) and args(record)")
    public void afterAdvice(JoinPoint joinPoint, Record record) {
        System.out.println("Executed Method:" + joinPoint.getSignature());

        System.out.println("Successfully created Record with id - " + record.getId()+" with a cost of: " + record.getAmount()  );
    }
}
