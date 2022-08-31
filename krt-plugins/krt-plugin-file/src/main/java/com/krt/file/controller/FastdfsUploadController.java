package com.krt.file.controller;

import com.alibaba.fastjson.JSON;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.DefaultGenerateStorageClient;
import com.krt.common.base.BaseController;
import com.krt.common.bean.ReturnBean;
import com.krt.common.constant.GlobalConstant;
import com.krt.common.util.ServletUtils;
import com.krt.common.util.UploadUtils;
import com.krt.file.entity.FileResult;
import com.krt.file.fastdfs.contant.FastdfsConstant;
import com.krt.file.fastdfs.enums.UploadTypeEnum;
import com.krt.file.service.IFileResultService;
import com.krt.file.util.FileMd5Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * fastdfs上传
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年09月10日
 */
@Slf4j
@Controller
@ConditionalOnProperty(name = "web.upload-type", havingValue = "fastdfs")
public class FastdfsUploadController extends BaseController {

    @Autowired
    private DefaultGenerateStorageClient defaultGenerateStorageClient;

//    @Autowired
//    private AppendFileStorageClient appendFileStorageClient;

 //   @Autowired
//    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private IFileResultService fileResultService;

    @Value("${web.file-prefix}")
    private String filePrefix;

    private String md5;

    /**
     * 上传文件 fastdfs (返回格式属于kindeditor)
     *
     * @param file 文件
     * @param dir  文件类型
     */
    @PostMapping("kindeditor/fileUpload")
    public void fileUploadForEditor(@RequestParam("imgFile") MultipartFile file, String dir) {
        Map<String, Object> map = new HashMap<>(2);
        try {
            // 验证
            boolean flag = validate(dir, UploadTypeEnum.KINDEDITOR.getValue(), file);
            if (!flag) {
                return;
            }
            // 计算出文件后缀名
            String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
            StorePath storePath = defaultGenerateStorageClient.uploadFile(FastdfsConstant.DEFAULT_GROUP, file.getInputStream(), file.getSize(), fileExt);
            String fileUrl = filePrefix + storePath.getFullPath();
            //上传完成保存记录
            FileResult fileResult = new FileResult();
            fileResult.setMd5(md5);
            fileResult.setName(file.getOriginalFilename());
            fileResult.setFileLenght(file.getSize());
            fileResult.setUrl(fileUrl);
            fileResultService.insertAsync(fileResult);
            //返回结果
            map.put("error", 0);
            map.put("url", fileUrl);
            ServletUtils.printText(response, JSON.toJSONString(map));
        } catch (Exception e) {
            log.error("程序异常", e);
            map.put("error", 1);
            map.put("url", "程序异常");
            ServletUtils.printText(response, JSON.toJSONString(map));
        }
    }

