package com.todocodeacademy.cart_service.service;

import com.todocodeacademy.cart_service.dto.CartRequestDTO;
import com.todocodeacademy.cart_service.dto.CartResponseDTO;
import com.todocodeacademy.cart_service.dto.ProductDTO;
import com.todocodeacademy.cart_service.exceptions.CartNotFoundException;
import com.todocodeacademy.cart_service.exceptions.ProductNotFoundException;
import com.todocodeacademy.cart_service.mapper.CartMapper;
import com.todocodeacademy.cart_service.model.Cart;
import com.todocodeacademy.cart_service.repository.CartRepository;
import com.todocodeacademy.cart_service.repository.ProductAPIClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final ProductAPIClient apiClient;
    private final CartRepository repository;
    private final CartMapper mapper;

    @Override
    @Transactional
    public void saveCart(CartRequestDTO dto) {

        validateProductExistence(dto.getProductIDsList());

        List<BigDecimal> prices = createPricesList(dto.getProductIDsList());

        BigDecimal total = calculateSaleTotal(prices);

        Cart cart = mapper.mapDTOtoEntity(dto.getProductIDsList(), total);

        repository.save(cart);
    }

    @Override
    @Transactional
    public void deleteCart(Long cartID) {

        Cart cart = repository.findById(cartID).orElseThrow(CartNotFoundException::new);

        repository.delete(cart);
    }

    @Override
    public CartResponseDTO getCartInfoByID(Long cartID) {

        Cart cart = repository.findById(cartID).orElseThrow(CartNotFoundException::new);

        return buildCartResponse(cart);
    }

    @Override
    public List<CartResponseDTO> getAllCarts() {

        List<Cart> carts = repository.findAll();

        if (!carts.isEmpty())

            return carts.stream().map(this::buildCartResponse).collect(Collectors.toList());

        return List.of();
    }

    @Override
    public ProductDTO getProductByName(String productName) {

        return apiClient.getProductInfoByName(productName);
    }

    private void validateProductExistence(List<Long> productIDs) {

        if (!productIDs.isEmpty()) {

            for (Long id : productIDs) {

                if (apiClient.getProductByID(id) == null)

                    throw new ProductNotFoundException();
            }
        }
    }

    private BigDecimal calculateSaleTotal(List<BigDecimal> prices) {

        BigDecimal total = BigDecimal.valueOf(0);

        for (BigDecimal price : prices) {

            total = total.add(price);
        }

        return total;
    }

    private List<BigDecimal> createPricesList(List<Long> productIDsList) {

        List<BigDecimal> prices = new ArrayList<>();

        if (productIDsList.isEmpty()) return List.of();

        for (Long id : productIDsList) {

            BigDecimal price = apiClient.getProductByID(id).getUnitPrice();

            prices.add(price);
        }

        return prices;
    }

    private CartResponseDTO buildCartResponse(Cart cart) {

        List<ProductDTO> dtos = cart.getProducts().stream()
                .map(apiClient::getProductByID)
                .toList();

        return mapper.mapEntityToDTO(cart, dtos);
    }
}
