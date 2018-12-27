package org.indyoracle.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This custom annotation identifies a method as a service call.
 * The ServiceInterceptor uses this annotation to redirect GET requests away from annotations.
 * 
 * @author Guy
 *
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {

}