    /**
     * 上传文件 fastdfs (返回格式属于ReturnBean)
     *
     * @param file 文件
     * @param dir  文件类型
     */
    @PostMapping("upload/fileUpload")
    public void fileUpload(@RequestParam("file") MultipartFile file, String dir) {
        try {
            // 验证
            boolean flag = validate(dir, UploadTypeEnum.DEFAULT.getValue(), file);
            if (!flag) {
                return;
            }
            //文件md5
            md5 = FileMd5Utils.getMd5File(file.getInputStream());
            // 计算出文件后缀名
            String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
            StorePath storePath = defaultGenerateStorageClient.uploadFile(FastdfsConstant.DEFAULT_GROUP, file.getInputStream(), file.getSize(), fileExt);
            String fileUrl = filePrefix + storePath.getFullPath();
            //上传完成保存记录
            FileResult fileResult = new FileResult();
            fileResult.setMd5(md5);
            fileResult.setName(file.getOriginalFilename());
            fileResult.setFileLenght(file.getSize());
            fileResult.setUrl(fileUrl);
            fileResultService.insertAsync(fileResult);
            //返回结果
            ReturnBean rb = ReturnBean.ok(fileUrl);
            ServletUtils.printText(response, JSON.toJSONString(rb));
        } catch (Exception e) {
            log.error("程序异常", e);
            ReturnBean rb = ReturnBean.error("程序异常");
            ServletUtils.printText(response, JSON.toJSONString(rb));
        }
    }

//    /**
//     * 切片上传 (需要redis)
//     *
//     * @param paramMap 参数
//     * @throws IOException 异常
//     */
//    @PostMapping("upload/chunkUpload")
//    public void chunkUpload(@RequestParam Map<String, Object> paramMap, @RequestParam("file") MultipartFile file) throws IOException {
//        //类型判断
//        String dir = String.valueOf(paramMap.get("dir"));
//        boolean flag = validate(dir, UploadTypeEnum.CHUNK.getValue(), file);
//        if (!flag) {
//            return;
//        }
//        //文件名
//        String name = paramMap.get("name").toString();
//        //文件后缀
//        String fileExt = FilenameUtils.getExtension(name);
//        if (!Arrays.asList(UploadUtils.extMap.get(dir).split(GlobalConstant.COMMA)).contains(fileExt)) {
//            ReturnBean rb = ReturnBean.error("上传文件扩展名只允许" + UploadUtils.extMap.get(dir) + "格式");
//            ServletUtils.printText(response, JSON.toJSONString(rb));
//            return;
//        }
//        //文件md5
//        String fileMd5 = (String) paramMap.get("uuid");
//        //添加上传锁(防止并发上传)
//        String chunklockName = FastdfsConstant.CHUNKLOCK + fileMd5;
//        Long lock = redisTemplate.opsForValue().increment(chunklockName, 1);
//        if (lock != null && lock > 1) {
//            ReturnBean rb = ReturnBean.error("请求块锁失败");
//            ServletUtils.printText(response, JSON.toJSONString(rb));
//        }
//        //文件存储路径
//        StorePath storePath = null;
//        try {
//            //当前块编号
//            Integer chunk = Integer.valueOf(paramMap.get("chunk").toString());
//            //总块数
//            Integer chunks = Integer.valueOf(paramMap.get("chunks").toString());
//            //redis中记录当前应该传第几块(从0开始)
//            String chunkCurrkey = FastdfsConstant.CHUNKCURR + fileMd5;
//            String chunkCurr = redisTemplate.opsForValue().get(chunkCurrkey);
//            //缓存中的文件块 默认是0
//            Integer cacheChunk = Integer.valueOf(chunkCurr == null ? "0" : chunkCurr);
//            //缓存文件块的编号比上传的大
//            if (chunk < cacheChunk) {
//                ReturnBean rb = ReturnBean.error("当前文件块已上传");
//                ServletUtils.printText(response, JSON.toJSONString(rb));
//                return;
//                //缓存文件块的编号比上传的小
//            } else if (chunk > cacheChunk) {
//                ReturnBean rb = ReturnBean.error("当前文件块需要等待上传,稍后请重试");
//                ServletUtils.printText(response, JSON.toJSONString(rb));
//                return;
//            }
//            //当编号相等 正常上传
//            if (!file.isEmpty()) {
//                try {
//                    Integer newCacheChunk = cacheChunk + 1;
//                    redisTemplate.opsForValue().set(chunkCurrkey, newCacheChunk.toString());
//                    log.debug("{}:redis块+1", chunk);
//                    //上传第一块
//                    if (chunk == 0) {
//                        try {
//                            storePath = appendFileStorageClient.uploadAppenderFile(FastdfsConstant.DEFAULT_GROUP, file.getInputStream(), file.getSize(), fileExt);
//                            log.debug("{}:更新完fastdfs", chunk);
//                            if (storePath == null) {
//                                //上传失败快编号还原
//                                redisTemplate.opsForValue().set(chunkCurrkey, cacheChunk.toString());
//                                ReturnBean rb = ReturnBean.error("获取远程文件路径出错");
//                                ServletUtils.printText(response, JSON.toJSONString(rb));
//                            }
//                        } catch (Exception e) {
//                            //上传失败快编号还原
//                            redisTemplate.opsForValue().set(chunkCurrkey, cacheChunk.toString());
//                            log.error("初次上传远程文件出错", e);
//                            ReturnBean rb = ReturnBean.error("上传远程服务器文件出错");
//                            ServletUtils.printText(response, JSON.toJSONString(rb));
//                        }
//                        //存储文件地址
//                        redisTemplate.opsForValue().set(FastdfsConstant.FASTDFSPATH + fileMd5, storePath.getPath());
//                        log.debug("上传文件 result={}", storePath);
//                    } else {
//                        String noGroupPath = redisTemplate.opsForValue().get(FastdfsConstant.FASTDFSPATH + fileMd5);
//                        if (noGroupPath == null) {
//                            ReturnBean rb = ReturnBean.error("无法获取上传远程服务器文件出错");
//                            ServletUtils.printText(response, JSON.toJSONString(rb));
//                        }
//                        try {
//                            //追加方式实际实用如果中途出错多次,可能会出现重复追加情况,这里改成修改模式,即时多次传来重复文件块,依然可以保证文件拼接正确
//                            //获取切片大小
//                            Integer chunkSize = Integer.valueOf(paramMap.get("chunk_size").toString());
//                            appendFileStorageClient.modifyFile(FastdfsConstant.DEFAULT_GROUP, noGroupPath, file.getInputStream(), file.getSize(), chunk * chunkSize);
//                            log.debug("{}:更新完fastdfs", chunk);
//                            storePath = new StorePath(FastdfsConstant.DEFAULT_GROUP, noGroupPath);
//                        } catch (Exception e) {
//                            redisTemplate.opsForValue().set(chunkCurrkey, cacheChunk.toString());
//                            log.error("更新远程文件出错", e);
//                            ReturnBean rb = ReturnBean.error("更新远程文件出错");
//                            ServletUtils.printText(response, JSON.toJSONString(rb));
//                        }
//                    }
//                    //最后一块,清空缓存
//                    if (chunk + 1 == chunks) {
//                        //删除块编号
//                        redisTemplate.delete(chunkCurrkey);
//                        //删除地址缓存
//                        redisTemplate.delete(FastdfsConstant.FASTDFSPATH + fileMd5);
//                        //删除锁
//                        redisTemplate.delete(FastdfsConstant.CHUNKLOCK + fileMd5);
//                        //上传完成保存记录
//                        FileResult fileResult = new FileResult();
//                        fileResult.setMd5(fileMd5);
//                        fileResult.setName(paramMap.get("name").toString());
//                        fileResult.setFileLenght(file.getSize());
//                        fileResult.setUrl(filePrefix + storePath.getFullPath());
//                        fileResultService.insertAsync(fileResult);
//                    }
//                } catch (Exception e) {
//                    log.error("上传文件错误", e);
//                    ReturnBean rb = ReturnBean.error("上传错误 " + e.getMessage());
//                    ServletUtils.printText(response, JSON.toJSONString(rb));
//                }
//            }
//        } finally {
//            //释放上传锁
//            redisTemplate.opsForValue().set(chunklockName, "0");
//        }
//        ReturnBean rb = ReturnBean.ok(filePrefix + storePath.getFullPath());
//        ServletUtils.printText(response, JSON.toJSONString(rb));
//    }

