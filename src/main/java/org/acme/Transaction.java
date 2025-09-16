package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Transaction extends PanacheEntity {
    @Column(name = "user_id")
    public String userId;
    public Double amount;
    public String status;
}
