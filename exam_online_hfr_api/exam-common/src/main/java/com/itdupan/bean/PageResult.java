package com.itdupan.bean;

import java.util.List;

public class PageResult<T> {

    private Long total;// 总条数
    private Integer totalPage;// 总页数
    private List<T> list;// 当前页数据

    public PageResult() {
    }

    public PageResult(Long total, Integer totalPage, List<T> list) {
        this.total = total;
        this.totalPage = totalPage;
        this.list = list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
