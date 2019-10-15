package com.lm.pedro.test.receipt.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "ITEMS")
@Data
@NoArgsConstructor
public class Item implements Serializable {

    private static final long serialVersionUID = 5052271440262044656L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE", scale = 2, precision = 2)
    private double price;

    @Column(name = "IMPORTED")
    private boolean imported;

    @Column(name = "EXEMPT")
    private boolean exempt;

    @Column(name = "ACTIVE")
    private boolean active;
}
