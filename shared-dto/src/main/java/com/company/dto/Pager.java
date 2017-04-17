package com.company.dto;

import java.io.Serializable;

public class Pager implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int DEFAULT_PAGE_SIZE = 20;

    // 总 记录条数
    private final int total;

    // 每页记录条数
    private final int pageSize;

    // 页数
    private final int pages;

    // 当前页数
    private int page;

    public Pager(int total) {
        this(total, DEFAULT_PAGE_SIZE);
    }

    public Pager(int total, int pageSize) {
        this.total = total;
        this.pageSize = pageSize;
        this.pages = (total - 1) / pageSize + 1;
    }

    public int getTotal() {
        return total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPages() {
        return pages;
    }

    public int getPage() {
        return page;
    }

    public Pager setPage(int page) {
        this.page = page;
        return this;
    }
}