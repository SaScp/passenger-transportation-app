package org.service.ouptput_port.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_user")
public class User {
    @Id
    @Column(name = "user_phone", nullable = false)
    private String numberPhone;

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String id) {
        this.numberPhone = id;
    }
}
