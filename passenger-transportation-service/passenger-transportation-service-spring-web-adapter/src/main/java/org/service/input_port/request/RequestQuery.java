package org.service.input_port.request;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class RequestQuery {
    private String userName;
    private String routeId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }
}
