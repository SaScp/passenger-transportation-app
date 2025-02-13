package org.service.entity;

import java.time.ZonedDateTime;

public record ParamsEntity(ZonedDateTime zonedDateTime,
                           String type,
                           String from,
                           String to) {

}
