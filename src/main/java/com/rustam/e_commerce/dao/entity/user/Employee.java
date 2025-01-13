package com.rustam.e_commerce.dao.entity.user;

import com.rustam.e_commerce.dao.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("EMPLOYEE")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Employee extends BaseUser{
    String username;
    LocalDate startWorkDay;
    String iban;
}
