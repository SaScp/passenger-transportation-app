package org.service.input_port.request;

public class BookingQueryParam {
    private String numberPhone;
    private String routeId;

    public BookingQueryParam() {
    }

    public BookingQueryParam(String numberPhone, String routeId) {
        this.numberPhone = numberPhone;
        this.routeId = routeId;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }
}
