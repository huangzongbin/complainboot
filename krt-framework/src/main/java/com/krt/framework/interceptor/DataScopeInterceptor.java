package com.krt.framework.interceptor;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.google.common.base.Joiner;
import com.krt.common.datascope.DataScope;
import com.krt.common.datascope.DataScopeHelper;
import com.krt.common.util.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.util.List;
import java.util.Properties;

/**
 * Mybatis数据范围的拦截器
 * 使用方法：
 * DataScope.start();
 * 业务方法
 * DataScope.remove();
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月30日
 */
@Slf4j
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class DataScopeInterceptor implements Interceptor {

    /**
     * 拦截器
     *
     * @param invocation 代理点
     * @return {@link Object}
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
        if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        }
        BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
        String originalSql = boundSql.getSql();
        //获取线程变量
        boolean flag = DataScopeHelper.get();
        //开启数据权限
        if (flag) {
            DataScope dataScope = ShiroUtils.getDataScope();
            if (dataScope == null) {
                return invocation.proceed();
            } else {
                String scopeName = dataScope.getScopeName();
                List<Object> scopeValue = dataScope.getScopeValue();
                if (scopeValue != null && scopeValue.size() > 0) {
                    log.debug("==================数据范围的拦截器========================");
                    String inScope = Joiner.on(",").join(scopeValue);
                    originalSql = "select * from (" + originalSql + ") t where " + " t." + scopeName + " in " + "(" + inScope + ")";
                    metaStatementHandler.setValue("delegate.boundSql.sql", originalSql);
                    return invocation.proceed();
                } else {
                    return invocation.proceed();
                }
            }
        } else {
            return invocation.proceed();
        }

    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}