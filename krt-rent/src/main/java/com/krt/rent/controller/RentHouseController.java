package com.krt.rent.controller;

//import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.entity.vo.MapExcelConstants;
import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.krt.common.annotation.KrtLog;
import com.krt.common.base.BaseController;
import com.krt.common.bean.DataTable;
import com.krt.common.bean.ReturnBean;
import com.krt.common.constant.GlobalConstant;
import com.krt.common.exception.KrtException;
import com.krt.common.util.ServletUtils;
import com.krt.common.util.StringUtils;
import com.krt.rent.entity.RentHouse;
import com.krt.rent.enums.HouseTypeEnum;
import com.krt.rent.service.IRentHouseService;
import com.krt.rent.utils.ExcelImportUtil;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.text.DecimalFormat;
import java.util.*;

/**
 * 住户管理控制层
 *
 * @author 陈仁豪
 * @version 1.0
 * @date 2019年06月09日
 */
@Controller
@Slf4j
public class RentHouseController extends BaseController {

    @Autowired
    private IRentHouseService rentHouseService;

    /**
     * 住户管理管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("rent:house:list")
    @GetMapping("rent/rentHouse/list")
    public String list(String houseType) {
        checkHouseType(houseType,true);
        return "rent/rentHouse/list";
    }

    /**
     * 住户管理管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("rent:house:list")
    @PostMapping("rent/rentHouse/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage page = rentHouseService.selectPageList(para);
        return DataTable.ok(page);
    }

    /**
     * 新增住户管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("rent:house:insert")
    @GetMapping("rent/rentHouse/insert")
    public String insert(String houseType) {
        checkHouseType(houseType,true);
        return "rent/rentHouse/insert";
    }

    /**
     * 添加住户管理
     *
     * @param rentHouse 住户管理
     * @return {@link ReturnBean}
     */
    @KrtLog("添加住户管理")
    @RequiresPermissions("rent:house:insert")
    @PostMapping("rent/rentHouse/insert")
    @ResponseBody
    public ReturnBean insert(RentHouse rentHouse) {
        if(!IdcardUtil.isValidCard(rentHouse.getIdCard())){
            return ReturnBean.error("身份证格式不正确！");
        }
        // 判断是否是符合的房源类型
        String houseType = HouseTypeEnum.getName(rentHouse.getHouseType());
        if(ObjectUtil.isEmpty(houseType)){
            return ReturnBean.error("房源类型不符合");
        }

        Map map = rentHouseService.judgeIdCardExist(rentHouse.getIdCard(),rentHouse.getHouseType(),rentHouse.getAddress());
        if(map!=null){
            Integer number = Integer.parseInt(map.get("number")+"");
            if(number>0){
                return ReturnBean.error("该身份证号已绑定小区"+map.get("houseName"));
            }
        }
        try {
            rentHouseService.insertRent(rentHouse);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnBean.error("添加用户失败！");
        }
        return ReturnBean.ok();
    }

    /**
     * 修改住户管理页
     *
     * @param id 住户管理id
     * @return {@link String}
     */
    @RequiresPermissions("rent:house:update")
    @GetMapping("rent/rentHouse/update")
    public String update(Integer id) {
        RentHouse rentHouse = rentHouseService.selectById(id);
        request.setAttribute("rentHouse", rentHouse);
        return "rent/rentHouse/update";
    }

    @GetMapping("rent/rentHouse/see")
    public String seesee(Integer id) {
        RentHouse rentHouse = rentHouseService.selectById(id);
        request.setAttribute("rentHouse", rentHouse);
        return "rent/rentHouse/see";
    }

    /**
     * 修改住户管理
     *
     * @param rentHouse 住户管理
     * @return {@link ReturnBean}
     */
    @KrtLog("修改住户管理")
    @RequiresPermissions("rent:house:update")
    @PostMapping("rent/rentHouse/update")
    @ResponseBody
    public ReturnBean update(RentHouse rentHouse) {
        if(!IdcardUtil.isValidCard(rentHouse.getIdCard())){
            return ReturnBean.error("身份证格式不正确！");
        }
        RentHouse oldhouse = rentHouseService.selectById(rentHouse.getId());
        //如果idCard和以前的不一样则查新card绑定情况
        if(!rentHouse.getIdCard().equals(oldhouse.getIdCard())){
            Map map = rentHouseService.judgeIdCardExist(rentHouse.getIdCard(),oldhouse.getHouseType(),rentHouse.getAddress());
            if(map!=null){
                Integer number = Integer.parseInt(map.get("number")+"");
                if(number>0){
                    return ReturnBean.error("该身份证号已绑定小区"+map.get("houseName"));
                }
            }
        }
        rentHouseService.updateRentById(rentHouse);
        return ReturnBean.ok();
    }

