package com.safepass.safebuilding.rent_contract.controller;

import com.safepass.safebuilding.rent_contract.service.RentContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/web/rent-contracts")
public class RentContractController {
    @Autowired
    private RentContractService rentContractService;



}
