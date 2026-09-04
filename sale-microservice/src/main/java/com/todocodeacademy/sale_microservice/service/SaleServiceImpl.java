package com.todocodeacademy.sale_microservice.service;

import com.todocodeacademy.sale_microservice.dto.CartDTO;
import com.todocodeacademy.sale_microservice.dto.SaleDTO;
import com.todocodeacademy.sale_microservice.exceptions.CartNotFoundException;
import com.todocodeacademy.sale_microservice.exceptions.SaleNotFoundException;
import com.todocodeacademy.sale_microservice.exceptions.SaleRegisterFailureException;
import com.todocodeacademy.sale_microservice.mapper.SaleMapper;
import com.todocodeacademy.sale_microservice.model.Sale;
import com.todocodeacademy.sale_microservice.repository.CartAPIClient;
import com.todocodeacademy.sale_microservice.repository.SaleRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleMapper mapper;
    private final SaleRepository repository;
    private final CartAPIClient apiClient;

    @Override
    @Transactional
    public void createSale(Long cartID) {

        try {

            validateCartExistenceBeforeSaleCreation(cartID);

            Sale sale = mapper.mapDTOtoEntity(cartID);

            repository.save(sale);

        } catch (CartNotFoundException exception) {

            throw new SaleRegisterFailureException();
        }
    }

    @Override
    @Transactional
    public void deleteSale(Long saleID) {

        Sale sale = loadSale(saleID);

        repository.delete(sale);
    }

    @Override
    @CircuitBreaker(name = "cart-microservice", fallbackMethod = "fallbackGetSaleInfoByID")
    public SaleDTO getSaleInfoByID(Long saleID) {

        try {

            Sale sale = loadSale(saleID);

            CartDTO cartDTO = loadCart(sale.getCartID());

            return mapper.mapEntityToDTO(sale, cartDTO);

        } catch (Exception e) {

            return fallbackGetSaleInfoByID();
        }
    }

    @Override
    public List<SaleDTO> getSales() {

        List<Sale> sales = repository.findAll();

        if (!sales.isEmpty()) return sales.stream().map(
                sale -> mapper.mapEntityToDTO(
                        sale,
                        loadCart(sale.getCartID())
                )
        ).toList();

        return List.of();
    }

    private void validateCartExistenceBeforeSaleCreation(Long cartID) {

        CartDTO cartDTO = loadCart(cartID);

        if (cartDTO == null) {

            throw new CartNotFoundException();
        }
    }

    private Sale loadSale(Long saleID) {

        return repository.findById(saleID).orElseThrow(SaleNotFoundException::new);
    }

    private CartDTO loadCart(Long cartID) {

        return apiClient.getCartInfoByID(cartID);
    }

    public SaleDTO fallbackGetSaleInfoByID() {

        return SaleDTO.builder()
                .saleID(-99999999L)
                .cartID(-99999999L)
                .timestamp(null)
                .saleTotal(BigDecimal.valueOf(0.0))
                .productNameList(List.of("Hubo un error al procesar la información de venta. Intente nuevamente."))
                .build();
    }
}
