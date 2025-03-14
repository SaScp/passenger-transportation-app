package org.service.passangertransportationgraphjpaadapter.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public final class ParamsEntity implements Serializable {
    private LocalDateTime time;
    private String type;
    private String from;
    private String to;
    private List<String> routeId;

    public ParamsEntity(LocalDateTime time,
                        String type,
                        String from,
                        String to
                        ) {
        this.time = time;
        this.type = type;
        this.from = from;
        this.to = to;
    }

    public ParamsEntity(List<String> routeId) {
        this.routeId = routeId;
    }

    public ParamsEntity() {
    }

    public ParamsEntity(LocalDateTime time, String type, String from, String to, List<String> routeId) {
        this.time = time;
        this.type = type;
        this.from = from;
        this.to = to;
        this.routeId = routeId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public List<String> getRouteId() {
        return routeId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ParamsEntity) obj;
        return Objects.equals(this.time, that.time) &&
                Objects.equals(this.type, that.type) &&
                Objects.equals(this.from, that.from) &&
                Objects.equals(this.to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, type, from, to, routeId);
    }

    @Override
    public String toString() {
        return "ParamsEntity[" +
                "time=" + time + ", " +
                "type=" + type + ", " +
                "from=" + from + ", " +
                "to=" + to + ", " + ']';
    }


    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setRouteId(List<String> routeId) {
        this.routeId = routeId;
    }
}
