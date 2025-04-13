package com.rustam.e_commerce.service;

import com.rustam.e_commerce.dao.entity.Product;
import com.rustam.e_commerce.dao.entity.campaign.Campaign;
import com.rustam.e_commerce.dao.repository.campaign.CampaignRepository;
import com.rustam.e_commerce.dto.request.create.CampaignRequest;
import com.rustam.e_commerce.dto.request.read.ProductDiscountedRequest;
import com.rustam.e_commerce.dto.response.create.CampaignResponse;
import com.rustam.e_commerce.dto.response.read.ProductDiscountedResponse;
import com.rustam.e_commerce.util.UtilService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CampaignService {

    CampaignRepository campaignRepository;
    UtilService utilService;
    ModelMapper modelMapper;

    public CampaignResponse create(CampaignRequest campaignRequest) {

        Campaign campaign = Campaign.builder()
                .name(campaignRequest.getName())
                .discountPercentage(campaignRequest.getDiscountPercentage())
                .build();
        campaignRepository.save(campaign);


        List<ProductDiscountedResponse> productDiscountedResponse = new ArrayList<>();
        for (ProductDiscountedRequest productRequest : campaignRequest.getRequests()) {
            Product product = utilService.findByProductId(productRequest.getProductId());

            product.setCampaign(campaign);
            product.setDiscount(campaignRequest.getDiscountPercentage());
            double discount = campaign.getDiscountPercentage();
            double discountedPrice = product.getPrice() - (product.getPrice() * discount / 100);
            product.setSpecialPrice(discountedPrice);
            campaign.getProducts().add(product);
            ProductDiscountedResponse response = modelMapper.map(product, ProductDiscountedResponse.class);
            productDiscountedResponse.add(response);
        }

        return CampaignResponse.builder()
                .id(campaign.getId())
                .name(campaign.getName())
                .discountPercentage(campaign.getDiscountPercentage())
                .responses(productDiscountedResponse)
                .build();
    }

}
