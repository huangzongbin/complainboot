package com.krt.file.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.krt.common.base.BaseController;
import com.krt.common.bean.ReturnBean;
import com.krt.common.constant.GlobalConstant;
import com.krt.common.exception.KrtException;
import com.krt.common.util.DateUtils;
import com.krt.common.util.IdUtils;
import com.krt.common.util.ServletUtils;
import com.krt.common.util.UploadUtils;
import com.krt.file.entity.FileResult;
import com.krt.file.service.IFileResultService;
import com.krt.file.util.FileMd5Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * KindEditor控制层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年05月16日
 */
@Slf4j
@Controller
@ConditionalOnProperty(name = "web.upload-type", havingValue = "location")
public class LocationUploadController extends BaseController {

    @Value("${web.update-path}")
    private String updatePath;

    @Autowired
    private IFileResultService fileResultService;

    /**
     * 上传文件 （用于普通的上传插件）
     *
     * @throws Exception
     */
    @PostMapping("upload/fileUpload")
    public void fildUpload(@RequestParam("file") MultipartFile file, String dir) throws Exception {
        if (dir == null || !UploadUtils.extMap.containsKey(dir.toLowerCase())) {
            ReturnBean rb = ReturnBean.error("类型不合法");
            ServletUtils.printText(response, JSONObject.toJSONString(rb));
            return;
        }
        if (file == null || file.isEmpty()) {
            ReturnBean rb = ReturnBean.error("文件不能为空");
            ServletUtils.printText(response, rb);
            return;
        }
        if (!Arrays.asList(UploadUtils.extMap.get(dir).split(GlobalConstant.COMMA)).contains(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(GlobalConstant.POINT) + 1))) {
            ReturnBean rb = ReturnBean.error("上传文件扩展名只允许" + UploadUtils.extMap.get(dir) + "格式");
            ServletUtils.printText(response, JSONObject.toJSONString(rb));
            return;
        }
        //文件md5
        String md5 = FileMd5Utils.getMd5File(file.getInputStream());
        FileResult fileResult = fileResultService.selectByMd5(md5);
        if (fileResult != null) {
            ReturnBean rb = ReturnBean.ok(fileResult.getUrl());
            ServletUtils.printText(response, JSON.toJSONString(rb));
            return;
        }
        // 计算出文件后缀名
        String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
        //本地上传文件 放到对应的项目 对应的文件夹下
        log.debug("path:{}", updatePath);
        String savePath = updatePath.replace("/", System.getProperty("file.separator"))
                + dir + System.getProperty("file.separator")
                + DateUtils.dateToString("yyyyMMdd", new Date()) + System.getProperty("file.separator");
        File f = new File(savePath);
        if (!f.exists()) {
            f.mkdirs();
        }
        String fileName = IdUtils.getUUID();
        String saveFilePath = savePath + fileName + GlobalConstant.POINT + fileExt;
        log.debug("saveFilePath:{}", saveFilePath);
        file.transferTo(new File(saveFilePath));
        String fileNameUrl = fileName + GlobalConstant.POINT + fileExt;
        String fileUrl = request.getContextPath() + "/file/" + dir + "/" + DateUtils.dateToString("yyyyMMdd", new Date()) + "/" + fileNameUrl;
        //返回结果
        ReturnBean rb = ReturnBean.ok(fileUrl);
        //异步保存结果
        fileResult = new FileResult();
        fileResult.setMd5(md5);
        fileResult.setName(file.getOriginalFilename());
        fileResult.setFileLenght(file.getSize());
        fileResult.setUrl(fileUrl);
        fileResult.setSavePath(saveFilePath);
        fileResultService.insertAsync(fileResult);
        ServletUtils.printText(response, JSONObject.toJSONString(rb));
    }

    /**
     * 上传文件 （用户普通的上传插件）
     *
     * @param file 文件
     * @param dir  文件类型
     */
    @PostMapping("kindeditor/fileUpload")
    public void fileUpload(@RequestParam("imgFile") MultipartFile file, String dir) {
        try {
            Map<String, Object> map = new HashMap<>(2);
            if (dir == null || !UploadUtils.extMap.containsKey(dir.toLowerCase())) {
                map = getError("文件类型不合法");
                ServletUtils.printText(response, JSON.toJSONString(map));
                return;
            }
            if (file == null || file.isEmpty()) {
                map = getError("文件不能为空");
                ServletUtils.printText(response, JSON.toJSONString(map));
                return;
            }
            if (!Arrays.asList(UploadUtils.extMap.get(dir).split(GlobalConstant.COMMA)).contains(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1))) {
                map = getError("上传文件扩展名只允许" + UploadUtils.extMap.get(dir) + "格式");
                ServletUtils.printText(response, JSON.toJSONString(map));
                return;
            }
            //文件md5
            String md5 = FileMd5Utils.getMd5File(file.getInputStream());
            FileResult fileResult = fileResultService.selectByMd5(md5);
            if (fileResult != null) {
                Map<String, Object> msg = new HashMap<>(2);
                msg.put("error", 0);
                msg.put("url", fileResult.getUrl());
                ServletUtils.printText(response, JSON.toJSONString(msg));
                return;
            }
            // 计算出文件后缀名
            String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
            //本地上传文件 放到对应的项目 对应的文件夹下
            log.debug("path:{}", updatePath);
            String savePath = updatePath.replace("/", System.getProperty("file.separator")) + dir + System.getProperty("file.separator");
            File f = new File(savePath);
            if (!f.exists()) {
                f.mkdirs();
            }
            SimpleDateFormat fileNameFormat = new SimpleDateFormat("yyyyMMddkkmmss_S");
            String fileName = fileNameFormat.format(new Date());
            String saveFilePath = savePath + fileName + GlobalConstant.POINT + fileExt;
            log.debug("saveFilePath:{}", saveFilePath);
            copy(file.getInputStream(), saveFilePath);
            String fileNameUrl = fileName + GlobalConstant.POINT + fileExt;
            String fileUrl = request.getContextPath() + "/file/" + dir + "/" + fileNameUrl;
            map.put("error", 0);
            map.put("url", fileUrl);
            //异步保存文件结果
            fileResult = new FileResult();
            fileResult.setMd5(md5);
            fileResult.setName(file.getOriginalFilename());
            fileResult.setFileLenght(file.getSize());
            fileResult.setUrl(fileUrl);
            fileResult.setSavePath(saveFilePath);
            fileResultService.insertAsync(fileResult);
            //返回结果
            ServletUtils.printText(response, JSON.toJSONString(map));
        } catch (Exception e) {
            e.printStackTrace();
            throw new KrtException("文件上传异常", e);
        }
    }

    /**
     * 文件管理
     */
    @RequestMapping("kindeditor/fileManager")
    public void fileManager(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            // 根目录路径，可以指定绝对路径，比如 /var/www/upload_files/
            String rootPath = updatePath.replace("/", System.getProperty("file.separator"));
            log.debug("rootPath:{}", rootPath);
            // 根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/upload_files/
            String rootUrl = request.getContextPath() + "/file/";
            log.debug("rootUrl:{}", rootUrl);
            // 图片扩展名
            String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};
            String dirName = request.getParameter("dir");
            if (dirName != null) {
                if (!Arrays.<String>asList(new String[]{"image", "flash", "media", "file"}).contains(dirName)) {
                    response.getWriter().print("Invalid Directory name.");
                }
                rootPath += dirName + System.getProperty("file.separator");
                rootUrl += dirName + "/";
                File saveDirFile = new File(rootPath);
                if (!saveDirFile.exists()) {
                    saveDirFile.mkdirs();
                }
            }
            // 根据path参数，设置各路径和URL
            String path = request.getParameter("path") != null ? request.getParameter("path") : "";
            String currentPath = rootPath + path;
            String currentUrl = rootUrl + path;
            String moveDirPath = "";
            if (!"".equals(path)) {
                String str = path.substring(0, path.length() - 1);
                moveDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
            }
            // 排序形式，name or size or type
            String order = request.getParameter("order") != null ? request.getParameter("order").toLowerCase() : "name";
            // 不允许使用..移动到上一级目录
            if (path.contains("..")) {
                response.getWriter().print("Access is not allowed.");
            }
            // 最后一个字符不是/
            if (!"".equals(path) && !path.endsWith("/")) {
                response.getWriter().print("Parameter is not valid.");
            }
            // 目录不存在或不是目录
            File currentPathFile = new File(currentPath);
            if (!currentPathFile.isDirectory()) {
                response.getWriter().print("Directory does not exist.");
            }
            // 遍历目录取的文件信息
            List<Hashtable> fileList = new ArrayList<Hashtable>();
            if (currentPathFile.listFiles() != null) {
                for (File file : Objects.requireNonNull(currentPathFile.listFiles())) {
                    Hashtable<String, Object> hash = new Hashtable<String, Object>();
                    String fileName = file.getName();
                    if (file.isDirectory()) {
                        hash.put("is_dir", true);
                        hash.put("has_file", (file.listFiles() != null));
                        hash.put("filesize", 0L);
                        hash.put("is_photo", false);
                        hash.put("filetype", "");
                    } else if (file.isFile()) {
                        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                        hash.put("is_dir", false);
                        hash.put("has_file", false);
                        hash.put("filesize", file.length());
                        hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
                        hash.put("filetype", fileExt);
                    }
                    hash.put("filename", fileName);
                    hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
                    fileList.add(hash);
                }
            }

            if ("size".equals(order)) {
                fileList.sort(new SizeComparator());
            } else if ("type".equals(order)) {
                fileList.sort(new TypeComparator());
            } else {
                fileList.sort(new NameComparator());
            }
            JSONObject result = new JSONObject();
            result.put("moveup_dir_path", moveDirPath);
            result.put("current_dir_path", path);
            result.put("current_url", currentUrl);
            result.put("total_count", fileList.size());
            result.put("file_list", fileList);
            log.debug(result.toString());
            response.getWriter().print(result.toString());
        } catch (Exception e) {
            log.error("程序异常", e);
        }
    }

    class NameComparator implements Comparator {
        @Override
        public int compare(Object a, Object b) {
            Hashtable hashA = (Hashtable) a;
            Hashtable hashB = (Hashtable) b;
            if (((Boolean) hashA.get("is_dir"))
                    && !((Boolean) hashB.get("is_dir"))) {
                return -1;
            } else if (!((Boolean) hashA.get("is_dir"))
                    && ((Boolean) hashB.get("is_dir"))) {
                return 1;
            } else {
                return ((String) hashA.get("filename"))
                        .compareTo((String) hashB.get("filename"));
            }
        }
    }

    class SizeComparator implements Comparator {
        @Override
        public int compare(Object a, Object b) {
            Hashtable hashA = (Hashtable) a;
            Hashtable hashB = (Hashtable) b;
            if (((Boolean) hashA.get("is_dir"))
                    && !((Boolean) hashB.get("is_dir"))) {
                return -1;
            } else if (!((Boolean) hashA.get("is_dir"))
                    && ((Boolean) hashB.get("is_dir"))) {
                return 1;
            } else {
                if (((Long) hashA.get("filesize")) > ((Long) hashB
                        .get("filesize"))) {
                    return 1;
                } else if (((Long) hashA.get("filesize")) < ((Long) hashB
                        .get("filesize"))) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
    }

    class TypeComparator implements Comparator {
        @Override
        public int compare(Object a, Object b) {
            Hashtable hashA = (Hashtable) a;
            Hashtable hashB = (Hashtable) b;
            if (((Boolean) hashA.get("is_dir"))
                    && !((Boolean) hashB.get("is_dir"))) {
                return -1;
            } else if (!((Boolean) hashA.get("is_dir"))
                    && ((Boolean) hashB.get("is_dir"))) {
                return 1;
            } else {
                return ((String) hashA.get("filetype"))
                        .compareTo((String) hashB.get("filetype"));
            }
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
        src.close();
        tarOs.close();

    }

    /**
     * 返回错误信息
     */
    private Map<String, Object> getError(String message) {
        Map<String, Object> msg = new HashMap<>(2);
        msg.put("error", 1);
        msg.put("message", message);
        return msg;
    }

}
