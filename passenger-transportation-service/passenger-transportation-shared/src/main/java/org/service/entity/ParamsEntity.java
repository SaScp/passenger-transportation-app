package org.service.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public record ParamsEntity(
        LocalDateTime time,
        String type,
        String from,
        String to,
        List<String> routeId) implements Serializable {


    public ParamsEntity(LocalDateTime time, String type, String from, String to, List<String> routeId) {
        this.time = time;
        this.type = type;
        this.from = from;
        this.to = to;
        this.routeId = routeId;
    }

    public ParamsEntity(List<String> routeId) {
        this(null, null, null, null, routeId);
    }


    public ParamsEntity(LocalDateTime time, String type, String from, String to) {
        this(time, type, from, to, null);
    }


    @Override
    public int hashCode() {
        return Objects.hash(time, type, from, to, routeId);
    }
}
