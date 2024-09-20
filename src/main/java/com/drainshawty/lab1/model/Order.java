package com.drainshawty.lab1.model;



import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "orders")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

    public enum Status {
        CREATED,
        PAID,
        CANCELLED,
        ARRIVED,
        LOST,
        RECEIVED,
        REJECTED,
        FINALIZED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    Product product;

    String customerName;
    Status status;
    Long amount;
}
