package com.test.atos.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging inputs, outputs, and processing time of controller methods.
 */
@Component
@Aspect
public class LoggingAspect {

    // Logger instance for logging information
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Around advice that logs method entry, exit, and processing time for controller methods.
     *
     * @param joinPoint The join point representing the method execution.
     * @return The result of the method execution.
     * @throws Throwable if the method execution throws any exception.
     */
    @Around("execution(* com.test.atos.controller..*(..))")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        // Capture start time
        long startTime = System.currentTimeMillis();

        // Log method entry with arguments
        logger.info("Entering method: {} with arguments: {}",
                joinPoint.getSignature(), joinPoint.getArgs());
        try {
            // Proceed with method execution
            Object result = joinPoint.proceed();

            // Calculate time taken
            long timeTaken = System.currentTimeMillis() - startTime;

            // Log method exit with result and time taken
            logger.info("Exiting method: {} with result: {} | Time taken: {} ms",
                    joinPoint.getSignature(), result, timeTaken);
            return result;
        } catch (Throwable throwable) {
            // Log exception if any occurs during method execution
            logger.error("Exception in method: {} with message: {}",
                    joinPoint.getSignature(), throwable.getMessage());
            throw throwable;
        }
    }
}
