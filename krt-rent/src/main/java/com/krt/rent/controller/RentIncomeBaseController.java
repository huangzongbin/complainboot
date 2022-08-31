package com.krt.rent.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.krt.common.annotation.KrtLog;
import com.krt.common.base.BaseController;
import com.krt.common.bean.DataTable;
import com.krt.common.bean.ReturnBean;
import com.krt.common.exception.KrtException;
import com.krt.common.util.DateUtils;
import com.krt.common.util.NumberUtils;
import com.krt.common.util.StringUtils;
import com.krt.common.validator.Assert;
import com.krt.msgLog.entity.TMsgLog;
import com.krt.msgLog.service.ITMsgLogService;
import com.krt.rent.entity.RentHouse;
import com.krt.rent.entity.RentIncomeBase;
import com.krt.rent.entity.RentIncomeDetails;
import com.krt.rent.enums.HouseTypeEnum;
import com.krt.rent.enums.RentIncomeBaseStatusEnum;
import com.krt.rent.service.IRentIncomeBaseService;
import com.krt.rent.service.IRentIncomeDetailsService;
import com.krt.rent.utils.DateUtil;
import com.krt.rent.utils.ExcelImportUtil;
import com.krt.rent.utils.GroupMAS;
import com.krt.sys.entity.Dic;
import com.krt.sys.service.IDicService;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * 租金管理基础信息表控制层
 *
 * @author ylf
 * @version 1.0
 * @date 2019年06月10日
 */
@Controller
@Slf4j
public class RentIncomeBaseController extends BaseController {

    @Autowired
    private IDicService dicService;
    @Autowired
    private IRentIncomeBaseService rentIncomeBaseService;

    @Autowired
    private IRentIncomeDetailsService rentIncomeDetailsService;

    @Autowired
    private ITMsgLogService tMsgLogService;

    /**
     * 租金管理基础信息表管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("rentIncomeBase:rentIncomeBase:list")
    @GetMapping("rent/rentIncomeBase/list")
    public String list(String houseType) {
        checkHouseType(houseType,true);
        return "rent/rentIncomeBase/list";
    }

    /**
     * 租金管理基础信息表管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("rentIncomeBase:rentIncomeBase:list")
    @PostMapping("rent/rentIncomeBase/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage page = rentIncomeBaseService.selectPageList(para);
        return DataTable.ok(page);
    }

    /**
     * 租金管理详情表管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("rentIncomeDetails:rentIncomeDetails:list")
    @GetMapping("rent/rentIncomeBase/detailsList")
    public String detailsList(Integer baseId) {
        request.setAttribute("baseId", baseId);
        return "rent/rentIncomeDetails/detailsList";
    }

    /**
     * 租金管理详情表管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("rentIncomeDetails:rentIncomeDetails:list")
    @PostMapping("rent/rentIncomeBase/detailsList")
    @ResponseBody
    public DataTable detailsList(@RequestParam Map para) {
        IPage page = rentIncomeDetailsService.selectPageList(para);
        return DataTable.ok(page);
    }

    /**
     * 租金缴纳页
     *
     * @param id 租金管理详情表id
     * @return {@link String}
     */
    @RequiresPermissions("rentIncomeDetails:rentIncomeDetails:payment")
    @GetMapping("rent/rentIncomeBase/payment")
    public String payment(Integer id, Integer baseId) {
        RentIncomeBase rentIncomeBase = rentIncomeBaseService.selectById(baseId);
        RentIncomeDetails rentIncomeDetails = rentIncomeDetailsService.selectById(id);
        String data = DateUtils.dateToString("yyyy", new Date());
        Integer year = null;
        Integer month = null;
        if (rentIncomeBase != null) {
            year = Integer.parseInt(data) - Integer.parseInt(rentIncomeBase.getYear());
            String Monthdata = DateUtils.dateToString("MM", new Date());
            month = Integer.parseInt(Monthdata) - Integer.parseInt(rentIncomeBase.getMonth());
        }
        if (year < 0) {
            request.setAttribute("rentIncomeDetails", rentIncomeDetails);
        } else if (year == 0) {
            payMoeny(rentIncomeDetails, month);
        } else if (year == 1) {
            month = month + 12;
            payMoeny(rentIncomeDetails, month);
        } else if (year > 1) {
            if (rentIncomeDetails.getTotalRental() != null) {
                // BigDecimal totalRental = new BigDecimal(rentIncomeDetails.getTotalRental());
                //BigDecimal big = new BigDecimal("0.7");
                //Double totalRental = Double.parseDouble(rentIncomeDetails.getTotalRental()) * 0.7 + Double.parseDouble(rentIncomeDetails.getTotalRental());
                //rentIncomeDetails.setTotalRental(totalRental.multiply(big).add(totalRental).setScale(2,BigDecimal.ROUND_HALF_UP)+"");
                //request.setAttribute("rentIncomeDetails", rentIncomeDetails);
                backMoeny(rentIncomeDetails, "0.7");
            }
        }
        return "rent/rentIncomeDetails/payment";
    }

