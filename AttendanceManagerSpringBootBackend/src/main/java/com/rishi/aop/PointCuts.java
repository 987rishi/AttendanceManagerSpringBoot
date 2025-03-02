package com.rishi.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PointCuts {

    /**
     * Pointcut for all methods in the service layer.
     * Applies to all method executions in classes under the com.rishi.service package.
     */
    @Pointcut("execution(* com.rishi.service..*(..))")
    public void serviceAttendanceMethods() {}
    
    /**
     * Pointcut for all methods in the model layer.
     * Applies to all method executions in classes under the com.rishi.model package.
     * Note: If you use `within` here, it will target all join points, not just method executions.
     */
    @Pointcut("execution(* com.rishi.model.*.*(..))")
    public void modelMethods() {}

    /**
     * Pointcut for all methods in the controller layer.
     * Applies to all method executions in classes under the com.rishi.controller package.
     */
    @Pointcut("execution(* com.rishi.controller.*.*(..))")
    public void controllerMethods() {}
}
