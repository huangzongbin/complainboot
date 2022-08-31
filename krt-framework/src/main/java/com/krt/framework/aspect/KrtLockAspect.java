package com.krt.framework.aspect;

import com.krt.common.annotation.KrtLock;
import com.krt.common.bean.ReturnBean;
import com.krt.common.constant.GlobalConstant;
import com.krt.common.exception.KrtException;
import com.krt.common.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.*;
import org.apache.ibatis.javassist.bytecode.CodeAttribute;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.apache.ibatis.javassist.bytecode.MethodInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import oshi.jna.platform.mac.SystemB;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author 胡国坚
 * @date 2021/10/28 16:05
 * @description
 */
@Aspect
@Component
@Slf4j
public class KrtLockAspect {

    @Autowired
    private RedissonClient redissonClient;

    @Pointcut("@annotation(com.krt.common.annotation.KrtLock)")
    public void pointcut() {
    }


    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //按方法名称顺序排列，只不过为空的没有展示出来
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        //获取所有参数
        Parameter[] parameter = method.getParameters();

        //调用执行目标方法(result为目标方法执行结果)
        Object result;
        KrtLock krtLock = method.getAnnotation(KrtLock.class);
        String lockName = krtLock.lockName();
        long leaveTime = krtLock.leaveTime();
        long waitTime = krtLock.waitTime();
        RLock lock = null;

        StringBuilder sb = new StringBuilder();

        String preKey = method.getDeclaringClass().getName() + "." + method.getName() + GlobalConstant.WELL_NUMBER;
        String[] lockNameArrays = lockName.split(GlobalConstant.WELL_NUMBER);
        sb.append(preKey);
        try {
            if (lockName.contains(GlobalConstant.WELL_NUMBER)) {
                int flag = -1;
                //查找参数名称对应的参数值
                for (String lockName2 : lockNameArrays) {
                    for (int i = 0; i < parameter.length; i++) {
                        Parameter parameter1 = parameter[i];
                        if (lockName2.equals(parameter1.getName())) {
                            flag = 1;
                            if (parameter1.getType() == Date.class) {
                                sb.append(DateUtils.dateToString("yyyy-MM-dd", (Date) args[i])).append(GlobalConstant.WELL_NUMBER);
                            } else {
                                sb.append(args[i].toString()).append(GlobalConstant.WELL_NUMBER);
                            }
                            break;
                        }
                    }
                }
                if (flag == -1) {
                    throw new KrtException(ReturnBean.error("没有找到该参数名称" + lockName));
                }
                sb.delete(sb.length() - 1, sb.length());
            } else {
                sb.append(lockName);
            }
            lock = redissonClient.getLock(sb.toString());
            boolean isLock = lock.tryLock(waitTime, leaveTime, TimeUnit.MILLISECONDS);
            if (!isLock) {
                throw new KrtException(ReturnBean.error(Thread.currentThread().getId() + "您的操作太快，请稍后再试！"));
            }
            result = joinPoint.proceed(args);
        } catch (InterruptedException e) {
            log.error("", e);
            throw new KrtException(ReturnBean.error(e.getMessage()));
        } finally {
            if (null != lock && lock.isLocked()) {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }
        return result;
    }

}
