package com.krt.api.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.krt.api.controller.vo.RentHouseVO;
import com.krt.api.dto.UserDTO;
import com.krt.common.annotation.KrtIgnoreAuth;
import com.krt.common.annotation.KrtLock;
import com.krt.common.annotation.KrtLog;
import com.krt.common.base.BaseController;
import com.krt.common.bean.ReturnBean;
import com.krt.common.constant.GlobalConstant;
import com.krt.common.exception.KrtException;
import com.krt.common.util.AES2;
import com.krt.common.util.PayUtils;
import com.krt.common.util.TLinx2Util;
import com.krt.common.validator.Assert;
import com.krt.pay.entity.PayNotify;
import com.krt.pay.enums.BillStatusEnums;
import com.krt.pay.service.IPayOrderService;
import com.krt.rent.entity.RentHouse;
import com.krt.rent.entity.RentIncomeBase;
import com.krt.rent.entity.RentIncomeDetails;
import com.krt.rent.enums.HouseTypeEnum;
import com.krt.rent.service.IRentHouseService;
import com.krt.rent.service.IRentIncomeBaseService;
import com.krt.rent.service.IRentIncomeDetailsService;
import com.krt.rent.vo.RentIncomeDetailVO;
import com.sun.org.apache.bcel.internal.generic.NEW;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.apache.poi.ss.usermodel.Workbook;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 租金账单以及租金支付
 *
 * @author zhangdb localhost:8080/complain/wechat/rentPayment-userBind.html
 * @date 2019/6/10 9:09
 */

@Slf4j
@RestController
@RequestMapping("api")
@Api(description = "租金账单以及支付")
public class ApiPayRentController extends BaseController {

    @Autowired
    private IRentHouseService rentHouseService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private IPayOrderService payOrderService;

    @Autowired
    private IRentIncomeBaseService rentIncomeBaseService;

    @Autowired
    private IRentIncomeDetailsService rentIncomeDetailsService;




