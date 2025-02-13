package org.service.entity;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public record ParamsEntity(LocalDateTime time,
                           String type,
                           String from,
                           String to) {

}
