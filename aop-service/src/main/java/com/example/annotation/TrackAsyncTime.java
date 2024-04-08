package com.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
/**
 * аннотацию можно ставить на void-методах, либо методах,
 * которые возвращают CompletableFuture<T>
 */
public @interface TrackAsyncTime {

}