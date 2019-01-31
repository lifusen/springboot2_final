package com.itfusen.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Created by lifusen on 2019/1/22 10:10
 */
@Aspect
@Component
public class WebLog {

    private static final Logger logger = LoggerFactory.getLogger(WebLog.class);

    @Before("webLog()")
    public void beforeLog(JoinPoint joinPoint) throws Throwable
    {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        logger.info("--------------------------------------WEB START--------------------------------------");
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("PARAMETERS : ");
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements())
        {
            String name = (String) enu.nextElement();
            logger.info(name + ":" + request.getParameter(name));
        }
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable
    {
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret);
        logger.info("--------------------------------------WEB   END--------------------------------------");
    }

    @Pointcut("execution(public * com.itfusen.controller.*.*(..))")
    public void webLog()
    {

    }

}