    /**
     * 租金缴纳
     *
     * @param rentIncomeDetails 租金管理详情表
     * @return {@link ReturnBean}
     */
    @KrtLog("租金缴纳表")
    @RequiresPermissions("rentIncomeDetails:rentIncomeDetails:payment")
    @PostMapping("rent/rentIncomeBase/payment")
    @ResponseBody
    public ReturnBean update(RentIncomeDetails rentIncomeDetails) {
        //修改缴纳状态
        rentIncomeDetails.setStatus(1);
        rentIncomeDetails.setPaymentTiime(new Date());
        rentIncomeDetailsService.updateById(rentIncomeDetails);
        return ReturnBean.ok();
    }


    /**
     * 租金管理基础信息表管理页
     *
     * @return {@link String}
     */
    @GetMapping("rent/rentIncomeBase/whetherPay")
    public String whetherPay(Integer id, String msgType, Integer status, Integer payStatus) {
        boolean closeMessage = isCloseMessage();
        if (closeMessage) {
            request.setAttribute("msgShow", 1);
        }
        request.setAttribute("id", id);
        request.setAttribute("msgType", msgType);
        request.setAttribute("status", status);
        request.setAttribute("payStatus", payStatus);
        Dic dic = dicService.selectByTypeAndCode("msg_type", msgType);
        request.setAttribute("btnName", dic.getName());
        return "rent/rentIncomeBase/whetherPay";
    }


    /**
     * 缴纳金额状态
     *
     * @return {@link ReturnBean}
     */
    @KrtLog("缴纳金额状态")
    @RequiresPermissions("rentIncomeBase:rentIncomeBase:pay")
    @PostMapping("rent/rentIncomeBase/whetherPay")
    @ResponseBody
    public ReturnBean whetherPay(RentIncomeBase rentIncomeBase) {
        String mCode = rentIncomeBase.getMsgCode();

        boolean closeMessage = isCloseMessage();

        if (!closeMessage && StringUtils.isBlank(mCode)) {
            return ReturnBean.error("请输入短信验证码！");
        }
        String msgCode = checkMsgCode(rentIncomeBase.getMobilePhone(), rentIncomeBase.getMsgType(), rentIncomeBase.getId());
        if (closeMessage || (StringUtils.isNotBlank(msgCode) && msgCode.equals(mCode))) {
            if (rentIncomeBase.getStatus() == 0) {
                Date day = DateUtils.dateAdd("day", 1, new Date());
                String data = DateUtils.dateToString("yyyy-MM-dd", day);
                Date date = DateUtils.stringToDate("yyyy-MM-dd", data);
                rentIncomeBase.setOverTime(date);
            }
            rentIncomeBaseService.updateById(rentIncomeBase);
            return ReturnBean.ok();
        } else {
            log.error(msgCode + "-" + JSON.toJSONString(rentIncomeBase));
            return ReturnBean.error("短信，请重新获取");
        }
    }

