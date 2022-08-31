package com.krt.api.controller;

import com.alibaba.fastjson.JSON;
import com.krt.common.annotation.KrtIgnoreAuth;
import com.krt.common.base.BaseController;
import com.krt.common.util.ImageUtils2;
import com.krt.common.util.ServletUtils;
import com.krt.file.entity.FileResult;
import com.krt.file.service.IFileResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author 殷帅
 * @version 1.0
 * @Description: KindEditor
 * @date 2017年05月16日
 */
@RestController
@RequestMapping("api/upload")
@Api(tags = "上传API")
@Slf4j
public class ApiUploadController extends BaseController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    @Value("${web.update-path}")
    private String updatePath;

    @Value("${spring.profiles.active}")
    private String profiles;


    @Resource
    private IFileResultService fileResultService;

    /**
     * 定义允许上传的文件扩展名
     */
    private ConcurrentHashMap<String, String> extMap = new ConcurrentHashMap<String, String>();

    /**
     * 初始化文件后缀配置
     */
    public void init() {
        extMap.put("image", "gif,jpg,jpeg,png,bmp,webp");
        extMap.put("flash", "swf,flv");
        extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb,webp");
        extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2,webp");
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    /**
     * 上传文件 (返回格式属于ReturnBean)
     *
     * @param file 文件
     */
    @KrtIgnoreAuth
    @PostMapping(value = "fileUpload")
    @ApiOperation(value = "上传文件", httpMethod = "POST")
    public Map<String, Object> fileUpload(@ApiParam(value = "上传的文件" ,required = true) MultipartFile file) {
        String topDir = "/goodsImage";
        String dir = "image";
        try {
            init();
            if (dir == null || !extMap.containsKey(dir.toLowerCase())) {
                return getError("文件类型不合法");
            }
            if (file == null || file.isEmpty()) {
                return getError("文件不能为空");
            }
            if (dir != null) {
                if (!Arrays.<String>asList(extMap.get(dir).split(",")).contains(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1))) {
                    return getError("上传文件扩展名只允许" + extMap.get(dir) + "格式");
                }
            }
            String md5 = getMd5File(file.getInputStream());
            FileResult fileResult = fileResultService.selectByMd5(md5);
            if (fileResult != null) {
                Map<String, Object> msg = new HashMap<>(2);
                msg.put("code", 0);
                msg.put("url", fileResult.getUrl());
               return  msg;
            }
            // 计算出文件后缀名
            String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
            String savePath = "";
            /*if(!"dev".equals(profiles)){
                savePath = request.getSession().getServletContext().getRealPath("/") + "upload/";
            }else{*/
                savePath = updatePath+request.getContextPath() + "/upload/";
            /*}*/
            //本地上传文件 放到对应的项目 对应的文件夹下
            logger.debug("path:{}", savePath);
            savePath = savePath.replace("/", System.getProperty("file.separator")) + dir + System.getProperty("file.separator");
            File f = new File(savePath);
            if (!f.exists()) {
                f.mkdirs();
            }
            SimpleDateFormat fileNameFormat = new SimpleDateFormat("yyyyMMddkkmmss_S");
            String fileName = fileNameFormat.format(new Date());
            String saveFilePath = savePath + fileName + "." + fileExt;
            logger.debug("saveFilePath:{}", saveFilePath);

            copy(file.getInputStream(), saveFilePath);
            ImageUtils2.commpressPicCycle(saveFilePath,500L,0.8);
            String fileNameUrl = fileName + "." + fileExt;
            String fileUrl =   topDir+request.getContextPath() + "/upload/" + dir + "/" + fileNameUrl;
            Map<String, Object> msg = new HashMap<String, Object>(2);
            msg.put("code", 0);
            msg.put("url", fileUrl);

            //异步保存文件结果
            fileResult = new FileResult();
            fileResult.setMd5(md5);
            fileResult.setName(file.getOriginalFilename());
            fileResult.setFileLenght(file.getSize());
            fileResult.setUrl(fileUrl);
            fileResult.setSavePath(saveFilePath);
            fileResultService.insertAsync(fileResult);
            return msg;
        } catch (Exception e) {
            e.printStackTrace();
            return getError("程序异常");
        }
    }


    /**
     * @param src 源文件
     * @param tar 目标路径
     * @category 拷贝文件
     */

    public void copy(InputStream src, String tar) throws Exception {
        // 判断源文件或目标路径是否为空
        if (null == src || null == tar || tar.isEmpty()) {
            return;
        }
        OutputStream tarOs = null;
        File tarFile = new File(tar);
        tarOs = new FileOutputStream(tarFile);
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = src.read(buffer))) {
            tarOs.write(buffer, 0, n);
        }
        if (null != src) {
            src.close();
        }
        if (null != tarOs) {
            tarOs.close();
        }

    }

    /**
     * 返回错误信息
     *
     * @param message
     * @return
     */
    public Map<String, Object> getError(String message) {
        Map<String, Object> msg = new HashMap<String, Object>(2);
        msg.put("error", 1);
        msg.put("message", message);
        return msg;
    }


    /**
     * 推荐此方法获取文件MD5
     *
     * @param inputStream 文件
     * @return 文件Md5
     */
    public  String getMd5File(InputStream inputStream) {
        String md5 = null;
        try {
            md5 = DigestUtils.md5Hex(IOUtils.toByteArray(inputStream));
        } catch (IOException e) {
            log.error("计算文件md5异常", e);
        }
        return md5;
    }
}
