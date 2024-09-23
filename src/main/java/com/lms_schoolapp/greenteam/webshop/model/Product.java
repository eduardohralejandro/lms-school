package com.lms_schoolapp.greenteam.webshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;

    @Override
    public String toString() {
        return "Product name: " + name;
    }

    public String displayInfoBeforeDetails() {
        return "Product name: " + name + ", " + description + ", price: " + price;
    }
}
