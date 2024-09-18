package com.lms_schoolapp.greenteam.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    @Size(min = 1, max = 100)
    private String firstName;
    @Column(name = "last_name")
    @Size(min = 1, max = 100)
    private String lastName;
    @Column(unique = true, length = 100)
    @Email(message = "Email should be valid and follow this format: student@company.com")
    private String email;
    @Size(min = 8, max = 255)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]*$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character.")
    private String password;
    @Embedded
    private Address address;
    @Size(min = 1, max = 30)
    private String telephone;
    UserType userType;
    boolean active;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address=" + address +
                ", telephone='" + telephone + '\'' +
                ", userType=" + userType +
                ", active=" + active +
                '}';
    }
}