    /**
     * 获取验证码
     *
     * @param mobile_phone
     * @param msg_type
     * @return
     */
    private String checkMsgCode(String mobile_phone, String msg_type, Integer id) {
        QueryWrapper tMsgLogWrapper = new QueryWrapper<TMsgLog>();
        tMsgLogWrapper.eq("phone", mobile_phone);
        tMsgLogWrapper.eq("type", msg_type);
//        if(id!=null){
        tMsgLogWrapper.eq("cId", id);
//        }
        //十分钟内
        tMsgLogWrapper.between("insert_time", new Date(System.currentTimeMillis() - 600000), new Date());
        tMsgLogWrapper.orderByDesc("insert_time");
        List<TMsgLog> tMsgLogList = tMsgLogService.selectList(tMsgLogWrapper);
        if (CollectionUtils.isNotEmpty(tMsgLogList)) {
            TMsgLog tMsgLog = tMsgLogList.get(0);
            String msgCode = tMsgLog.getContent();
            if (StringUtils.isNotBlank(msgCode)) {
                return msgCode;
            }
        }
        return "";
    }


    /**
     * 滞纳金计算的抽取
     */
    private void payMoeny(RentIncomeDetails rentIncomeDetails, Integer month) {
        if (month < 1) {
            request.setAttribute("rentIncomeDetails", rentIncomeDetails);
        } else if (month == 1) {
            if (rentIncomeDetails.getTotalRental() != null) {
                // Double totalRental = Double.parseDouble(rentIncomeDetails.getTotalRental()) * 0.2 + Double.parseDouble(rentIncomeDetails.getTotalRental());
                //rentIncomeDetails.setTotalRental(String.format("%.2f", totalRental));
                //request.setAttribute("rentIncomeDetails", rentIncomeDetails);
                backMoeny(rentIncomeDetails, "0.2");
            }

        } else if (month == 2) {
            if (rentIncomeDetails.getTotalRental() != null) {
                // Double totalRental = Double.parseDouble(rentIncomeDetails.getTotalRental()) * 0.5 + Double.parseDouble(rentIncomeDetails.getTotalRental());
                //rentIncomeDetails.setTotalRental(String.format("%.2f", totalRental));
                //request.setAttribute("rentIncomeDetails", rentIncomeDetails);
                backMoeny(rentIncomeDetails, "0.5");
            }
        } else if (month >= 3) {
            if (rentIncomeDetails.getTotalRental() != null) {
                //Double totalRental = Double.parseDouble(rentIncomeDetails.getTotalRental()) * 0.7 + Double.parseDouble(rentIncomeDetails.getTotalRental());
                //rentIncomeDetails.setTotalRental(String.format("%.2f", totalRental));
                //request.setAttribute("rentIncomeDetails", rentIncomeDetails);
                backMoeny(rentIncomeDetails, "0.7");
            }
        }
    }

    private void backMoeny(RentIncomeDetails rentIncomeDetails, String percentage) {
        BigDecimal totalRental = new BigDecimal(rentIncomeDetails.getTotalRental());
        BigDecimal big = new BigDecimal(percentage);
        rentIncomeDetails.setTotalRental(totalRental.multiply(big).add(totalRental).setScale(2, BigDecimal.ROUND_HALF_UP) + "");
        request.setAttribute("rentIncomeDetails", rentIncomeDetails);
    }


    @GetMapping("rent/rentIncomeBase/excelInFtl")
    public String excelInFtl(String houseType) {
        checkHouseType(houseType,true);
        boolean closeMessage = isCloseMessage();
        if (closeMessage) {
            request.setAttribute("msgShow", 1);
        }
        String id = DateUtil.dateToString(DateUtil.YYMMDD, new Date()) + NumberUtils.randomThreeCode();
        request.setAttribute("id", id);
        return "rent/rentIncomeBase/excelInFtl";
    }

