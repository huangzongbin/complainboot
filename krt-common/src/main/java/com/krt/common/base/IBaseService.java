package com.krt.common.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 抽取公共服务层接口
 *
 * @author 殷帅
 * @version 1.0
 * @date 2016年7月16日
 */
public interface IBaseService<T> {

    /**
     * 插入一条记录
     *
     * @param entity 实体
     * @return true:成功 false:失败
     */
    boolean insert(T entity);

    /**
     * 插入（批量）
     *
     * @param entityList 实体对象集合
     * @param batchSize  插入批次数量
     * @return true:成功 false:失败
     */
    boolean insertBatch(Collection<T> entityList, int batchSize);

    /**
     * 插入（批量）(默认每次操作1000条数据)
     *
     * @param entityList 实体对象集合
     * @return true:成功 false:失败
     */
    default boolean insertBatch(Collection<T> entityList) {
        return insertBatch(entityList, 1000);
    }

    /**
     * TableId 注解存在更新记录，否插入一条记录
     *
     * @param entity 实体对象
     * @return true:成功 false:失败
     */
    boolean insertOrUpdate(T entity);

    /**
     * 批量修改插入
     *
     * @param entityList 实体对象集合
     * @param batchSize  每次的数量
     * @return true:成功 false:失败
     */
    boolean insertOrUpdateBatch(Collection<T> entityList, int batchSize);

    /**
     * 批量修改插入(默认每次操作1000条数据)
     *
     * @param entityList 实体对象集合
     * @return true:成功 false:失败
     */
    default boolean insertOrUpdateBatch(Collection<T> entityList) {
        return insertOrUpdateBatch(entityList, 1000);
    }

    /**
     * 根据 ID 删除
     *
     * @param id 数据库id
     * @return true:成功 false:失败
     */
    boolean deleteById(Serializable id);

    /**
     * 根据 columnMap 条件，删除记录
     *
     * @param columnMap 表字段 map 对象
     * @return true:成功 false:失败
     */
    boolean deleteByMap(Map<String, Object> columnMap);

    /**
     * 根据 entity 条件，删除记录
     *
     * @param queryWrapper 实体包装类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @return true:成功 false:失败
     */
    boolean delete(Wrapper<T> queryWrapper);

    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList 主键ID列表
     * @return true:成功 false:失败
     */
    boolean deleteByIds(Collection<? extends Serializable> idList);

    /**
     * 根据 ID 选择修改
     *
     * @param entity 实体对象
     * @return true:成功 false:失败
     */
    boolean updateById(T entity);

    /**
     * 根据 whereEntity 条件，更新记录
     *
     * @param entity        实体对象
     * @param updateWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper}
     * @return true:成功 false:失败
     */
    boolean update(T entity, Wrapper<T> updateWrapper);

    /**
     * 根据ID 批量更新
     *
     * @param entityList 实体对象集合
     * @return true:成功 false:失败
     */
    default boolean updateBatchById(Collection<T> entityList) {
        return updateBatchById(entityList, 1000);
    }

    /**
     * 根据ID 批量更新
     *
     * @param entityList 实体对象集合
     * @param batchSize  更新批次数量
     * @return true:成功 false:失败
     */
    boolean updateBatchById(Collection<T> entityList, int batchSize);


    /**
     * 根据 ID 查询
     *
     * @param id
     * @return {@link T}
     */
    T selectById(Serializable id);

    /**
     * 查询（根据ID 批量查询）
     *
     * @param idList id列表
     * @return {@link Collection<T>}
     */
    Collection<T> selectByIds(Collection<? extends Serializable> idList);

    /**
     * 查询（根据 columnMap 条件）
     *
     * @param columnMap 表字段 map 对象
     * @return {@link Collection<T>}
     */
    Collection<T> selectByMap(Map<String, Object> columnMap);

    /**
     * 根据 Wrapper，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @return {@link T}
     */
    default T selectOne(Wrapper<T> queryWrapper) {
        return selectOne(queryWrapper, true);
    }

