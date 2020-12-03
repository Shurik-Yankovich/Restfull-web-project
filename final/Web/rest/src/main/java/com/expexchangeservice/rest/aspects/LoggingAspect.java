package com.expexchangeservice.rest.aspects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @AfterThrowing( pointcut = "execution(public * com.expexchangeservice.controller.rest.*.*(..))", throwing= "ex")
    public void loggingExceptions(JoinPoint joinPoint, Exception ex) {
        Logger logger = Logger.getLogger(joinPoint.getTarget().getClass());
        String method = joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();
        String message = ex.getMessage() + "\nCall method '" + method + "' with " + methodArgs.length + " args:\n";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            message += objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(methodArgs);
            logger.error(message);
        } catch (JsonProcessingException e){
            Logger.getLogger(this.getClass()).error(e.getMessage());
        }
    }
}
