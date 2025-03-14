package org.service.passangertransportationgraphjpaadapter.dto;

import lombok.*;
import org.service.passangertransportationgraphjpaadapter.model.Type;

import java.io.Serializable;

/**
 * DTO for {@link Type}
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TypeDto implements Serializable {
    private Long id;
    private String typeName;
    private String description;

    public static TypeDto fromType(Type type) {
        return new TypeDto(type.getId(), type.getTypeName(), type.getDescription());
    }
}