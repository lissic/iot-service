package com.hlytec.cloud.common.result;

import java.util.Collections;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @description: Page
 * @author: zero
 * @date: 2021/5/24 10:54
 */
public class PageResult<T> {

    protected List<T> list = Collections.emptyList();

    /**
     * 总数
     */
    protected long total = 0;
    /**
     * 每页显示条数，默认 10
     */
    protected long size = 10;

    /**
     * 当前页
     */
    protected long current = 1;

    protected long pages = 1;

    public PageResult() {
    }

    public PageResult(IPage<T> page) {
        this.list = page.getRecords();
        this.total = page.getTotal();
        this.size = page.getSize();
        this.current = page.getCurrent();
        this.pages = page.getPages();
    }

    /**
     * 分页构造函数
     *
     * @param current 当前页
     * @param size    每页显示条数
     */
    public PageResult(long current, long size) {
        this(current, size, 0);
    }

    public PageResult(long current, long size, long total) {
        if (current > 1) {
            this.current = current;
        }
        this.size = size;
        this.total = total;
    }

    /**
     * 是否存在上一页
     *
     * @return true / false
     */
    public boolean hasPrevious() {
        return this.current > 1;
    }

    /**
     * 是否存在下一页
     *
     * @return true / false
     */
    public boolean hasNext() {
        return this.current < this.pages;
    }

    public List<T> getList() {
        return this.list;
    }

    public PageResult<T> setList(List<T> records) {
        this.list = records;
        return this;
    }

    public long getTotal() {
        return this.total;
    }

    public PageResult<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    public long getSize() {
        return this.size;
    }

    public PageResult<T> setSize(long size) {
        this.size = size;
        return this;
    }

    public long getCurrent() {
        return this.current;
    }

    public PageResult<T> setCurrent(long current) {
        this.current = current;
        return this;
    }

}
