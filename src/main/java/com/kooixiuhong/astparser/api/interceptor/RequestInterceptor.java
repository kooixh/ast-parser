package com.kooixiuhong.astparser.api.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class RequestInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);
    public static final String UUID_HEADER = "requestUUID";
    private static final String START_TIME = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestUri = request.getRequestURI();
        long startTime = System.currentTimeMillis();
        String requestUUID = UUID.randomUUID().toString();
        logger.info("Started handling uri {} for uuid {} at time {}", requestUri, requestUUID, startTime);
        request.setAttribute(UUID_HEADER, requestUUID);
        response.addHeader(UUID_HEADER, requestUUID);
        request.setAttribute(START_TIME, startTime);
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        String requestUri = request.getRequestURI();
        String uuid = request.getAttribute(UUID_HEADER).toString();
        long startTime = (Long) request.getAttribute(START_TIME);
        long diff = System.currentTimeMillis() - startTime;
        logger.info("Handled request for uri {} for uuid {} in {} ms", requestUri, uuid, diff);
        super.afterCompletion(request, response, handler, ex);
    }
}
