package com.abderrahmane.elearning.common.aspects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

// TODO : implement this and test it

@Aspect
public class ClearCacheAspect {
    @PersistenceContext
    private  EntityManager entityManager;
    
    @Pointcut("execution (* *(..)) && @annotation(com.abderrahmane.elearning.common.annotations.ClearCache)")
    private void mustClearCache () {}

    @Before("mustClearCache ()")
    public void clearCache () {
        System.out.println("[CLEARING CACHE]");
        entityManager.clear();
    }
}
