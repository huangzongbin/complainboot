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
 * ????????????????????????????????????
 *
 * @author ylf
 * @version 1.0
 * @date 2019???06???10???
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
     * ????????????????????????????????????
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
     * ?????????????????????????????????
     *
     * @param para ????????????
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
     * ??????????????????????????????
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
     * ???????????????????????????
     *
     * @param para ????????????
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
     * ???????????????
     *
     * @param id ?????????????????????id
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
     * ????????????
     *
     * @param rentIncomeDetails ?????????????????????
     * @return {@link ReturnBean}
     */
    @KrtLog("???????????????")
    @RequiresPermissions("rentIncomeDetails:rentIncomeDetails:payment")
    @PostMapping("rent/rentIncomeBase/payment")
    @ResponseBody
    public ReturnBean update(RentIncomeDetails rentIncomeDetails) {
        //??????????????????
        rentIncomeDetails.setStatus(1);
        rentIncomeDetails.setPaymentTiime(new Date());
        rentIncomeDetailsService.updateById(rentIncomeDetails);
        return ReturnBean.ok();
    }


    /**
     * ????????????????????????????????????
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
     * ??????????????????
     *
     * @return {@link ReturnBean}
     */
    @KrtLog("??????????????????")
    @RequiresPermissions("rentIncomeBase:rentIncomeBase:pay")
    @PostMapping("rent/rentIncomeBase/whetherPay")
    @ResponseBody
    public ReturnBean whetherPay(RentIncomeBase rentIncomeBase) {
        String mCode = rentIncomeBase.getMsgCode();

        boolean closeMessage = isCloseMessage();

        if (!closeMessage && StringUtils.isBlank(mCode)) {
            return ReturnBean.error("???????????????????????????");
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
            return ReturnBean.error("????????????????????????");
        }
    }

    /**
     * ???????????????
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
        //????????????
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
     * ????????????????????????
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
    @KrtLog("????????????????????????")
    @PostMapping("rent/rentIncomeBase/excelIn")
    @ResponseBody
    public ReturnBean excelIn(@RequestParam("file") MultipartFile file, @RequestParam("year") String year, @RequestParam("month") String month, @RequestParam("id") Integer id,
                              @RequestParam("msgType") String msgType, @RequestParam("msgCode") String msgCode, @RequestParam("mobilePhone") String mobilePhone, @RequestParam("houseType") String houseType) throws Exception {

        Map returnMap = null;
        try {
            Assert.isExcel(file);
            // ??????????????????
            checkHouseType(houseType,false);
            String mCode = checkMsgCode(mobilePhone, msgType, id);

            boolean closeMessage = isCloseMessage();

            if (!closeMessage) {
                if (StringUtils.isBlank(msgCode) || !mCode.equals(msgCode)) {
                    return ReturnBean.error("?????????????????????????????????????????????????????????");
                }
            }
            ImportParams params = new ImportParams();
            params.setTitleRows(0);
            params.setHeadRows(1);
            returnMap = new HashMap(5);
            List<RentIncomeDetails> list = ExcelImportUtil.importExcelBySax(file.getInputStream(), RentIncomeDetails.class, params);
            // ???????????????
            List<String> erroeIdcardList = checkIdCard(list);
            //???????????????????????????????????????????????????  ???????????????????????????????????????
            List<String> fileExistCards = null;
            if(HouseTypeEnum.ZZ.getValue().equals(houseType)){
                fileExistCards = removeExistExcelData(list);
            }else {
                // ?????????????????????????????????
                fileExistCards = removeExistIdcardAndAddr(list);
            }
            //????????????????????????????????????????????????
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
            return ReturnBean.error("????????????");
        }
        return ReturnBean.ok(returnMap);
    }

    private boolean isCloseMessage() {
        //??????????????????
        Dic dic = dicService.selectByTypeAndCode("closeMessage", "1");
        boolean closeMessage = false;

        if (null != dic && "1".equals(dic.getName())) {
            closeMessage = true;
        }
        return closeMessage;
    }

    /**
     * ????????? ?????? ??????????????????????????????
     */
    private List<String> removeExistExcelData(List<RentIncomeDetails> list) {
        List<String> msgs = new ArrayList<>();
        List<String> existIdCards = new ArrayList<>();
        List<String> allIdCards = new ArrayList<>();
        for (RentIncomeDetails details : list) {
            //???????????????????????????????????????????????????????????????
            if (allIdCards.contains(details.getIdCard())) {
                //?????????????????? ??????????????? ?????????????????????????????????????????????????????????
                if (!existIdCards.contains(details.getIdCard())) {
                    existIdCards.add(details.getIdCard());
                    //    msgs.add("???????????????????????????" + house.getIdCard() + " ??????????????????");
                    msgs.add(details.getIdCard());
                }
            } else {
                allIdCards.add(details.getIdCard());
            }
        }
        //?????????????????????????????????
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
     * ????????? ?????? ???????????????-???????????? ???????????????
     */
    private List<String> removeExistIdcardAndAddr(List<RentIncomeDetails> list){
        List<String> msgs = new ArrayList<>();
        List<String> existIdCards = new ArrayList<>();
        List<String> allIdCards = new ArrayList<>();
        for (RentIncomeDetails details : list) {
            if(ObjectUtil.isEmpty(details.getAddress())){
                continue;
            }
            // ???????????????????????????????????????
            String idcardAddr = details.getIdCard().trim() +"-"+ details.getAddress().trim();
            //???????????????????????????????????????????????????????????????
            if (allIdCards.contains(idcardAddr)) {
                //?????????????????? ??????????????? ?????????????????????????????????????????????????????????
                if (!existIdCards.contains(idcardAddr)) {
                    existIdCards.add(idcardAddr);
                    //    msgs.add("???????????????????????????" + house.getIdCard() + " ??????????????????");
                    msgs.add(idcardAddr);
                }
            } else {
                allIdCards.add(idcardAddr);
            }
        }
        //?????????????????????????????????
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
     *  ??????????????????????????????
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
            // ?????????????????????
            details.setAdvancePrice(details.getAdvanceProperty().add(details.getAdvanceRental()));
        }
        return erroeIdcardList;
    }

    @GetMapping("rent/rentIncomeDetails/excelOut")
    public ModelAndView individualCompanyDownload(@RequestParam Map para) throws IOException {
        Integer baseId = Integer.parseInt(para.get("baseId") + "");
        RentIncomeBase rentIncomeBase = rentIncomeBaseService.selectById(baseId);
        List list = rentIncomeDetailsService.selectExcelList(para);
        String dateStr = rentIncomeBase.getYear() + "???" + rentIncomeBase.getMonth() + "???";
        String outTitle = dateStr + "????????????????????????";
        ExportParams exportParams = new ExportParams(outTitle, dateStr);
        exportParams.setType(ExcelType.XSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, RentIncomeDetails.class, list);
        //??????workBook???????????????????????????
        if (ExcelType.HSSF.equals(exportParams.getType())) {
            outTitle += ".xls";
        } else {
            outTitle += ".xlsx";
        }
        //??????
        setIoResponseHeader(workbook, outTitle);
        return null;
    }

    /**
     * ???????????????io??????????????????
     */
    private void setIoResponseHeader(Workbook workbook, String title) throws IOException {
        response.reset(); //??????buffer??????
        // ??????????????????????????????????????????????????????????????????GBK??????????????????????????????????????????ISO-8859-1?????????????????????GBK????????? +".xlsx"
        // ???????????????GBK?????????ISO-8859-1???????????????????????????????????????????????????
        response.setHeader("Content-Disposition", "attachment;filename=" + new String((title).getBytes("UTF-8"), "ISO-8859-1"));
        //???????????????ContentType?????????????????????????????????????????????
//        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        //??????????????????????????????????????????????????????????????????
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
            // ?????? flush ????????? ??????????????????????????????????????????
            bufferedOutPut.flush();
            // ????????? ??? Excel ??????????????????????????????????????????????????????
            workbook.write(bufferedOutPut);
        } catch (Exception e) {
            throw e;
        } finally {
            if (bufferedOutPut != null) {
                // ?????????????????????
                bufferedOutPut.close();
            }
            if (output != null) {
                output.close();
            }
        }
    }

    /**
     * ?????????????????????
     *
     * @return {@link ReturnBean}
     */
    @KrtLog("?????????????????????")
    @PostMapping("rent/rentIncomeBase/sendMsgCode")
    @ResponseBody
    public ReturnBean sendMsgCode(@RequestParam("id") Integer id, @RequestParam("msgType") String msgType, @RequestParam("mobilePhone") String mobilePhone) {
        if (StringUtils.isBlank(mobilePhone)) {
            return ReturnBean.error("????????????????????????????????????");
        }
        if (StringUtils.isBlank(msgType)) {
            return ReturnBean.error("?????????????????????");
        }
//        String mCode = checkMsgCode(mobilePhone,msgType,id);
//        if(StringUtils.isNotBlank(mCode)){
//            return ReturnBean.ok();
//        }
        String msgCode = NumberUtils.randomSixCode();
        String returnMsg = GroupMAS.sendMsg(mobilePhone, "?????????????????????" + msgCode + ",????????????????????????");
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
     *  ??????????????????
     * @param houseType
     * @return
     */
    private void checkHouseType(String houseType,boolean ifSend){
        String name = HouseTypeEnum.getName(houseType);
        if(ObjectUtil.isEmpty(name)){
            throw new KrtException(ReturnBean.error("?????????????????????"));
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
