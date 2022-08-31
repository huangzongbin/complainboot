package com.krt.rent.mapper;

import com.krt.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.krt.rent.entity.RentHouse;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 住户管理映射层
 *
 * @author 陈仁豪
 * @version 1.0
 * @date 2019年06月09日
 */
@Mapper
public interface RentHouseMapper extends BaseMapper<RentHouse>{

    /**
     * 判断身份证号是否已绑定过
     * @param idCard
     * @return
     */
    Map judgeIdCardExist(@Param("idCard") String idCard, @Param("houseType") String houseType,@Param("houseAddr") String houseAddr);

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
    List<Map> listBillByIdCard(@Param("idCard") String idCard, @Param("id") Integer id);

    void deleteAllByHouseType(String houseType);

    /**
     *  删除不存在另一种类型的账号
     * @param houseType
     */
    void deleteUserJoin(String houseType);

    void insertUserByHourse(@Param("salt") String salt,@Param("password") String password);
}
