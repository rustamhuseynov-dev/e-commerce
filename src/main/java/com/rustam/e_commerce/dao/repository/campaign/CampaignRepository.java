package com.rustam.e_commerce.dao.repository.campaign;

import com.rustam.e_commerce.dao.entity.campaign.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign,Long> {
}
