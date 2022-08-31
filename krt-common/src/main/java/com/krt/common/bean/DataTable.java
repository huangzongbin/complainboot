package com.krt.common.bean;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.List;

/**
 * DataTable数据返回
 *
 * @author 殷帅
 * @version 1.0
 */
public class DataTable<T> implements Serializable {

    /**
     * 每页显示数据
     */
    private Long length = 10L;
    /**
     * 页数
     */
    private Long pageNum = 1L;
    /**
     * 总数
     */
    private Long recordsFiltered;
    /**
     * 数据
     */
    private List<T> data;


    public Long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public Long getPageNum() {
        return pageNum;
    }

    public void setPageNum(Long start) {
        Long page = start / this.getLength();
        this.pageNum = page + 1L;
    }

    public static DataTable ok(IPage page) {
        DataTable dataTable = new DataTable();
        dataTable.setLength(page.getSize());
        dataTable.setPageNum(page.getCurrent());
        dataTable.setData(page.getRecords());
        dataTable.setRecordsFiltered(page.getTotal());
        return dataTable;
    }
}
