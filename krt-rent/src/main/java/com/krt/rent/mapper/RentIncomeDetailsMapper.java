package com.krt.rent.mapper;

import com.krt.common.base.BaseMapper;
import com.krt.pay.dto.WeiRentalPayBackDTO;
import com.krt.rent.vo.RentIncomeDetailVO;
import org.apache.ibatis.annotations.Mapper;
import com.krt.rent.entity.RentIncomeDetails;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 租金管理详情表映射层
 *
 * @author ylf
 * @version 1.0
 * @date 2019年06月10日
 */
@Mapper
public interface RentIncomeDetailsMapper extends BaseMapper<RentIncomeDetails> {

    /**
     * 哪些 身份证号 是数据库中有的
     *
     * @param list
     * @return
     */
    List<String> selectExistByList(@Param("list") List<RentIncomeDetails> list,@Param("baseId") Integer baseId, @Param("houseType") String houseType);

    /**
     * 获取账单列表
     *
     * @param idCard
     * @return
     */
    List<Map> listBillGroupByMonth(@Param("idCard") String idCard, @Param("houseType") String houseType, @Param("address") String address);

    /**
     * 获取月账单详情
     *
     * @param detailId
     * @return
     */
    RentIncomeDetailVO getBillById(@Param("detailId") Integer detailId, @Param("houseType") String houseType);

    /**
     * 月初更新
     */
    void updateBeginMonth();

    WeiRentalPayBackDTO selectRentalDTO(@Param("detailId") Integer detailId);

    /**
     * 查询缴费成功数据
     *
     * @param month
     * @return
     */
    List<WeiRentalPayBackDTO> selectRentalDTOList(@Param("month") Integer month);
}
