package org.service.passangertransportationgraphjpaadapter;

import java.time.LocalDateTime;
import java.util.Optional;

public class FilterParamEntity {
    private LocalDateTime time;
    private String type;
    private String from;
    private String to;

    public FilterParamEntity(LocalDateTime localDateTime, String type, String from, String to) {
        this.time = localDateTime;
        this.type = getString(type);
        this.from = getString(from);
        this.to = getString(to);
    }

    private static String getString(String to) {
        return Optional.ofNullable(to).map(e -> e.trim()).orElse(null);
    }

    public FilterParamEntity(){}
    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