    /**
     * 判断当前是否开启了缴费入口
     *
     * @return
     */
    @KrtIgnoreAuth
    @PostMapping("payEntranceIsOpen")
    @ApiOperation(value = "判断当前是否开启了缴费入口", notes = "返回false,则无法进行任何操作")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "houseType", value = "房屋类型 0住宅，1非住宅", required = true),
    })
    public ReturnBean getPaymentStatus(String houseType) {
        Assert.isBlank(houseType,"房源类型为空");
        return ReturnBean.ok(rentIncomeBaseService.entranceIsOpen(null,houseType));
    }

    /**
     * 租金缴纳列表页
     *
     * @param idCard 身份证号
     * @return
     */
    @PostMapping("listBill")
    @ApiOperation(value = "租金缴纳情况列表", notes = "缴纳状态 0待缴纳 1已缴纳 2已划扣</br>" +
            "billList 中 payRentEntranceStatus=0 并且 isOver=1 时，表示缴纳租金功能已关闭 ")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "accessToken", name = "accessToken", value = "鉴权token", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "idCard", value = "身份证号", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "houseType", value = "房屋类型 0住宅，1非住宅", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "houseId", value = "房源Id"),

            //@ApiImplicitParam(paramType = "query", dataType = "string", name = "uniqueId", value = "微信在公众号唯一标识", required = true),
    })
    public ReturnBean listBill(String idCard, String houseType,String houseId) {
        // String weChatUuid = payOrderService.getWeChatUuid(uniqueId);
        checkIdCard(idCard);
        Assert.isBlank(houseType, "房源类型不能为空");
        RentHouse rentHouse = null ;
        Map<String, Object> resultMap = new HashMap<>(2);
        if(HouseTypeEnum.FZZ.getValue().equals(houseType)){
            Assert.isBlank(houseId, "非住宅房源标识为空");
            rentHouse = rentHouseService.selectById(Integer.valueOf(houseId));
            Assert.isNull(rentHouse,"租户信息为空");
        }else{
            rentHouse = rentHouseService.getRentHouseByIdCard(idCard,houseType);
        }
        // 获取租户信息
        resultMap.put("userInfo", rentHouse);
        // 获取租金缴纳情况列表 住宅地址可以为空，非住宅的地址必须传值
        List<Map> billList = rentIncomeDetailsService.listBillGroupByMonth(idCard,houseType,rentHouse.getAddress());
        resultMap.put("billList", billList);
        return ReturnBean.ok(resultMap);
    }


    /**
     * 单月租金缴纳详情
     *
     * @param idCard 身份证号
     * @return
     */
    @PostMapping("getBillById")
    @ApiOperation(value = "单月租金缴纳详情", notes = "缴纳状态 0待缴纳 1已缴纳 2已划扣;<br/>" +
            "支付状态 1交易成功，2待支付，4已取消，9等待用户输入密码确认")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "accessToken", name = "accessToken", value = "鉴权token", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "idCard", value = "身份证号", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "detailId", value = "id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "houseType", value = "房屋类型 0住宅，1非住宅", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "houseId", value = "房源Id"),
            //@ApiImplicitParam(paramType = "query", dataType = "string", name = "uniqueId", value = "微信在公众号唯一标识", required = true),
    })
    public ReturnBean getBillById(String idCard, Integer detailId, String houseType,String houseId) {

        checkIdCard(idCard);
        Assert.isBlank(houseType, "房子类型不能为空");
        Map<String, Object> resultMap = new HashMap<>(2);
        // 获取租户信息
        RentHouse rentHouse = null ;
        if(HouseTypeEnum.FZZ.getValue().equals(houseType)){
            Assert.isBlank(houseId, "非住宅房源标识为空");
            rentHouse = rentHouseService.selectById(Integer.valueOf(houseId));
            Assert.isNull(rentHouse,"租户信息为空");
        }else{
            rentHouse = rentHouseService.getRentHouseByIdCard(idCard,houseType);
        }
        resultMap.put("userInfo", rentHouse);

        // 获取租金缴纳情况详情
        RentIncomeDetailVO billDetail = rentIncomeDetailsService.getBillById(detailId,houseType);
        if(ObjectUtil.isEmpty(billDetail)){
            return ReturnBean.error("未查询到租金缴纳详情");
        }
        resultMap.put("billDetail", billDetail);

        return ReturnBean.ok(resultMap);
    }


    /**
     * 单月租金缴纳详情
     *
     *
     * @return
     */
    @KrtIgnoreAuth
    @PostMapping("selectExistsHouse")
    @ApiOperation(value = "查询当前用户是否存在需要缴费的房屋")
    public ReturnBean selectExistsHouse(@RequestBody UserDTO userDTO) {
        int count = rentHouseService.selectCount(Wrappers.<RentHouse>lambdaQuery()
                .eq(RentHouse::getName, userDTO.getUsername())
                .eq(RentHouse::getIdCard, userDTO.getPassword())
        );
        return ReturnBean.ok(count);
    }

    @KrtLog("支付功能拉起支付接口调用")
    @PostMapping("pay")
    @ApiOperation(value = "支付", notes = "每月1日凌晨会计算滞纳金，返回code == 2,弹出账单金额发生变化，请重新获取账单，并重新加载账单</br>" +
            "code = 1 该月租金账单已缴纳成功,重新加载账单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "accessToken", name = "accessToken", value = "鉴权token", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "totalRental", value = "应缴金额", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "detailId", value = "缴纳详情id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "dataFrom", value = "数据来源（1 小程序）", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "subOpenid", value = "openid", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "ifAdvance", value = "是否预缴 0否，1是", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "months", value = "预缴月份(个)", required = false)
    })
    @KrtLock(lockName = "#detailId", leaveTime = 10000, waitTime = 12000)
    public ReturnBean pay(String totalRental, Integer detailId, String dataFrom, String subOpenid,String ifAdvance, Integer months) throws Exception {
        BoundValueOperations boundValueOperations = redisTemplate.boundValueOps("payDetailId#" + detailId);
        boolean pass = boundValueOperations.setIfAbsent("1"); // flag 表示的是否set

        if (!pass) {
            return ReturnBean.error(2, "账单金额发生变化，请重新获取账单。一分钟内只能提交一次订单！");
        }
        boundValueOperations.expire(61, TimeUnit.SECONDS);

        Assert.isBlank(totalRental, "应缴金额为空");
        // Assert.isBlank(ifAdvance, "是否预缴的参数必填");

        RentIncomeDetails rentIncomeDetails = rentIncomeDetailsService.selectById(detailId);
        Assert.isNull(rentIncomeDetails, "账单信息有误");
        RentIncomeBase rentIncomeBase = rentIncomeBaseService.selectById(rentIncomeDetails.getBaseId());
        Assert.isNull(rentIncomeBase,"账单信息有误");

        if(GlobalConstant.IF_ADVANCE_YES.equals(ifAdvance)){
            if(ObjectUtil.isNull(months) || months <1 ){
                return ReturnBean.error("预缴月份参数不正确！");
            }
            BigDecimal rentMonth = new BigDecimal(rentIncomeBase.getMonth());
            if(rentMonth.add(BigDecimal.valueOf(months)).compareTo(BigDecimal.valueOf(12))>0){
                return ReturnBean.error("预缴月不可以进行跨年！");
            }
        }

        // 判断缴纳租金入口是否关闭
        if (!rentIncomeBaseService.entranceIsOpen(rentIncomeDetails.getBaseId(),rentIncomeDetails.getHouseType())) {
            return ReturnBean.error(5, "数据正在导入,未开启缴费，请留意最新动态");
        }
        if(GlobalConstant.IF_ADVANCE_NO.equals(ifAdvance)){
            // 校验总金额是否与账单金额一致
            if (!totalRental.equals(rentIncomeDetails.getTotalRental())) {
                return ReturnBean.error(2, "账单金额发生变化，请重新获取账单");
            }
        }else if(GlobalConstant.IF_ADVANCE_YES.equals(ifAdvance)){
            List<Map> list = rentIncomeDetailsService.listBillGroupByMonth(rentIncomeDetails.getIdCard(), rentIncomeDetails.getHouseType(), rentIncomeDetails.getAddress());
            if(CollectionUtil.isNotEmpty(list)){
                Map map = list.get(0);
                if(!(detailId+"").equals(map.get("detailId")+"")){
                    return ReturnBean.error(2,"只有最新未缴纳的月份才可以进行预缴！");
                }
                BigDecimal monthNum = new BigDecimal(months);
                BigDecimal oldTotalRent = new BigDecimal(rentIncomeDetails.getTotalRental());
                BigDecimal rentEnd = monthNum.multiply(rentIncomeDetails.getAdvancePrice()).add(oldTotalRent);
                if(rentEnd.compareTo(new BigDecimal(totalRental)) != 0){
                    return ReturnBean.error(2,"支付金额与账单不符合！");
                }
            }else{
                return ReturnBean.error("未查询到订单！");
            }
        }else{
            return ReturnBean.error("是否预缴参数错误！");
        }

        boolean payCheck = payOrderService.payCheck(rentIncomeDetails);
        // 校验租金是否已经缴纳l
        if (!BillStatusEnums.DJN.getValue().equals(rentIncomeDetails.getStatus()) || payCheck) {
            return ReturnBean.error(1, "该月租金账单已缴纳成功");
        }
        // 支付
        // 订单名称命名规则 用户名 + X 年 + X 月保障性住房租金
        String orderName = rentIncomeDetails.getName() + rentIncomeBase.getYear() + "年" + rentIncomeBase.getMonth() + "月保障性住房租金";
        String jsApiPayUrl = null;
        try {
            jsApiPayUrl = payOrderService.pay(rentIncomeDetails, dataFrom, subOpenid, orderName,rentIncomeBase,months,totalRental);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("操作失败{}",e.getMessage());
            return ReturnBean.error("操作失败");
        }
        return ReturnBean.ok(jsApiPayUrl);
    }


    @KrtLog("支付功能回调")
    @KrtIgnoreAuth
    @GetMapping("notify")
    public String payNotify() throws Exception {
       /* WeiRentalPayBackDTO weiRentalPayBackDTO = new WeiRentalPayBackDTO();
        weiRentalPayBackDTO.setPaymentTime(DateUtils.getDate());
        weiRentalPayBackDTO.setOutNo("12312222222222222223");
        log.error("推送到保障房系统数据{}", JSON.toJSONString(weiRentalPayBackDTO));
        jmsTemplateQueue.convertAndSend(weiRentalPayBackQueue3, JSON.toJSONString(weiRentalPayBackDTO));*/
//        jmsTemplateTopic.convertAndSend(defaultTopic, JSON.toJSONString(weiRentalPayBackDTO));
        log.error("支付回调接口进来了");
        // 验签
        Map<String, String> params = new TreeMap<String, String>();

        // 取出所有参数是为了验证签名
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            params.put(parameterName, request.getParameter(parameterName));
        }

        JSONObject paramsObject = JSONObject.parseObject(JSON.toJSONString(params));
        String open_key = AES2.Decrypt(PayUtils.open_key, "k8356742k8356742", 2);
        boolean verifySign = TLinx2Util.verifySign(paramsObject, open_key);

        // 签名验证规则
        if (verifySign) {
            // {"ord_no":"9156447066282840495371462","out_no":"89486krt83567421564470662","rand_str":"CP9FjUswyT79EIgTfJIC00XUo370u5R6V0MfVeLt8TDMBUGRDouEorzMuGNYMF5HFRWB6I5eCI1dDH5h5AVu1vhwb5vXKAFqsC1yk6NWPOasvgJBQF6SanomsTkduZDW","sign":"d9500018832e970107bcf72b956a3c27","status":"1","timestamp":"1564470672"}
            PayNotify payNotify = JSON.toJavaObject(paramsObject, PayNotify.class);
            Boolean notifySuccess = payOrderService.payNotify(payNotify);
            log.error("支付回调接口成功出去了");
            if (notifySuccess) {
                return "notify_success";
            }
        }
        log.error("支付回调接口失败出去了");
        return "";
