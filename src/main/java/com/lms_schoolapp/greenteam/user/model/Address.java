package com.lms_schoolapp.greenteam.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Column(nullable = false)
    @Size(min = 1, max = 280)
    private String street;
    @Column(nullable = false)
    @Size(min = 1, max = 100)
    private String city;
    @Column(nullable = false)
    @Size(min = 1, max = 100)
    private String state;
}
