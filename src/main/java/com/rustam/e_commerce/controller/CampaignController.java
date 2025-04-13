package com.rustam.e_commerce.controller;

import com.rustam.e_commerce.dto.request.create.CampaignRequest;
import com.rustam.e_commerce.dto.response.create.CampaignResponse;
import com.rustam.e_commerce.service.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/campaign")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CampaignController {

    private final CampaignService campaignService;

    @PostMapping(path = "/create")
    public ResponseEntity<CampaignResponse> create(@RequestBody CampaignRequest campaignRequest){
        return new ResponseEntity<>(campaignService.create(campaignRequest), HttpStatus.CREATED);
    }

}