//        Boolean notifySuccess = payOrderService.payNotify(payNotify);
    }

    /**
     * 最近创建的订单数
     *
     * @return
     */
    @PostMapping("getRecentOrdCount")
    @ApiOperation(value = "最近创建的订单数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "accessToken", name = "accessToken", value = "鉴权token", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "detailId", value = "id", required = true)
    })
    public ReturnBean<Integer> getRecentOrdCount(Integer detailId) {
        // payOrderService.getRecentOrdCount(detailId)
        return ReturnBean.ok(0);
    }


    /**
     * 最近创建的订单数
     *
     * @return
     */
    @PostMapping("getRentHoseList")
    @ApiOperation(value = "获取当前用户承租房子的套数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "accessToken", name = "accessToken", value = "鉴权token", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "idCard", value = "身份证号", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "houseType", value = "房屋类型 0住宅，1非住宅", required = true),
    })
    public ReturnBean<Map> getRentHoseList(String idCard, String houseType) {
        checkIdCard(idCard);
        Assert.isBlank(houseType, "房子类型不能为空");
        HashMap<String, Object> data = new HashMap<>();
        LambdaUpdateWrapper<RentHouse> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(RentHouse::getIdCard,idCard)
                .eq(RentHouse::getHouseType,houseType);
        List<RentHouse> rentHouseList = rentHouseService.selectList(wrapper);
        if(CollectionUtil.isEmpty(rentHouseList)){
            return ReturnBean.error("未查询到该类型需要缴纳的费用");
        }
        List<RentHouseVO> addressList = rentHouseList.stream().map(e -> {
            RentHouseVO rentHouse = new RentHouseVO();
            rentHouse.setId(e.getId());
            rentHouse.setAddress(e.getAddress());
            return rentHouse;
        }).collect(Collectors.toList());
        RentHouse rentHouse = rentHouseList.get(0);
        data.put("name",rentHouse.getName());
        data.put("idcard",rentHouse.getIdCard());
        data.put("phone",rentHouse.getPhone());
        data.put("count",addressList.size());
        data.put("list",addressList);
        return ReturnBean.ok(data);
    }

    /**
     *  内部调用，监测身份证
     * @param idCard
     */
    private void checkIdCard(String idCard){
        if(!IdcardUtil.isValidCard(idCard)){
           throw new KrtException(ReturnBean.error("身份证格式错误！"));
        }
    }
}
