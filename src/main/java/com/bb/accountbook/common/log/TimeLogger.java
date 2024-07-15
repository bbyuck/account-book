package com.bb.accountbook.common.log;

import com.bb.accountbook.common.log.target.ExecutionTimeLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class TimeLogger {

    @Around("@annotation(com.bb.accountbook.common.log.target.ExecutionTimeLog)")
    public Object method(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        Object result = joinPoint.proceed();
        stopWatch.stop();

        log.info("{} ====== 실행 시간 : {}ms", methodName, stopWatch.getTotalTimeMillis());

        return result;
    }

}
