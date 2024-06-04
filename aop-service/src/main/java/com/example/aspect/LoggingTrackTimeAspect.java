package com.example.aspect;

import com.example.dto.NewTrackTimeDto;
import com.example.exception.TrackTimeException;
import com.example.service.TrackTimeService;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
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
  @Value("${logging.track.async.time.switch: true}")
  private boolean loggingTrackAsyncTimeSwitch;

  private final TrackTimeService trackTimeService;

  @Pointcut("@annotation(com.example.annotation.TrackTime)")
  public void loggingTrackTime() {
  }

  @Pointcut("@annotation(com.example.annotation.TrackAsyncTime)")
  public void loggingTrackAsyncTime() {
  }

  @Around("loggingTrackTime()")
  public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    if (Objects.equals(loggingTrackTimeSwitch, false)) {
      return joinPoint.proceed();
    }

    return getTrackTime(joinPoint, "TrackTime");
  }

  @Around("loggingTrackAsyncTime()")
  public Object logExecutionAsyncTime(ProceedingJoinPoint joinPoint) throws Throwable {
    if (Objects.equals(loggingTrackAsyncTimeSwitch, false)) {
      return joinPoint.proceed();
    }

    CompletableFuture<Object> future = CompletableFuture.supplyAsync(() ->
        getTrackTime(joinPoint, "TrackAsyncTime"));
    return future.join();
  }

  private Object getTrackTime(ProceedingJoinPoint joinPoint, String annotation) {
    log.info("Начало работы @{}", annotation);
    final StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    String className = methodSignature.getDeclaringType().getSimpleName();
    String methodName = methodSignature.getName();

    try {
      return joinPoint.proceed();
    } catch (Throwable e) {
      throw new TrackTimeException("В аннотации @" + annotation + " произошла ошибка в " +
          className + "." + methodName);
    } finally {
      stopWatch.stop();
      long totalTime = stopWatch.getTotalTimeMillis();
      log.info("Track time for {}.{} :: {} millis.", className, methodName, totalTime);

      NewTrackTimeDto trackTime = new NewTrackTimeDto();
      trackTime.setName(annotation);
      trackTime.setClassName(className);
      trackTime.setMethodName(methodName);
      trackTime.setTotalTime(totalTime);
      trackTimeService.saveTrackTime(trackTime);
    }
  }

}