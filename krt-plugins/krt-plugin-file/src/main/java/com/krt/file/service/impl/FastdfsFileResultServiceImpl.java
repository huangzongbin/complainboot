package com.krt.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.DefaultGenerateStorageClient;
import com.krt.common.base.BaseServiceImpl;
import com.krt.file.entity.FileResult;
import com.krt.file.mapper.FileResultMapper;
import com.krt.file.service.IFileResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;


/**
 * 文件服务接口实现层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年09月11日
 */
@Service
@ConditionalOnProperty(name = "web.upload-type", havingValue = "fastdfs")
public class FastdfsFileResultServiceImpl extends BaseServiceImpl<FileResultMapper, FileResult> implements IFileResultService {

    @Value("${web.file-prefix}")
    private String filePrefix;

    @Autowired
    private DefaultGenerateStorageClient defaultGenerateStorageClient;

    /**
     * 新增文件记录
     *
     * @param entity 实体
     * @return true成功　false失败
     */
    @Override
    @Async
    public void insertAsync(FileResult entity) {
        super.insert(entity);
    }


    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList 主键ID列表
     * @return true:成功 false:失败
     */
    @Override
    @CacheEvict(value = "fileResultCache")
    public boolean deleteByIds(Collection<? extends Serializable> idList) {
        for (Serializable id : idList) {
            deleteById(id);
        }
        return true;
    }

    /**
     * 删除文件
     */
    @Override
    @CacheEvict(value = "fileResultCache")
    public boolean deleteById(Serializable id) {
        FileResult fileResult = selectById(id);
        String fileUrl = fileResult.getUrl();
        StorePath storePath = StorePath.praseFromUrl(fileUrl.replace(filePrefix, ""));
        defaultGenerateStorageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        return super.deleteById(id);
    }

    /**
     * 根据md5查询文件
     *
     * @return 文件
     */
    @Override
    @Cacheable(value = "fileResultCache", key = "#md5", unless = "#result == null")
    public FileResult selectByMd5(String md5) {
        return selectOne(new LambdaQueryWrapper<FileResult>().eq(FileResult::getMd5, md5));
    }
}
