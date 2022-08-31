package com.krt.complain.service.impl;

import com.krt.msgLog.entity.TMsgLog;
import com.krt.msgLog.service.ITMsgLogService;
import com.krt.rent.utils.DateUtil;
import com.krt.rent.utils.GroupMAS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.krt.complain.entity.Complain;
import com.krt.complain.mapper.ComplainMapper;
import com.krt.complain.service.IComplainService;
import com.krt.common.base.BaseServiceImpl;


/**
 * 投诉信息服务接口实现层
 *
 * @author 转移
 * @version 1.0
 * @date 2019年06月10日
 */
@Service
public class ComplainServiceImpl extends BaseServiceImpl<ComplainMapper, Complain> implements IComplainService {

    @Autowired
    private ITMsgLogService tMsgLogService;

    /**
     * 提交保存发送短信
     * @param entity
     * @return
     */
    @Override
    public boolean insertAndMsgLog(Complain entity) {
        boolean insert = super.insert(entity);
        String returnMsg = GroupMAS.sendMsg("13155991651,17770107976", "您有一条新的投诉举报信息，请及时查收。");
        TMsgLog msgLog = new TMsgLog();
        msgLog.setPhone("13155991651,17770107976");
        msgLog.setContent("您有一条新的投诉举报信息，请及时查收。");
        msgLog.setReturnMsg(returnMsg);
        msgLog.setCid(entity.getId());
        tMsgLogService.insert(msgLog);
        return insert;
    }

    /**
     * 回复发送短信
     * @param entity
     */
    @Override
    public boolean updateByIdAndMsgLog(Complain entity) {
        return super.updateById(entity);
    }
}
