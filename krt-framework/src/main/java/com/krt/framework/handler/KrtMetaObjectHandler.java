package com.krt.framework.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.krt.common.session.SessionUser;
import com.krt.common.util.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自定义填充公共 name 字段
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年10月16日
 */
@Slf4j
@Component
public class KrtMetaObjectHandler implements MetaObjectHandler {

    /**
     * 录入人id
     */
    private final String INSERTER = "inserter";

    /**
     * 录入时间
     */
    private final String INSERTE_TIME = "insertTime";

    /**
     * 更新者
     */
    private final String UPDATER = "updater";

    /**
     * 更新时间
     */
    private final String UPDATER_TIME = "updateTime";


    /**
     * 新增操作公共数据填充
     *
     * @param metaObject 元数据
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        SessionUser sessionUser = ShiroUtils.getSessionUser();
        Object inserter = getFieldValByName(INSERTER, metaObject);
        if (inserter == null && sessionUser != null) {
            setFieldValByName(INSERTER, sessionUser.getId(), metaObject);
            setFieldValByName(UPDATER, sessionUser.getId(), metaObject);
        }
        Object insertTime = getFieldValByName(INSERTE_TIME, metaObject);
        if (insertTime == null) {
            setFieldValByName(INSERTE_TIME, new Date(), metaObject);
            setFieldValByName(UPDATER_TIME, new Date(), metaObject);
        }
    }

    /**
     * 更新操作公共数据对接
     *
     * @param metaObject 元数据
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        SessionUser sessionUser = ShiroUtils.getSessionUser();
        Object updater = getFieldValByName(UPDATER, metaObject);
        if (updater == null && sessionUser != null) {
            setFieldValByName(UPDATER, sessionUser.getId(), metaObject);
        }
        Object updateTime = getFieldValByName(UPDATER_TIME, metaObject);
        if (updateTime == null) {
            setFieldValByName(UPDATER_TIME, new Date(), metaObject);
        }
    }
}
