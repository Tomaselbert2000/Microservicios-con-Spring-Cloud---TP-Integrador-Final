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
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private static final Long FALLBACK_ID = -999999L;
    private static final BigDecimal FALLBACK_PRICE = BigDecimal.valueOf(0.0);

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
    @Retry(name = "cart-microservice")
    public SaleDTO getSaleInfoByID(Long saleID) {

        Sale sale = loadSale(saleID);

        CartDTO cartDTO = loadCart(sale.getCartID());

        return mapper.mapEntityToDTO(sale, cartDTO);
    }

    @Override
    @CircuitBreaker(name = "cart-microservice", fallbackMethod = "fallbackGetSales")
    @Retry(name = "cart-microservice")
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

    public SaleDTO fallbackGetSaleInfoByID(Long saleID, Throwable throwable) {

        return buildFallbackDTO(saleID, throwable);
    }

    public List<SaleDTO> fallbackGetSales(Throwable throwable) {

        return List.of(buildFallbackDTO(FALLBACK_ID, throwable));
    }

    private SaleDTO buildFallbackDTO(Long saleID, Throwable throwable) {

        return SaleDTO.builder()
                .saleID(saleID)
                .cartID(FALLBACK_ID)
                .timestamp(null)
                .saleTotal(FALLBACK_PRICE)
                .productNameList(List.of("Error: " + throwable.getMessage()))
                .build();
    }
}
