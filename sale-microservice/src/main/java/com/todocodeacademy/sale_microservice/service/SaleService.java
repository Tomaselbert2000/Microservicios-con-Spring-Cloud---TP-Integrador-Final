package com.todocodeacademy.sale_microservice.service;

import com.todocodeacademy.sale_microservice.dto.SaleDTO;

import java.util.List;

public interface SaleService {

    void createSale(Long cartID);

    void deleteSale(Long saleID);

    SaleDTO getSaleInfoByID(Long saleID);

    List<SaleDTO> getSales();
}
