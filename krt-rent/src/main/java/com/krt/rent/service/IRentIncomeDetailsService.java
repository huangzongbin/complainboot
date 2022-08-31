package com.krt.rent.service;

import com.krt.pay.dto.WeiRentalPayBackDTO;
import com.krt.rent.entity.RentIncomeBase;
import com.krt.rent.entity.RentIncomeDetails;
import com.krt.common.base.IBaseService;
import com.krt.rent.vo.RentIncomeDetailVO;

import java.util.List;
import java.util.Map;


/**
 * 租金管理详情表服务接口层
 *
 * @author ylf
 * @version 1.0
 * @date 2019年06月10日
 */
public interface IRentIncomeDetailsService extends IBaseService<RentIncomeDetails>{

    /**
     * 租金缴纳情况列表
     * @param idCard
     * @return
     */
    List<Map> listBillGroupByMonth(String idCard, String houseType, String address);

    void insertExcelIn(RentIncomeBase incomeBase,List<RentIncomeDetails> list,String year , String month, String houseType);
    /**
     *
     * @param detailId
     */
    RentIncomeDetailVO getBillById(Integer detailId, String houseType);

    /**
     * 查询通知保障房数据
     * @param detailId
     * @return
     */
    WeiRentalPayBackDTO selectRentalDTO(Integer detailId);

    /**
     * 查询缴费成功数据
     * @param month
     * @return
     */
    List<WeiRentalPayBackDTO> selectRentalDTOList(Integer month);
}
