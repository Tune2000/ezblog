package com.easy.aspect;

import com.alibaba.fastjson.JSON;
import com.easy.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wxx
 * @create 08--25--10:51
 * @description:切面类
 */
@Component
@Aspect//告诉spring容器，myLogAspect是切面类
@Slf4j//用于在控制台打印日志信息
public class LogAspect {

    @Pointcut("@annotation(com.easy.annotation.SystemLog)")
    public void pt() {

    }

    //定义通知的方法(这里用的是环绕通知)，通知的方法也就是增强的具体代码。@Around注解表示该通知的方法会用在哪个切点
    @Around("pt()")
    //ProceedingJoinPoint可以拿到被增强方法的信息
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        //proceed方法表示调用目标方法，ret就是目标方法执行完之后的返回值
        Object ret;
        try {
            //调用下面写的'实现打印日志信息的格式信息'的方法
            handleBefore(joinPoint);
            ret = joinPoint.proceed();//这是目标方法执行完成，上一行是目标方法未执行，下一行是目标方法已经执行
            //调用下面写的'实现打印日志信息的数据信息'的方法
            handleAfter(ret);
        } finally {
            //下面的'实现打印日志信息的数据信息'的方法，不管有没有出异常都会被执行，确保下面的输出必然输出在控制台
            //System.lineSeparator表示当前系统的换行符
            log.info("=======End=======" + System.lineSeparator());
        }

        //封装成切面，然后返回
        return ret;
    }

    //上面的printLog方法执行之前会去调用下面这个handleAfter方法
    public void handleAfter(Object ret) {
        // 打印出参
        log.info("Response       : {}", JSON.toJSONString(ret));
    }

    //上面的printLog方法执行之前会去调用下面这个handleBefore方法
    public void handleBefore(ProceedingJoinPoint joinPoint) {
        //ServletRequestAttributes是RequestAttributes是spring接口的实现类
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //下面那行就可以拿到请求的报文了，其中有我们需要的url、请求方式、ip。这里拿到的request会在下面的log中大量使用
        HttpServletRequest request = requestAttributes.getRequest();

        //获取被增强方法的注解对象，例如获取UserController类的updateUserInfo方法上一行的@mySystemlog注解
        //getSystemlog是我们下面写的方法
        SystemLog systemLog = getSystemLog(joinPoint);

        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}",request.getRequestURI());
        // 打印描述信息
        log.info("BusinessName   : {}",systemLog.BusinessName());
        // 打印 Http method
        log.info("HTTP Method    : {}",request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}",joinPoint.getSignature().getDeclaringTypeName(),((MethodSignature) joinPoint.getSignature()).getName());
        // 打印请求的 IP
        log.info("IP             : {}",request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", JSON.toJSONString(joinPoint.getArgs()));
    }

    //获取被增强方法的注解对象，例如获取UserController类的updateUserInfo方法上一行的@mySystemlog注解
    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        //Signature是spring提供的接口，MethodSignature是Signature的子接口
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //下面那行就能获取被增强方法的注解对象，例如获取UserController类的updateUserInfo方法上一行的@mySystemlog注解
        SystemLog systemLog = methodSignature.getMethod().getAnnotation(SystemLog.class);
        return systemLog;
    }
}
