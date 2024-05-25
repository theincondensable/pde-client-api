package com.pde.config.log;

import com.pde.global.exceptions.BusinessException;
import com.pde.global.exceptions.BusinessExceptionResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * @author abbas
 */
@Aspect
@Configuration
public class LoggerAspect {

    private final LoggerService loggerService;
    private final MessageSource messageSource;

    public LoggerAspect(LoggerService loggerService, MessageSource messageSource) {
        this.loggerService = loggerService;
        this.messageSource = messageSource;
    }

    /**
     * Pointcut annotation declares a pointcut. Inside the pointcut argument,
     * we have defined a path to the custom annotation @Log we have created using @annotation.
     */
    @Pointcut("@annotation(Log) || execution(* com.pde.web.controllers..*.*(..))")
    public void logPointcut() {
    }

    @Around(value = "logPointcut()")
    public Object around(ProceedingJoinPoint o) throws Throwable {
        long start = new Date().getTime();
        String trackId = Long.toString(System.currentTimeMillis()) + Thread.currentThread().threadId();
        Object[] parameters = o.getArgs().length != 0 ? o.getArgs() : null;
        String scope = extractScope(o.getSignature().getDeclaringTypeName(), o.getSignature().getName());
        loggerService.logRequest(trackId, scope, parameters);

        Object res;
        try {
            res = o.proceed(); // Here the Controller comes into play.
        } catch (Exception e) {
            if (e instanceof BusinessException be) {
                long end = new Date().getTime();
                String message = messageSource.getMessage(be.getMessage(), be.getArgs(), be.getLocale());
                loggerService.logException(trackId, be.getClassName(), message, end - start);
                return new BusinessExceptionResponse(
                        message,
                        HttpStatus.resolve(be.getHttpStatusCode()).getReasonPhrase(),
                        be.getHttpStatusCode()
                );
            }
            throw e;
        }

        long end = new Date().getTime();
        loggerService.logResponse(trackId, scope, res.toString(), (end - start));
        return res;
    }

    private String extractScope(String declaringTypeName, String methodName) {
        String[] split = declaringTypeName.split("\\.");
        String className = split[split.length - 1];
        return className + "." + methodName + "()";
    }

}
