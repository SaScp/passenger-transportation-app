package org.service.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

public record PageEntity(
        int pageNum,
        int pageSize) implements Serializable {


    public PageEntity(String pageNum, String pageSize) {
        this(getInt(pageNum, 0), getInt(pageSize, 5));
    }

    public PageEntity(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
    public PageEntity(int defaultSize) {
        this(defaultSize, defaultSize);
    }

    private static Integer getInt(String obj, Integer orElseObj) {
        return Optional.ofNullable(obj).filter(e -> e.matches("\\d+")).map(Integer::parseInt).filter(e -> e > 0).orElse(orElseObj);
    }
}
