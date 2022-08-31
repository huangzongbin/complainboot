package com.krt.framework.aspect;

import com.krt.common.annotation.KrtLog;
import com.krt.common.bean.ReturnBean;
import com.krt.common.bean.ReturnCode;
import com.krt.common.constant.GlobalConstant;
import com.krt.common.exception.KrtException;
import com.krt.common.session.SessionUser;
import com.krt.common.util.IpUtils;
import com.krt.common.util.ServletUtils;
import com.krt.common.util.ShiroUtils;
import com.krt.common.util.UserAgentUtils;
import com.krt.sys.entity.Log;
import com.krt.sys.service.ILogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 切面日志
 *
 * @author 殷帅
 * @version 1.0
 * @date 2016年7月21日
 */
@Slf4j
@Aspect
@Component
public class KrtLogAspect {

    @Autowired
    private ILogService logService;

    /**
     * 线程变量
     */
    private static final ThreadLocal<Map> BIZ_LOG = new NamedThreadLocal<>("Biz Log");

    /**
     * 定义切入点
     */
    @Pointcut("@annotation(com.krt.common.annotation.KrtLog)")
    public void logAspect() {

    }

    /**
     * 方法执行前执行
     */
    @Before("logAspect()")
    public void doBefore(JoinPoint joinPoint) {
        try {
            Map<Object, Object> localMap = new HashMap<>(2);
            //开始时间
            localMap.put("beginTime", System.currentTimeMillis());
            //请求的参数
            Log log = getAopLog(joinPoint);
            //设置请求方法名
            String methodName = joinPoint.getTarget().getClass().getName() + GlobalConstant.POINT + joinPoint.getSignature().getName() + "()";
            log.setRequestMethodName(methodName);
            HttpServletRequest request = ServletUtils.getRequest();
            if (request != null) {
                // 请求的IP
                String ip = IpUtils.getIpAddr(request);
                log.setRequestIp(ip);
                //请求方法
                log.setRequestMethod(request.getMethod());
                //请求地址
                log.setRequestUrl(request.getServletPath());
                //请求环境
                UserAgentUtils userAgentUtils = new UserAgentUtils(request);
                log.setOs(userAgentUtils.getOS());
                log.setDevice(userAgentUtils.getDevice());
                log.setBrowser(userAgentUtils.getBrowser());
            }
            log.setExceptionCode(null);
            log.setExceptionDetail(null);
            //日志
            localMap.put("log", log);
            //保存到线程局部变量
            BIZ_LOG.set(localMap);
        } catch (Exception e) {
            // 记录本地异常日志
            log.error("前置通知异常:{}", e);
        }
    }

    /**
     * 方法返回后执行
     */
    @AfterReturning(pointcut = "logAspect()", returning = "returnValue")
    public void log(Object returnValue) {
        //得到线程绑定的局部变量
        Map localMap = BIZ_LOG.get();
        try {
            long beginTime = (long) localMap.get("beginTime");
            //2、结束时间
            Log log = (Log) localMap.get("log");
            log.setUseTime(System.currentTimeMillis() - beginTime);
            // 读取session中的用户
            SessionUser sessionUser = ShiroUtils.getSessionUser();
            if (sessionUser != null) {
                log.setUsername(sessionUser.getUsername());
            }
            //定时日志
            if (log.getType().equals(GlobalConstant.LogType.QUARTZ)) {
                log.setUsername(GlobalConstant.SYSTEM);
                log.setRequestIp(IpUtils.getLocalIp());
            }
            //登录日志
            if (log.getType().equals(GlobalConstant.LogType.LOGIN)) {
                ReturnBean rb = (ReturnBean) returnValue;
                if (ReturnCode.OK.getCode() != rb.getCode()) {
                    //记录登录成功的日志 登录失败不记录
                    return;
                }
            }
            logService.insertLog(log);
        } catch (Exception e) {
            //记录本地异常日志
            log.error("程序异常", e);
        } finally {
            BIZ_LOG.remove();
        }

    }


    /**
     * 发生异常后执行
     */
    @AfterThrowing(pointcut = "logAspect()", throwing = "e")
    public void doAfterThrowing(Throwable e) {
        //参数错误日志不记录
        if(e instanceof KrtException){
            ReturnBean rb = ((KrtException) e).getReturnBean();
            if(rb.getCode() == ReturnCode.INVALID_REQUEST.getCode()){
                return;
            }
        }
        //得到线程绑定的局部变量
        Map localMap = BIZ_LOG.get();
        Log sysLog = (Log) localMap.get("log");
        try {
            long beginTime = (long) localMap.get("beginTime");
            //2、结束时间
            Log log = (Log) localMap.get("log");
            log.setUseTime(System.currentTimeMillis() - beginTime);
            sysLog.setType(GlobalConstant.LogType.EXCEPTION);
            sysLog.setExceptionCode(e.getClass().getName());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintStream pout = new PrintStream(out);
            e.printStackTrace(pout);
            String exceptionDetail = new String(out.toByteArray());
            sysLog.setExceptionDetail(exceptionDetail);
            //保存数据库
            logService.insertLog(sysLog);
        } catch (Exception ex) {
            //记录本地异常日志
            log.error("程序异常", ex);
        } finally {
            BIZ_LOG.remove();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     */
    private Log getAopLog(JoinPoint joinPoint) {
        Log log = new Log();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        KrtLog krtLog = method.getAnnotation(KrtLog.class);
        if (null != krtLog) {
            //请求的参数
            Object[] args = joinPoint.getArgs();
            //日志参数长度
            int paraLength = 2000;
            if (krtLog.para() && Arrays.toString(args).length() < paraLength) {
                log.setRequestParams(Arrays.toString(args));
            }
            String description = krtLog.value();
            int type = krtLog.type();
            log.setDescription(description);
            log.setType(type);
        }
        return log;
    }

}

