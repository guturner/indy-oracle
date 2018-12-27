package org.indyoracle.interceptors;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * This interceptor prevents users from accessing service calls with GET requests.
 * 
 * @author Guy
 *
 */
public class ServiceInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {

		HandlerMethod method = (HandlerMethod) handler;
		Method m = method.getMethod();
		
		// Redirect if this is a service and does not have any parameters:
		if (m.isAnnotationPresent(org.indyoracle.annotations.Service.class) && !request.getParameterNames().hasMoreElements()) {
			response.sendRedirect("/");
			return false;
		}
		
		return true;
	}
	
}
