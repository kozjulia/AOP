package com.example.aspect;

import com.example.dto.NewTrackTimeDto;
import com.example.service.TrackTimeService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingTrackTimeAspect {

    @Value("${logging.track.time.switch: true}")
    private boolean loggingTrackTimeSwitch;

   private final TrackTimeService trackTimeService;

    @Pointcut("@annotation(com.example.annotation.TrackTime)")
    public void loggingMethod() {
    }

    @Around("loggingMethod()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        if (Objects.equals(loggingTrackTimeSwitch, false)) {
            return joinPoint.proceed();
        }
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        try {
            return joinPoint.proceed();
        } finally {
            stopWatch.stop();
            long totalTime = stopWatch.getTotalTimeMillis();
            log.info("Track time for " + className + "." + methodName + " :: " + totalTime + " millis.");

            NewTrackTimeDto trackTime = new NewTrackTimeDto();
            trackTime.setName("TrackTime");
            trackTime.setClassName(className);
            trackTime.setMethodName(methodName);
            trackTime.setTotalTime(totalTime);
            trackTimeService.saveTrackTime(trackTime);
        }
    }

}