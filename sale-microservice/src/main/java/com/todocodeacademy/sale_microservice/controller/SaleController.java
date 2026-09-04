package com.todocodeacademy.sale_microservice.controller;

import com.todocodeacademy.sale_microservice.dto.SaleDTO;
import com.todocodeacademy.sale_microservice.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.todocodeacademy.sale_microservice.constants.StringResource.ControllerConstants.SALE_CREATED_SUCCESSFULLY;
import static com.todocodeacademy.sale_microservice.constants.StringResource.ControllerConstants.SALE_DELETED_SUCCESSFULLY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sales")
public class SaleController {

    private final SaleService service;

    @GetMapping("/all")
    public List<SaleDTO> getAllSales() {

        return service.getSales();
    }

    @GetMapping("/{saleID}")
    public SaleDTO getSaleById(@PathVariable Long saleID) {

        return service.getSaleInfoByID(saleID);
    }

    @PostMapping("/registerSale")
    public String registerSale(@RequestBody Long cartID) {

        service.createSale(cartID);

        return SALE_CREATED_SUCCESSFULLY;
    }

    @DeleteMapping("/delete/{saleID}")
    public String deleteSaleById(@PathVariable Long saleID) {

        service.deleteSale(saleID);

        return SALE_DELETED_SUCCESSFULLY;
    }
}
