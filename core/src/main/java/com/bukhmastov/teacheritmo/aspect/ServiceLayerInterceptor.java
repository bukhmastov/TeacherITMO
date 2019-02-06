package com.bukhmastov.teacheritmo.aspect;

import com.bukhmastov.teacheritmo.exception.HttpStatusException;
import com.bukhmastov.teacheritmo.exception.InternalErrorException;
import com.bukhmastov.teacheritmo.struct.Response;
import com.bukhmastov.teacheritmo.struct.Status;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

@Aspect
@Order(0)
public class ServiceLayerInterceptor {

    @Pointcut("execution(public com.bukhmastov.teacheritmo.struct.Response *(..))")
    private void responseMethod() {}

    @Pointcut("within(com.bukhmastov.teacheritmo.service..*)")
    private void inServiceLayer() {}

    @Around("responseMethod() && inServiceLayer()")
    public Object unhandledException(ProceedingJoinPoint pjp) {
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            return handleReturn(pjp.getSignature(), Status.ERROR, throwable);
        }
    }

    private Object handleReturn(Signature signature, Status status, Throwable throwable) {
        if (!(signature instanceof MethodSignature)) {
            return null;
        }
        log.info("Caught uncaught exception | signature={} | status={} | throwable={}",
                signature.getName(), status, throwable.toString());
        if (Response.class.isAssignableFrom(((MethodSignature)signature).getReturnType())) {
            if (!(throwable instanceof HttpStatusException)) {
                throwable = new InternalErrorException("Unexpected error occurred", throwable);
            }
            return Response.error(status, throwable);
        }
        return null;
    }

    private static final Logger log = LoggerFactory.getLogger(ServiceLayerInterceptor.class);
}
