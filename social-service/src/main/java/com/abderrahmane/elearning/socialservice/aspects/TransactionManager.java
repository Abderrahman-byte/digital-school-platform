package com.abderrahmane.elearning.socialservice.aspects;

import javax.persistence.EntityManager;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TransactionManager {
    @Autowired
    private EntityManager entityManager;

    @Pointcut("execution (* *(..)) && @annotation(com.abderrahmane.elearning.socialservice.annotations.WrapTransaction)")
    public void handleTransaction () {}
    
    @Pointcut("execution (* *(..)) && @annotation(com.abderrahmane.elearning.socialservice.annotations.ClearCache)")
    public void mustClearCache () {}

    @Around("handleTransaction ()")
    public Object transactionWrapper (ProceedingJoinPoint pjp) throws Throwable {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }

            Object retValue = pjp.proceed();

            entityManager.getTransaction().commit();

            return retValue;
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
