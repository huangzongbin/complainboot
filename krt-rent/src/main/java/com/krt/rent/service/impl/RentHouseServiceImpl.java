package com.krt.rent.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.krt.common.base.BaseServiceImpl;
import com.krt.common.util.IdUtils;
import com.krt.common.util.ShiroUtils;
import com.krt.common.validator.Assert;
import com.krt.rent.entity.RentHouse;
import com.krt.rent.mapper.RentHouseMapper;
import com.krt.rent.service.IRentHouseService;
import com.krt.rent.service.IRentIncomeBaseService;
import com.krt.sys.entity.User;
import com.krt.sys.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 住户管理服务接口实现层
 *
 * @author 陈仁豪
 * @version 1.0
 * @date 2019年06月09日
 */
@Service
@Slf4j
public class RentHouseServiceImpl extends BaseServiceImpl<RentHouseMapper, RentHouse> implements IRentHouseService {

    @Autowired
    private RentHouseMapper rentHouseMapper;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRentIncomeBaseService rentIncomeBaseService;

    /**
     * 判断身份证号是否已绑定过
     */
    public Map judgeIdCardExist(String idCard, String houseType,String houseAddr){
        return rentHouseMapper.judgeIdCardExist(idCard,houseType,houseAddr);
    }

    /**
     * 查询数据库中的已存在身份证号数据
     * @param list
     * @return
     */
    public List<String> selectExistIdCardsByList(List<RentHouse> list) {
        return rentHouseMapper.selectExistIdCardsByList(list);
    }

    @Override
    public List<Map> listBillByIdCard(String idCard, Integer id) {
        return rentHouseMapper.listBillByIdCard(idCard,id);
    }

    @Override
    public RentHouse getRentHouseByIdCard(String idCard, String houseType) {
        // 获取租户信息
        LambdaQueryWrapper<RentHouse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RentHouse::getIdCard,idCard);
        queryWrapper.eq(RentHouse::getHouseType,houseType);
        RentHouse rentHouse = selectOne(queryWrapper);
        log.info("身份证号查询了租金信息============{}",idCard);
        Assert.isNull(rentHouse,"租户信息为空");
        return rentHouse;
    }


    /**
     * 新增租户信息，新增用户信息
     * @param e
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertRent(RentHouse e){
        super.insert(e);
        User user = new User();
        user.setIdCard(e.getIdCard());
        user.setPassword("krt123456");
        user.setName(e.getName());
        user.setUsername(e.getIdCard());
        user.setPhone(e.getPhone());
        User userTemp = userService.selectByUsername(user);
        if(ObjectUtil.isNull(userTemp)){
            userService.insert(user);
        }
    }

    /**
     * 修改租户信息同步修改用户信息
     * @param e
     * @return
     */
    public void updateRentById(RentHouse e){
        super.updateById(e);
        User user = new User();
        User userCheck = userService.selectByUsername(user);
        if(userCheck!=null){
            User userUpdate = new User();
            userUpdate.setPhone(e.getPhone());
            userUpdate.setName(e.getName());
            userUpdate.setId(userCheck.getId());
            userUpdate.setUsername(e.getIdCard());
            userService.updateById(userCheck);
        }
    }

    /**
     * 删除租户信息同步删除用户信息
     */
    public void deleteRentById(Integer id){
        RentHouse renthouse = selectById(id);
        if(renthouse!=null) {
            User user = new User();
            user.setIdCard(renthouse.getIdCard());
            int i = userService.deleteByIdcard(user);
        }
        super.deleteById(id);
    }

    /**
     * 导入租户用户
     * @param list
     * @param batchSize
     * @return
     */
    public void insertRentBatch(List<RentHouse> list, Integer batchSize, String houseType){

        //根据住户类型直接删除后换新数据，用户表中的对应数据也删除
        // 排除删除另一种类型的用户
        rentHouseMapper.deleteUserJoin(houseType);
        rentHouseMapper.deleteAllByHouseType(houseType);
        insertBatch(list,batchSize);
        List<User> users = userService.selectList();
        List<String> userNames = users.stream().map(User::getUsername).collect(Collectors.toList());
        List<User> saveUserList = new ArrayList<>();
        String salt = IdUtils.getUUID();
        String password = ShiroUtils.getMD5("krt123456", salt);
        ArrayList<String> addUser = new ArrayList<>();
        for (RentHouse rentHouse : list) {
            if(!userNames.contains(rentHouse.getIdCard()) && !addUser.contains(rentHouse.getIdCard())){
                User user = new User();
                user.setIdCard(rentHouse.getIdCard());
                user.setUsername(rentHouse.getIdCard());
                user.setPhone(rentHouse.getPhone());
                user.setPassword(password);
                user.setName(rentHouse.getName());
                user.setSalt(salt);
                user.setStatus("0");
                saveUserList.add(user);
                addUser.add(rentHouse.getIdCard());
            }
        }
        if(CollectionUtil.isNotEmpty(saveUserList)){
            userService.insertBatch(saveUserList, 2000);
        }
        //获取加密盐

       // rentHouseMapper.insertUserByHourse(salt,password);
    }

    /**
     * 批量删除租户用户
     */
    public void deleteRentByIds(List<Integer> list){
        System.out.println(list);
        Iterator it = list.iterator();
        while(it.hasNext()){
            Integer i = (Integer)it.next();
            RentHouse rent = selectById(i);
            System.out.println(rent);
            User user = new User();
            user.setUsername(rent.getIdCard());
            User deluser = userService.selectByUsername(user);
            userService.deleteById(deluser.getId());
            super.deleteById(rent.getId());
        }
    }

    /**
     * 判断密码长度
     */
    public User judgePsw(User user){
        String oldpsw = user.getPassword();
        if(oldpsw.length()<=6){
            return user;
        }else{
            String newpsw = oldpsw.substring(user.getPassword().length()-6);
            user.setPassword(newpsw);
            return user;
        }
    }

}
