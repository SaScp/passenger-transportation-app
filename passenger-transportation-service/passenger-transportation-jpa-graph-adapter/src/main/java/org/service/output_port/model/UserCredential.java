package org.service.output_port.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_credential")
public class UserCredential {

    @Id
    private Long id;

    @Column(name = "card")
    private String card;

    @Column(name = "cvc")
    private Byte cvc;

    @Column(name = "expired_date")
    private LocalDateTime expiredDate;
}
