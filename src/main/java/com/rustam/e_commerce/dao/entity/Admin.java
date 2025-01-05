package com.rustam.e_commerce.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("ADMIN")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Admin extends BaseUser {

    @Column(unique = true)
    private String username;
}