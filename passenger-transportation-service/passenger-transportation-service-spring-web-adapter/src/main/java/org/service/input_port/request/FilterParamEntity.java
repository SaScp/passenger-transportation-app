package org.service.input_port.request;

import java.time.ZonedDateTime;

public class FilterParamEntity {
    private ZonedDateTime zonedDateTime;
    private String type;
    private String from;
    private String to;

    public FilterParamEntity(ZonedDateTime zonedDateTime, String type, String from, String to) {
        this.zonedDateTime = zonedDateTime;
        this.type = type;
        this.from = from;
        this.to = to;
    }

    public FilterParamEntity(){}
    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public void setZonedDateTime(ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
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