    /**
     * CellValueServer 168
     *
     * @return
     * @throws Exception
     */
    @KrtLog("租金详细情况导入")
    @PostMapping("rent/rentIncomeBase/excelIn")
    @ResponseBody
    public ReturnBean excelIn(@RequestParam("file") MultipartFile file, @RequestParam("year") String year, @RequestParam("month") String month, @RequestParam("id") Integer id,
                              @RequestParam("msgType") String msgType, @RequestParam("msgCode") String msgCode, @RequestParam("mobilePhone") String mobilePhone, @RequestParam("houseType") String houseType) throws Exception {

        Map returnMap = null;
        try {
            Assert.isExcel(file);
            // 检查房源类型
            checkHouseType(houseType,false);
            String mCode = checkMsgCode(mobilePhone, msgType, id);

            boolean closeMessage = isCloseMessage();

            if (!closeMessage) {
                if (StringUtils.isBlank(msgCode) || !mCode.equals(msgCode)) {
                    return ReturnBean.error("短信验证码不正确或者已失效，请重新获取");
                }
            }
            ImportParams params = new ImportParams();
            params.setTitleRows(0);
            params.setHeadRows(1);
            returnMap = new HashMap(5);
            List<RentIncomeDetails> list = ExcelImportUtil.importExcelBySax(file.getInputStream(), RentIncomeDetails.class, params);
            // 检查身份证
            List<String> erroeIdcardList = checkIdCard(list);
            //身份证号重复判断，重复的剔出做提示  返回：文件中重复的身份证号
            List<String> fileExistCards = null;
            if(HouseTypeEnum.ZZ.getValue().equals(houseType)){
                fileExistCards = removeExistExcelData(list);
            }else {
                // 非住宅是存在多套房子的
                fileExistCards = removeExistIdcardAndAddr(list);
            }
            //判断该年月是初次导入还是重复导入
            if (list.size() > 0) {
                QueryWrapper wrapper = new QueryWrapper<RentIncomeBase>();
                wrapper.eq("year", year);
                wrapper.eq("month", month);
                wrapper.eq("house_type",houseType);
                RentIncomeBase incomeBase = rentIncomeBaseService.selectOne(wrapper);
                if (incomeBase != null) {
                    if (incomeBase.getPayStatus() == 2) {
                            returnMap.put("PayStatus", RentIncomeBaseStatusEnum.paying.getStatus());
                    }else if(incomeBase.getPayStatus() == 3){
                            Date overTime = incomeBase.getOverTime();
                            if(overTime != null && (overTime).after(new Date())){
                                returnMap.put("PayStatus", RentIncomeBaseStatusEnum.paying.getStatus());
                            }else{
                                rentIncomeDetailsService.insertExcelIn(incomeBase, list, year, month,houseType);
                            }
                    } else {
                        rentIncomeDetailsService.insertExcelIn(incomeBase, list, year, month,houseType);
                    }
                } else {
                    rentIncomeDetailsService.insertExcelIn(incomeBase, list, year, month,houseType);
                }
                returnMap.put("number", 1);
            } else {
                returnMap.put("number", 0);
            }
            returnMap.put("fileExistCards", fileExistCards);
            returnMap.put("erroeIdcardList", erroeIdcardList);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnBean.error("操作失败");
        }
        return ReturnBean.ok(returnMap);
    }

    private boolean isCloseMessage() {
        //是否关闭短信
        Dic dic = dicService.selectByTypeAndCode("closeMessage", "1");
        boolean closeMessage = false;

        if (null != dic && "1".equals(dic.getName())) {
            closeMessage = true;
        }
        return closeMessage;
    }

    /**
     * 剔除掉 集合 中身份证号相同的数据
     */
    private List<String> removeExistExcelData(List<RentIncomeDetails> list) {
        List<String> msgs = new ArrayList<>();
        List<String> existIdCards = new ArrayList<>();
        List<String> allIdCards = new ArrayList<>();
        for (RentIncomeDetails details : list) {
            //如果身份证号已经刷过这个了，则说明是重复的
            if (allIdCards.contains(details.getIdCard())) {
                //如果还没加入 重复集合中 则加入（防止出现多个重复身份证号问题）
                if (!existIdCards.contains(details.getIdCard())) {
                    existIdCards.add(details.getIdCard());
                    //    msgs.add("文件中身份证号为：" + house.getIdCard() + " 存在多个数据");
                    msgs.add(details.getIdCard());
                }
            } else {
                allIdCards.add(details.getIdCard());
            }
        }
        //删除掉重复身份证号数据
        Iterator it = list.iterator();
        while (it.hasNext()) {
            RentIncomeDetails details = (RentIncomeDetails) it.next();
            details.setIdcardAddr(details.getIdCard());
            if (existIdCards.contains(details.getIdCard())) {
                it.remove();
            }
        }
        return msgs;
    }

