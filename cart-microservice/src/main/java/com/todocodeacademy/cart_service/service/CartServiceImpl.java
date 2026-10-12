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
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.todocodeacademy.cart_service.factory.DTOFactory.errorDTO;

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

        Cart cart = getCart(cartID);

        repository.delete(cart);
    }

    @Override
    public CartResponseDTO getCartInfoByID(Long cartID) {

        Cart cart = getCart(cartID);

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
    @CircuitBreaker(name = "product-microservice", fallbackMethod = "fallbackGetProductByName")
    @Retry(name = "product-microservice")
    public ProductDTO getProductByName(String productName) {

        try {

            return apiClient.getProductInfoByName(productName);

        } catch (Exception exception) {

            return fallbackGetProductByName(productName, exception);
        }
    }

    @Override
    @Transactional
    public void addProductToCart(Long cartID, Long productID) {

        Cart cart = getCart(cartID);

        if (apiClient.getProductByID(productID) != null) {

            cart.getProducts().add(productID);

            updateCartTotal(cart, productID, CART_CHANGE.ADD_PRODUCT);

            repository.save(cart);
        }
    }

    @Override
    @Transactional
    public void removeProductFromCart(Long cartID, Long productID) {

        Cart cart = getCart(cartID);

        if (cart.getProducts().remove(productID)) {

            updateCartTotal(cart, productID, CART_CHANGE.REMOVE_PRODUCT);

            repository.save(cart);
        }
    }

    private void updateCartTotal(Cart cart, Long productID, CART_CHANGE operation) {

        switch (operation) {

            case ADD_PRODUCT -> {

                BigDecimal price = apiClient.getProductByID(productID).getUnitPrice();

                cart.setTotal(cart.getTotal().add(price));
            }

            case REMOVE_PRODUCT -> {

                BigDecimal price = apiClient.getProductByID(productID).getUnitPrice();

                cart.setTotal(cart.getTotal().subtract(price));
            }
        }
    }

    private @NonNull Cart getCart(Long cartID) {

        return repository.findById(cartID).orElseThrow(CartNotFoundException::new);
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

    public enum CART_CHANGE {

        ADD_PRODUCT,
        REMOVE_PRODUCT
    }

    public ProductDTO fallbackGetProductByName(String name, Throwable throwable) {

        return errorDTO(name, throwable);
    }
}
