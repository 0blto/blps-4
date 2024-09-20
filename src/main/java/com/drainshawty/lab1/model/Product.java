package com.drainshawty.lab1.model;


import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    String description;
}