    /**
     * 剔除掉 集合 中身份证号-房源地址 相同的数据
     */
    private List<String> removeExistIdcardAndAddr(List<RentIncomeDetails> list){
        List<String> msgs = new ArrayList<>();
        List<String> existIdCards = new ArrayList<>();
        List<String> allIdCards = new ArrayList<>();
        for (RentIncomeDetails details : list) {
            if(ObjectUtil.isEmpty(details.getAddress())){
                continue;
            }
            // 将身份证和地址进行联合去重
            String idcardAddr = details.getIdCard().trim() +"-"+ details.getAddress().trim();
            //如果身份证号已经刷过这个了，则说明是重复的
            if (allIdCards.contains(idcardAddr)) {
                //如果还没加入 重复集合中 则加入（防止出现多个重复身份证号问题）
                if (!existIdCards.contains(idcardAddr)) {
                    existIdCards.add(idcardAddr);
                    //    msgs.add("文件中身份证号为：" + house.getIdCard() + " 存在多个数据");
                    msgs.add(idcardAddr);
                }
            } else {
                allIdCards.add(idcardAddr);
            }
        }
        //删除掉重复身份证号数据
        Iterator it = list.iterator();
        while (it.hasNext()) {
            RentIncomeDetails details = (RentIncomeDetails) it.next();
            details.setIdcardAddr(details.getIdCard()+"-"+details.getAddress());
            if ( ObjectUtil.isEmpty(details.getIdCard()) ||  ObjectUtil.isEmpty(details.getAddress()) || existIdCards.contains(details.getIdCard())) {
                it.remove();
            }
        }
        return msgs;
    }

    /**
     *  检测身份证是否有错误
     * @param list
     * @return
     */
    private List<String> checkIdCard(List<RentIncomeDetails> list){
        ArrayList<String> erroeIdcardList = new ArrayList<>();
        Iterator<RentIncomeDetails> iterator = list.iterator();
        while (iterator.hasNext()){
            RentIncomeDetails details = iterator.next();
            if(!IdcardUtil.isValidCard(details.getIdCard())){
                if(ObjectUtil.isNotEmpty(details.getIdCard())){
                    erroeIdcardList.add(details.getIdCard());
                }
                iterator.remove();
                continue;
            }
            if(ObjectUtil.isEmpty(details.getTotalRental())){
                erroeIdcardList.add(details.getIdCard());
                iterator.remove();
                continue;
            }
            if(ObjectUtil.isEmpty(details.getAdvanceProperty())){
                erroeIdcardList.add(details.getIdCard());
                iterator.remove();
                continue;
            }
            if(ObjectUtil.isEmpty(details.getAdvanceRental())){
                erroeIdcardList.add(details.getIdCard());
                iterator.remove();
                continue;
            }
            if(ObjectUtil.isEmpty(details.getAddress())){
                erroeIdcardList.add(details.getIdCard());
                iterator.remove();
                continue;
            }
            // 计算预缴月租金
            details.setAdvancePrice(details.getAdvanceProperty().add(details.getAdvanceRental()));
        }
        return erroeIdcardList;
    }

    @GetMapping("rent/rentIncomeDetails/excelOut")
    public ModelAndView individualCompanyDownload(@RequestParam Map para) throws IOException {
        Integer baseId = Integer.parseInt(para.get("baseId") + "");
        RentIncomeBase rentIncomeBase = rentIncomeBaseService.selectById(baseId);
        List list = rentIncomeDetailsService.selectExcelList(para);
        String dateStr = rentIncomeBase.getYear() + "年" + rentIncomeBase.getMonth() + "月";
        String outTitle = dateStr + "租金缴纳详细情况";
        ExportParams exportParams = new ExportParams(outTitle, dateStr);
        exportParams.setType(ExcelType.XSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, RentIncomeDetails.class, list);
        //根据workBook的类型进行生成后缀
        if (ExcelType.HSSF.equals(exportParams.getType())) {
            outTitle += ".xls";
        } else {
            outTitle += ".xlsx";
        }
        //导出
        setIoResponseHeader(workbook, outTitle);
        return null;
    }

