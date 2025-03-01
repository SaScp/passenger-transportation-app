package org.service.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

public class PageEntity implements Serializable {
    private int pageNum;
    private int pageSize;


    public PageEntity(String pageNum, String pageSize) {
        this.pageNum = getInt(pageNum, 0);
        this.pageSize = getInt(pageSize, 5);
    }

    public PageEntity(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PageEntity that)) return false;
        return pageNum == that.pageNum && pageSize == that.pageSize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageNum, pageSize);
    }
}
