package org.service.entity;

import java.util.Optional;

public class PageEntity {
    private int pageNum;
    private int pageSize;


    public PageEntity(String pageNum, String pageSize) {
        this.pageNum = getInt(pageNum, 0);
        this.pageSize = getInt(pageSize, 5);
    }

    public PageEntity() {
    }
    private static Integer getInt(String obj, Integer orElseObj) {
        return Optional.ofNullable(obj).map(Integer::parseInt).filter(e -> e > 0).orElse(orElseObj);
    }
    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
