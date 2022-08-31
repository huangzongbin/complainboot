package com.krt.framework.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * AOP事务拦截器
 * 对于系统性能有一定的影响 ，不开启需要程序员使用声明式事务
 * 开启需要添加注解 @Aspect @Configuration
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年04月14日
 */
@Aspect
@Configuration
public class TxAdviceInterceptor implements TransactionManagementConfigurer {

    private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.krt.*..service..*.*(..))";

    @Resource
    private PlatformTransactionManager transactionManager;

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return transactionManager;
    }

    @Bean
    public TransactionInterceptor txAdvice() {
        Properties transactionAttributes = new Properties();
        // 新增
        transactionAttributes.setProperty("insert*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("update*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("delete*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("create*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("change*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("pay*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("give*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("save*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("cancel*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("do*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("*", "readOnly");
        return new TransactionInterceptor(transactionManager, transactionAttributes);
    }


    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }


}
