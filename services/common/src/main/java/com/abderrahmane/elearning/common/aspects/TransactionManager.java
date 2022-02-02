package com.abderrahmane.elearning.common.aspects;

import javax.persistence.EntityManager;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
public class TransactionManager {
    @Autowired
    private  EntityManager entityManager;

    @Pointcut("execution (* *(..)) && @annotation(com.abderrahmane.elearning.common.annotations.HandleTransactions)")
    private void getTransactions () {}
    
    @Pointcut("execution (* *(..)) && @annotation(com.abderrahmane.elearning.common.annotations.ClearCache)")
    private void mustClearCache () {}

    @Around("getTransactions ()")
    public Object wrapTransaction (ProceedingJoinPoint pjp) throws Throwable {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }

        try {
            Object value = pjp.proceed();

            entityManager.getTransaction().commit();
            return value;
        } catch (Throwable ex) {
            if (entityManager.getTransaction().getRollbackOnly()) {
                entityManager.getTransaction().rollback();
            }

            throw ex;
        }
    }

    @Before("mustClearCache ()")
    public void clearCache () {
        entityManager.clear();
    }
}
