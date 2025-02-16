package org.service.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public final class ParamsEntity {
    private LocalDateTime time;
    private String type;
    private String from;
    private String to;

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
        return Objects.hash(time, type, from, to);
    }

    @Override
    public String toString() {
        return "ParamsEntity[" +
                "time=" + time + ", " +
                "type=" + type + ", " +
                "from=" + from + ", " +
                "to=" + to + ", " + ']';
    }

}
