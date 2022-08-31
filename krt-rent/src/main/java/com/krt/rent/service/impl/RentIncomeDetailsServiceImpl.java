package com.krt.rent.service.impl;

import com.krt.common.base.BaseServiceImpl;
import com.krt.common.session.SessionUser;
import com.krt.common.util.ShiroUtils;
import com.krt.pay.dto.WeiRentalPayBackDTO;
import com.krt.rent.entity.RentIncomeBase;
import com.krt.rent.entity.RentIncomeDetails;
import com.krt.rent.enums.HouseTypeEnum;
import com.krt.rent.enums.RentIncomeBaseStatusEnum;
import com.krt.rent.mapper.RentIncomeDetailsMapper;
import com.krt.rent.service.IRentIncomeDetailsService;
import com.krt.rent.vo.RentIncomeDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * 租金管理详情表服务接口实现层
 *
 * @author ylf
 * @version 1.0
 * @date 2019年06月10日
 */
@Service
public class RentIncomeDetailsServiceImpl extends BaseServiceImpl<RentIncomeDetailsMapper, RentIncomeDetails> implements IRentIncomeDetailsService {

    @Autowired
    private RentIncomeBaseServiceImpl rentIncomeBaseService;
    @Autowired
    private RentIncomeDetailsMapper rentIncomeDetailsMapper;
    @Autowired
    private RentIncomeDetailsTemporaryServiceImpl rentIncomeDetailsTemporaryService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertExcelIn(RentIncomeBase incomeBase, List<RentIncomeDetails> list, String year, String month, String houseType) {
        SessionUser sessionUser = ShiroUtils.getSessionUser();
        if(incomeBase==null){
            //初次导入
            RentIncomeBase rentIncomeBase = new RentIncomeBase();
            rentIncomeBase.setYear(year);
            rentIncomeBase.setMonth(month);
            rentIncomeBase.setNumber(list.size());
            rentIncomeBase.setStatus(RentIncomeBaseStatusEnum.END.getStatus());
            rentIncomeBase.setInserter(sessionUser.getId());
            rentIncomeBase.setInsertTime(new Date());
            rentIncomeBase.setPayStatus(RentIncomeBaseStatusEnum.pay_Cost.getStatus());
            rentIncomeBase.setHouseType(houseType);
            rentIncomeBaseService.insert(rentIncomeBase);
            for(RentIncomeDetails details:list){
                if(details.getStatus()==null){
                    details.setStatus(0);
                }
                details.setInserter(sessionUser.getId());
                details.setInsertTime(new Date());
                details.setBaseId(rentIncomeBase.getId());
                details.setHouseType(houseType);
            }
            insertBatch(list);
        } else {
            //修改base
            RentIncomeBase rentIncomeBase = new RentIncomeBase();
            rentIncomeBase.setId(incomeBase.getId());
            rentIncomeBase.setNumber(list.size());
            rentIncomeBase.setStatus(RentIncomeBaseStatusEnum.END.getStatus());
            rentIncomeBase.setUpdater(sessionUser.getId());
            rentIncomeBase.setUpdateTime(new Date());
            rentIncomeBase.setPayStatus(RentIncomeBaseStatusEnum.pay_Cost.getStatus());
            rentIncomeBaseService.updateById(rentIncomeBase);
            // 查询这一批次哪些是系统中存在的
            List<String> existIdCard = rentIncomeDetailsMapper.selectExistByList(list,rentIncomeBase.getId(),houseType);
            List<RentIncomeDetails> insertDatas = new ArrayList<>();
            List<RentIncomeDetails> updateDatas = new ArrayList<>();
            for(RentIncomeDetails details:list){
                if(details.getStatus()==null){
                    details.setStatus(0);
                }
                if(existIdCard.contains(details.getIdcardAddr())){
                    details.setUpdater(sessionUser.getId());
                    details.setUpdateTime(new Date());
                    details.setBaseId(incomeBase.getId());
                    updateDatas.add(details);
                }else{
                    details.setInserter(sessionUser.getId());
                    details.setInsertTime(new Date());
                    details.setBaseId(incomeBase.getId());
                    details.setHouseType(houseType);
                    insertDatas.add(details);
                }
            }
            //1.哪些数据是数据库中没有的，新增的
            insertBatch(insertDatas);
            //2.需要批量修改哪些数据 (插入临时表，然后两表更新)
            rentIncomeDetailsTemporaryService.insertBatchByDetails(updateDatas,2000);
            //两表关联修改
            rentIncomeDetailsTemporaryService.updateDetails(incomeBase.getId());
            //删除临时表数据
            Map deleteMap = new HashMap();
            deleteMap.put("base_id",incomeBase.getId());
            rentIncomeDetailsTemporaryService.deleteByMap(deleteMap);
        }

    }
    @Override
    public List<Map> listBillGroupByMonth(String idCard, String houseType, String address) {
        return baseMapper.listBillGroupByMonth(idCard, houseType, address);
    }

    @Override
    public RentIncomeDetailVO getBillById(Integer detailId, String houseType) {
        return baseMapper.getBillById(detailId, houseType);
    }

    @Override
    public WeiRentalPayBackDTO selectRentalDTO(Integer detailId) {
        return baseMapper.selectRentalDTO(detailId);
    }

    @Override
    public List<WeiRentalPayBackDTO> selectRentalDTOList(Integer month) {
        return baseMapper.selectRentalDTOList(month);
    }


    @Override
    public List selectExcelList(Map para) {
        return baseMapper.selectPageList(para);
    }

}
