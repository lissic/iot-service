package com.hlytec.cloud.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hlytec.cloud.common.entity.BaseEntity;
import com.hlytec.cloud.common.result.PageResult;

/**
 * @description: BaseService
 * @author: zero
 * @date: 2021/5/18 9:42
 */
@Transactional(readOnly = true)
public abstract class BaseService<M extends BaseMapper<T>, T extends BaseEntity<T>> {

    @Autowired
    protected M mapper;

    /**
     * 获取单条数据
     * @param id id
     * @return T
     */
    public T get(String id) {
        return mapper.selectById(id);
    }

    /**
     * 获取单条数据
     * @param entity entity
     * @return T
     */
    public T get(T entity) {
        return mapper.selectOne(new QueryWrapper<>(entity));
    }

    /**
     * 查询列表数据
     * @param entity entity
     * @return List<T>
     */
    public List<T> findList(T entity) {
        return mapper.selectList(new QueryWrapper<>(entity));
    }

    /**
     * 查询分页数据
     * @param page 分页对象
     * @param entity entity
     * @return Page<T>
     */
    public PageResult<T> findPage(Page<T> page, T entity) {
        Page<T> tPage = mapper.selectPage(page, new QueryWrapper<>(entity));
        PageResult<T> result = new PageResult<>(tPage);
        return result;
    }

    /**
     * 查询分页数据
     * @param page 分页对象
     * @param entity entity
     * @return Page<T>
     */
    public PageResult<T> findPageWithParam(Page<T> page, QueryWrapper<T> entity) {
        Page<T> tPage = mapper.selectPage(page, entity);
        PageResult<T> result = new PageResult<>(tPage);
        return result;
    }

    /**
     * 保存数据
     * @param entity entity
     */
    @Transactional(readOnly = false)
    public String save(T entity) {
        mapper.insert(entity);
        return entity.getId();
    }

    /**
     * 更新数据
     * @param entity entity
     */
    @Transactional(readOnly = false)
    public void updateById(T entity) {
        mapper.updateById(entity);
    }

    /**
     * 删除数据
     * @param id id
     */
    @Transactional(readOnly = false)
    public void delete(String id) {
        mapper.deleteById(id);
    }

    /**
     * 批量删除
     * @param ids ids
     */
    @Transactional(readOnly = false)
    public void batchDelete(List<String> ids) {
        mapper.deleteBatchIds(ids);
    }


}
