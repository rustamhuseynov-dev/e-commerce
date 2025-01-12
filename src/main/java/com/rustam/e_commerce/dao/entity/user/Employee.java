package com.rustam.e_commerce.dao.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "employees")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Employee extends BaseUser {
    private String username;
    private LocalDate startWorkDay;
    private String iban;
}