    /**
     * 导出时设置io返回流的信息
     */
    private void setIoResponseHeader(Workbook workbook, String title) throws IOException {
        response.reset(); //清除buffer缓存
        // 指定下载的文件名，浏览器都会使用本地编码，即GBK，浏览器收到这个文件名后，用ISO-8859-1来解码，然后用GBK来显示 +".xlsx"
        // 所以我们用GBK解码，ISO-8859-1来编码，在浏览器那边会反过来执行。
        response.setHeader("Content-Disposition", "attachment;filename=" + new String((title).getBytes("UTF-8"), "ISO-8859-1"));
        //如果使用该ContentType则浏览器自动下载到默认下载地址
//        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        //如果使用这两行代码则浏览器会弹出另存为的弹窗
        response.setHeader("Content-Type", "application/force-download");
        response.setContentType("application/x-download;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        OutputStream output = null;
        BufferedOutputStream bufferedOutPut = null;
        try {
            output = response.getOutputStream();
            bufferedOutPut = new BufferedOutputStream(output);
            // 执行 flush 操作， 将缓存区内的信息更新到文件上
            bufferedOutPut.flush();
            // 将最新 的 Excel 文件写入到文件输出流中，更新文件信息
            workbook.write(bufferedOutPut);
        } catch (Exception e) {
            throw e;
        } finally {
            if (bufferedOutPut != null) {
                // 关闭输出流对象
                bufferedOutPut.close();
            }
            if (output != null) {
                output.close();
            }
        }
    }

    /**
     * 短信验证码发送
     *
     * @return {@link ReturnBean}
     */
    @KrtLog("短信验证码发送")
    @PostMapping("rent/rentIncomeBase/sendMsgCode")
    @ResponseBody
    public ReturnBean sendMsgCode(@RequestParam("id") Integer id, @RequestParam("msgType") String msgType, @RequestParam("mobilePhone") String mobilePhone) {
        if (StringUtils.isBlank(mobilePhone)) {
            return ReturnBean.error("请选择需要发送的手机号码");
        }
        if (StringUtils.isBlank(msgType)) {
            return ReturnBean.error("验证码类型异常");
        }
//        String mCode = checkMsgCode(mobilePhone,msgType,id);
//        if(StringUtils.isNotBlank(mCode)){
//            return ReturnBean.ok();
//        }
        String msgCode = NumberUtils.randomSixCode();
        String returnMsg = GroupMAS.sendMsg(mobilePhone, "短信验证码为：" + msgCode + ",有效期为十分钟。");
        TMsgLog msgLog = new TMsgLog();
        msgLog.setPhone(mobilePhone);
        msgLog.setContent(msgCode);
        msgLog.setReturnMsg(returnMsg);
        msgLog.setType(msgType);
        msgLog.setCid(id);
        tMsgLogService.insert(msgLog);
        return ReturnBean.ok();
    }


    /**
     *  判断房屋类型
     * @param houseType
     * @return
     */
    private void checkHouseType(String houseType,boolean ifSend){
        String name = HouseTypeEnum.getName(houseType);
        if(ObjectUtil.isEmpty(name)){
            throw new KrtException(ReturnBean.error("房屋类型错误！"));
        }
        if(ifSend){
            request.setAttribute("houseType",houseType);
        }
    }


    public BigDecimal getStrNullToZero(String big) {
        if (ObjectUtil.isNotEmpty(big)) {
            try {
                return new BigDecimal(big);
            } catch (Exception e) {
                log.error("", e);
                return BigDecimal.ZERO;
            }
        }
        return BigDecimal.ZERO;
    }

    public static void main(String[] args) {

    }

}
