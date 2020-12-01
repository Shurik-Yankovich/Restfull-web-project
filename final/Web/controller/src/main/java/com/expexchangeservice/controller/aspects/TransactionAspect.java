package com.expexchangeservice.controller.aspects;

import com.expexchangeservice.utils.hibernate.HibernateSessionFactoryUtil;
import org.aspectj.lang.annotation.*;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class TransactionAspect {

    private Transaction transaction;
    private int countMethods = 0;

    @Pointcut("execution(public * com.expexchangeservice.service.impl.*.*(..))")
    public void transactions() {
    }

    @Before("transactions()")
    public void beforeCallMethod() {
        if (countMethods == 0) {
            transaction = HibernateSessionFactoryUtil.openSession().beginTransaction();
        }
        countMethods++;
    }

    @AfterReturning("transactions()")
    public void afterCallMethod() {
        if (countMethods == 1) {
            transaction.commit();
            HibernateSessionFactoryUtil.closeSession();
        }
        countMethods--;
    }

    @AfterThrowing("transactions()")
    public void afterThrowingMethod() {
        transaction.rollback();
        HibernateSessionFactoryUtil.closeSession();
        countMethods = 0;
    }
}
