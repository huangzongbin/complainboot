package com.krt.rent.service;

import com.krt.rent.entity.RentHouse;
import com.krt.common.base.IBaseService;

import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * 住户管理服务接口层
 *
 * @author 陈仁豪
 * @version 1.0
 * @date 2019年06月09日
 */
public interface IRentHouseService extends IBaseService<RentHouse>{

    /**
     * 判断身份证号是否已绑定过
     */
    Map judgeIdCardExist(String idCard,String houseType,String houseAddr);

    /**
     * 查询数据库中的已存在身份证号数据
     * @param list
     * @return
     */
    List<String> selectExistIdCardsByList(List<RentHouse> list);

    /**
     * 通过身份证号获取租金缴纳情况
     * @param idCard
     * @param id
     * @return
     */
    List<Map> listBillByIdCard(String idCard, Integer id);

    /**
     * 通过身份证号获取租户信息
     * @param idCard
     * @return
     */
    RentHouse getRentHouseByIdCard(String idCard, String houseType);


    /**
     * 添加租户同时添加用户信息
     * @param rent
     * @return
     */
    void insertRent(RentHouse rent);

    /**
     * 更新租户信息同时更新对应用户信息
     * @param rent
     * @return
     */
    void updateRentById(RentHouse rent);

    /**
     * 删除租户信息同时删除对应用户信息
     */
    void deleteRentById(Integer id);

    /**
     * 导入租户用户
     */
    void insertRentBatch(List<RentHouse> list,Integer batchsize,String houseType);

    /**
     *  批量删除租户用户
     */
    void deleteRentByIds(List<Integer> list);

}
