package com.rustam.e_commerce.dao.entity.campaign;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "banner")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;
}