    /**
     * 上传验证
     *
     * @param dir  媒体类型
     * @param type 上传类别
     */
    private boolean validate(String dir, String type, MultipartFile file) throws IOException {
        if (dir == null || !UploadUtils.extMap.containsKey(dir.toLowerCase())) {

            if (UploadTypeEnum.DEFAULT.getValue().equals(type) || UploadTypeEnum.CHUNK.getValue().equals(type)) {
                ReturnBean rb = ReturnBean.error("文件类型不合法");
                ServletUtils.printText(response, JSON.toJSONString(rb));
                return false;
            }

            if (UploadTypeEnum.KINDEDITOR.getValue().equals(type)) {
                Map<String, Object> msg = new HashMap<>(2);
                msg.put("error", 1);
                msg.put("message", "文件类型不合法");
                ServletUtils.printText(response, JSON.toJSONString(msg));
                return false;
            }

        }
        if (file == null || file.isEmpty()) {

            if (UploadTypeEnum.DEFAULT.getValue().equals(type) || UploadTypeEnum.CHUNK.getValue().equals(type)) {
                ReturnBean rb = ReturnBean.error("文件不能为空");
                ServletUtils.printText(response, JSON.toJSONString(rb));
                return false;
            }

            if (UploadTypeEnum.KINDEDITOR.getValue().equals(type)) {
                Map<String, Object> msg = new HashMap<>(2);
                msg.put("error", 1);
                msg.put("message", "文件不能为空");
                ServletUtils.printText(response, JSON.toJSONString(msg));
                return false;
            }
        }
        if (dir != null && UploadTypeEnum.CHUNK.getValue().equals(type)) {
            if (!Arrays.asList(UploadUtils.extMap.get(dir).split(GlobalConstant.COMMA)).contains(FilenameUtils.getExtension(file.getOriginalFilename()))) {

                if (UploadTypeEnum.DEFAULT.getValue().equals(type)) {
                    ReturnBean rb = ReturnBean.error("上传文件扩展名只允许" + UploadUtils.extMap.get(dir) + "格式");
                    ServletUtils.printText(response, JSON.toJSONString(rb));
                    return false;
                }

                if (UploadTypeEnum.KINDEDITOR.getValue().equals(type)) {
                    Map<String, Object> msg = new HashMap<>(2);
                    msg.put("error", 1);
                    msg.put("message", "上传文件扩展名只允许" + UploadUtils.extMap.get(dir) + "格式");
                    ServletUtils.printText(response, JSON.toJSONString(msg));
                    return false;
                }
            }
        }

        //验证MD5
        assert file != null;
        md5 = FileMd5Utils.getMd5File(file.getInputStream());
        FileResult fileResult = fileResultService.selectByMd5(md5);
        if (fileResult != null) {
            if (UploadTypeEnum.DEFAULT.getValue().equals(type) || UploadTypeEnum.CHUNK.getValue().equals(type)) {
                //返回结果
                ReturnBean rb = ReturnBean.ok(fileResult.getUrl());
                ServletUtils.printText(response, JSON.toJSONString(rb));
                return false;
            }
            if (UploadTypeEnum.KINDEDITOR.getValue().equals(type)) {
                Map<String, Object> msg = new HashMap<>(2);
                msg.put("error", 0);
                msg.put("url", fileResult.getUrl());
                ServletUtils.printText(response, JSON.toJSONString(msg));
                return false;
            }
        }
        return true;
    }
}
