package org.service.passangertransportationgraphjpaadapter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_location")
public class Location {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "c_name", nullable = false)
    private String cName;


}
