package org.service.output_port.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

public final class RoutePageEntity {
    private final String fromLocationId;
    private final String toLocationId;
    private final String[] edge_path;
    private final Double totalPrice;
    private final LocalDateTime depTime;
    private final Long totalTimeCost;
    private final String transportType;
    private final LocalDateTime arr_time;

    public RoutePageEntity(
            String fromLocationId,
            String toLocationId,
            String edge_path,
            Double totalPrice,
            Timestamp depTime,
            Timestamp arr_time,
            Long totalTimeCost,
            String transportType
    ) {
        this.fromLocationId = fromLocationId;
        this.toLocationId = toLocationId;
        String[] split = edge_path.replace(",", " ").trim().split(" ");
        this.edge_path = split;
        this.totalPrice = totalPrice;
        this.depTime = depTime.toLocalDateTime();
        this.totalTimeCost = totalTimeCost;
        this.transportType = transportType;
        this.arr_time = arr_time.toLocalDateTime();
    }

    public String fromLocationId() {
        return fromLocationId;
    }

    public String toLocationId() {
        return toLocationId;
    }

    public String[] path() {
        return edge_path;
    }

    public Double totalPrice() {
        return totalPrice;
    }

    public LocalDateTime depTime() {
        return depTime;
    }

    public Long totalTimeCost() {
        return totalTimeCost;
    }

    public String transportType() {
        return transportType;
    }

    public LocalDateTime getArrivTime() {
        return arr_time;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RoutePageEntity) obj;
        return Objects.equals(this.fromLocationId, that.fromLocationId) &&
                Objects.equals(this.toLocationId, that.toLocationId) &&
                Objects.equals(this.edge_path, that.edge_path) &&
                Objects.equals(this.totalPrice, that.totalPrice) &&
                Objects.equals(this.depTime, that.depTime) &&
                Objects.equals(this.totalTimeCost, that.totalTimeCost) &&
                Objects.equals(this.transportType, that.transportType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromLocationId, toLocationId, edge_path, totalPrice, depTime, totalTimeCost, transportType);
    }

    @Override
    public String toString() {
        return "RouteEntityTemp{" +
                "fromLocationId='" + fromLocationId + '\'' +
                ", toLocationId='" + toLocationId + '\'' +
                ", path=" + Arrays.toString(edge_path) +
                ", totalPrice=" + totalPrice +
                ", depTime=" + depTime +
                ", totalTimeCost=" + totalTimeCost +
                ", transportType='" + transportType + '\'' +
                ", arrivTime=" + arr_time +
                '}';
    }
}