    /**
     * 删除住户管理
     *
     * @param id 住户管理id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除住户管理")
    @RequiresPermissions("rent:house:delete")
    @PostMapping("rent/rentHouse/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        rentHouseService.deleteRentById(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除住户管理
     *
     * @param ids 住户管理ids
     * @return {@link ReturnBean}
     */
    @KrtLog("批量删除住户管理")
    @RequiresPermissions("rent:house:delete")
    @PostMapping("rent/rentHouse/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        rentHouseService.deleteRentByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }


    @KrtLog("住户导入")
    @PostMapping("rent/rentHouse/excelIn")
    @ResponseBody
    public ReturnBean excelIn(@RequestParam("file") MultipartFile file,String houseType)  {
        Map returnMap = new HashMap();
        try{
            // 检验房屋类型
            checkHouseType(houseType,false);
            long time1 = System.currentTimeMillis();
            List<RentHouse> list = ExcelImportUtil.importExcelBySax(file.getInputStream(), RentHouse.class, new ImportParams());
            long time2 = System.currentTimeMillis();
            //
            List<String> erroeIdcardList = checkIdCard(list);
            long time3 = System.currentTimeMillis();

            //身份证号重复判断，重复的剔出做提示  返回：文件中重复的身份证号
            List<String> fileExistCards = null;
            if(HouseTypeEnum.ZZ.getValue().equals(houseType)){
                fileExistCards = removeExistExcelData(list,houseType);
            }else {
                fileExistCards = removeExistIdcardAndAddr(list,houseType);
            }
            long time4 = System.currentTimeMillis();
            List<String> nullCards = new ArrayList<>();
            if(list.size()>0) {
                //查询数据库中的已存在身份证号数据
//
                rentHouseService.insertRentBatch(list, 2000,houseType);
                long time5 = System.currentTimeMillis();
                log.info("time5-time4:{},time4-time3:{},time3-time2:{},ttime2-time1:{}",time5-time4,time4-time3,time3-time2,time2-time1);
                returnMap.put("number",1);
            }else{
                returnMap.put("number",0);
            }
            returnMap.put("fileExistCards",fileExistCards);
            returnMap.put("erroeIdcardList",erroeIdcardList);
            returnMap.put("nullCards",nullCards);
        }catch (KrtException e2){
            log.error("导入房屋类型错误{}",e2.getMessage());
            return ReturnBean.error(e2.getMessage());
        } catch (Exception e){
            log.error("错误信息{}",e);
            return ReturnBean.error("数据导入异常");
        }
        return ReturnBean.ok(returnMap);
    }

    /**
     * 剔除掉 集合 中身份证号相同的数据
     */
    private List<String> removeExistExcelData(List<RentHouse> list,String houseType){
        List<String> msgs = new ArrayList<>();
        List<String> existIdCards = new ArrayList<>();
        List<String> allIdCards = new ArrayList<>();
        for (RentHouse house : list) {
            if(house.getIdCard()==null){
                continue;
            }
            //如果身份证号已经刷过这个了，则说明是重复的
            if (allIdCards.contains(house.getIdCard())) {
                //如果还没加入 重复集合中 则加入（防止出现多个重复身份证号问题）
                if (!existIdCards.contains(house.getIdCard())) {
                    existIdCards.add(house.getIdCard());
//                    msgs.add("文件中身份证号为：" + house.getIdCard() + " 存在多个数据");
                    msgs.add(house.getIdCard());
                }
            } else {
                allIdCards.add(house.getIdCard());
            }
        }
        //删除掉重复身份证号数据
        Iterator it = list.iterator();
        while(it.hasNext()){
            RentHouse rentHouse = (RentHouse) it.next();
            rentHouse.setHouseType(houseType);
            if(ObjectUtil.isEmpty(rentHouse.getIdCard()) || existIdCards.contains(rentHouse.getIdCard())){
                it.remove();
            }
        }
        return msgs;
    }

    /**
     * 剔除掉 集合 中身份证号+地址相同的数据
     */
    private List<String> removeExistIdcardAndAddr(List<RentHouse> list,String houseType){
        List<String> msgs = new ArrayList<>();
        List<String> existIdCardsAddr = new ArrayList<>();
        List<String> allIdCardsAddr = new ArrayList<>();
        for (RentHouse house : list) {
            if(ObjectUtil.isEmpty(house.getIdCard()) || ObjectUtil.isEmpty(house.getAddress())){
                continue;
            }
            // 将身份证和地址进行联合去重
            String idcardAddr = house.getIdCard().trim() +"-"+ house.getAddress().trim();
            //如果身份证号+地址已经刷过这个了，则说明是重复的
            if (allIdCardsAddr.contains(idcardAddr)) {
                //如果还没加入 重复集合中 则加入（防止出现多个身份证号+地址 重复问题）
                if (!existIdCardsAddr.contains(idcardAddr)) {
                    existIdCardsAddr.add(idcardAddr);
//                    msgs.add("文件中身份证号为：" + house.getIdCard() + " 存在多个数据");
                    msgs.add(idcardAddr);
                }
            } else {
                allIdCardsAddr.add(idcardAddr);
            }
        }
        //删除掉重复身份证号数据
        Iterator it = list.iterator();
        while(it.hasNext()){
            RentHouse rentHouse = (RentHouse) it.next();
            rentHouse.setHouseType(houseType);
            if(ObjectUtil.isEmpty(rentHouse.getIdCard()) ||  ObjectUtil.isEmpty(rentHouse.getAddress()) || existIdCardsAddr.contains(rentHouse.getIdCard().trim() +"-"+ rentHouse.getAddress().trim())){
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
    private List<String> checkIdCard(List<RentHouse> list){
        ArrayList<String> erroeIdcardList = new ArrayList<>();
        Iterator<RentHouse> iterator = list.iterator();
        while (iterator.hasNext()){
            RentHouse rentHouse = iterator.next();
            if(!IdcardUtil.isValidCard(rentHouse.getIdCard())){
                erroeIdcardList.add(rentHouse.getIdCard());
                iterator.remove();
            }
        }
        return erroeIdcardList;
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


    @KrtLog("检验身份证")
    @PostMapping("rent/rentHouse/checkIdcard")
    public String checkIdcard(ModelMap modelMap, @RequestParam("file") MultipartFile file)  {
        ArrayList<Map> erroeIdcardList = new ArrayList<>();
        try{
            List<RentHouse> list = ExcelImportUtil.importExcelBySax(file.getInputStream(), RentHouse.class, new ImportParams());
            Iterator<RentHouse> iterator = list.iterator();
            while (iterator.hasNext()){
                RentHouse rentHouse = iterator.next();
                if(!IdcardUtil.isValidCard(rentHouse.getIdCard())){
                    HashMap<String, Object> para = new HashMap<>();
                    para.put("idcard",rentHouse.getIdCard());
                    para.put("name",rentHouse.getName());
                    para.put("addr",rentHouse.getAddress());
                    erroeIdcardList.add(para);
                }
            }

        } catch (Exception e){
            log.error("错误信息{}",e);
            ServletUtils.printText(response, JSON.toJSONString(ReturnBean.error("导入数据错误")));
            return "";
        }
            List<ExcelExportEntity> entityList = new ArrayList<>();
            entityList.add(new ExcelExportEntity("身份证", "idcard", 15));
            entityList.add(new ExcelExportEntity("姓名", "name", 15));
            entityList.add(new ExcelExportEntity("地址", "addr", 20));
            modelMap.put(MapExcelConstants.ENTITY_LIST, entityList);
            modelMap.put(MapExcelConstants.MAP_LIST, erroeIdcardList);
            modelMap.put(MapExcelConstants.FILE_NAME, "错误身份证");
            modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("错误身份证", "错误身份证", ExcelType.XSSF));
            return MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW;
    }

}
