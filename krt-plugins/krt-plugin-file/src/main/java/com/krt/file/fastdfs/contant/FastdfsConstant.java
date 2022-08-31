package com.krt.file.fastdfs.contant;

/**
 * 上传常量
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年09月10日
 */
public class FastdfsConstant {

    /**
     * 上传前缀
     */
    private final static String UPLOADING = "Uploading:";
    /**
     * 锁前缀
     */
    private final static String LOCK = UPLOADING + "lock:";
    /**
     * 文件前缀
     */
    private final static String FILE = UPLOADING + "file:";

    /**
     * 当前文件传输到第几块
     */
    public final static String CHUNKCURR = FILE + "chunkCurr:";

    /**
     * 当前文件上传到fastdfs路径
     */
    public final static String FASTDFSPATH = FILE + "fastDfsPath:";

    /**
     * 默认分组
     */
    public final static String DEFAULT_GROUP = "group1";

    /**
     * 文件块锁(解决同一个用户正在上传时并发解决,比如后端正在上传一个大文件块,前端页面点击删除按钮,
     * 继续添加删除的文件,这时候要加锁来阻止其上传,否则会出现丢块问题,
     * 因为fastdfs上传不像迅雷下载一样,下载时会创建一个完整的文件,如果上传第一块时,服务器能快速创建一个大文件0填充,那么这样可以支持并发乱序来下载文件块,上传速度会成倍提升,要实现乱序下载文件块,估计就得研究fastdfs源码了)
     */
    public final static String CHUNKLOCK = LOCK + "chunkLock:";
}