    /**
     * 根据 Wrapper，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @param throwEx      有多个 result 是否抛出异常
     * @return {@link T}
     */
    T selectOne(Wrapper<T> queryWrapper, boolean throwEx);

    /**
     * 根据 Wrapper，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @return {@link Map<String, Object>}
     */
    Map<String, Object> selectMap(Wrapper<T> queryWrapper);

    /**
     * 根据 Wrapper，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @param mapper       转换函数
     * @return {@link V}
     */
    default <V> V selectObj(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
        return SqlHelper.getObject(selectObjs(queryWrapper, mapper));
    }

    /**
     * 根据 Wrapper 条件，查询总记录数
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @return 数量
     */
    int selectCount(Wrapper<T> queryWrapper);

    /**
     * 查询总记录数
     *
     * @return 总记录数
     * @see Wrappers#emptyWrapper()
     */
    default int selectCount() {
        return selectCount(Wrappers.emptyWrapper());
    }

    /**
     * 查询列表
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @return {@link List<T>}
     */
    List<T> selectList(Wrapper<T> queryWrapper);

    /**
     * 查询所有
     *
     * @return {@link List<T>}
     * @see Wrappers#emptyWrapper()
     */
    default List<T> selectList() {
        return selectList(Wrappers.emptyWrapper());
    }


    /**
     * 查询列表
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @return {@link List<Map<String, Object>>}
     */
    List<Map<String, Object>> selectMaps(Wrapper<T> queryWrapper);

    /**
     * 查询所有列表
     *
     * @return {@link List<Map<String, Object>>}
     * @see Wrappers#emptyWrapper()
     */
    default List<Map<String, Object>> selectMaps() {
        return selectMaps(Wrappers.emptyWrapper());
    }

    /**
     * 查询全部记录
     *
     * @return {@link List<Object>>}
     */
    default List<Object> selectObjs() {
        return selectObjs(Function.identity());
    }

    /**
     * 查询全部记录
     *
     * @param mapper 转换函数
     * @return {@link List<V>}
     */
    default <V> List<V> selectObjs(Function<? super Object, V> mapper) {
        return selectObjs(Wrappers.emptyWrapper(), mapper);
    }

    /**
     * 根据 Wrapper 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @return {@link List<Object>>}
     */
    default List<Object> selectObjs(Wrapper<T> queryWrapper) {
        return selectObjs(Wrappers.emptyWrapper(), Function.identity());
    }

    /**
     * 根据 Wrapper 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @param mapper       转换函数
     * @return {@link List<V>}
     */
    <V> List<V> selectObjs(Wrapper<T> queryWrapper, Function<? super Object, V> mapper);

    /**
     * 翻页查询
     *
     * @param page         翻页对象
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @return {@link IPage<T>}
     */
    IPage<T> selectPage(IPage<T> page, Wrapper<T> queryWrapper);

    /**
     * 无条件翻页查询
     *
     * @param page 翻页对象
     * @return {@link IPage<T>}
     * @see Wrappers#emptyWrapper()
     */
    default IPage<T> selectPage(IPage<T> page) {
        return selectPage(page, Wrappers.emptyWrapper());
    }

    /**
     * <p>
     * 翻页查询
     * </p>
     *
     * @param page         翻页对象
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @return {@link IPage<Map<String, Object>>}
     */
    IPage<Map<String, Object>> selectPageMaps(IPage<T> page, Wrapper<T> queryWrapper);

    /**
     * 无条件翻页查询
     *
     * @param page 翻页对象
     * @return {@link IPage<Map<String, Object>>}
     * @see Wrappers#emptyWrapper()
     */
    default IPage<Map<String, Object>> selectPageMaps(IPage<T> page) {
        return selectPageMaps(page, Wrappers.emptyWrapper());
    }

    /**
     * 自定义分页（PageHelper模式）
     *  
     * @param para
     * @return {@link IPage}
     */
    IPage selectPageList(Map para);

    /**
     * excel导出
     *
     * @param para 参数
     * @return {@link List}
     */
    List selectExcelList(Map para);
}
