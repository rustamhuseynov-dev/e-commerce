package com.rustam.e_commerce.dao.entity.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;



@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("USER")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseUser {
    @Column(unique = true)
    private String username;
}
