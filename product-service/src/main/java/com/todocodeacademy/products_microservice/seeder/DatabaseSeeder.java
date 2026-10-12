package com.todocodeacademy.products_microservice.seeder;

import com.todocodeacademy.products_microservice.model.Product;
import com.todocodeacademy.products_microservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String @NonNull ... args) {

        List<Product> products = createProducts();

        productRepository.saveAll(products);
    }

    private List<Product> createProducts() {
        return List.of(
                Product.builder().name("Laptop Pro").brand("TechCorp").unitPrice(new BigDecimal("1299.99")).build(),
                Product.builder().name("Wireless Mouse").brand("ErgoGear").unitPrice(new BigDecimal("29.95")).build(),
                Product.builder().name("Mechanical Keyboard").brand("KeyMaster").unitPrice(new BigDecimal("89.00")).build(),
                Product.builder().name("USB-C Hub").brand("ConnectPlus").unitPrice(new BigDecimal("45.50")).build(),
                Product.builder().name("27-inch Monitor").brand("ViewTech").unitPrice(new BigDecimal("349.99")).build(),
                Product.builder().name("Noise Cancelling Headphones").brand("AudioPro").unitPrice(new BigDecimal("199.00")).build(),
                Product.builder().name("Portable SSD 1TB").brand("StorageMax").unitPrice(new BigDecimal("89.99")).build(),
                Product.builder().name("Webcam HD").brand("VisionClear").unitPrice(new BigDecimal("59.95")).build(),
                Product.builder().name("Ergonomic Chair").brand("ComfortSeat").unitPrice(new BigDecimal("249.00")).build(),
                Product.builder().name("Standing Desk Converter").brand("DeskRise").unitPrice(new BigDecimal("179.99")).build(),
                Product.builder().name("Smart LED Bulb Pack").brand("LightSmart").unitPrice(new BigDecimal("34.50")).build(),
                Product.builder().name("Bluetooth Speaker").brand("SoundWave").unitPrice(new BigDecimal("69.99")).build(),
                Product.builder().name("External Battery 20kWh").brand("PowerBank Pro").unitPrice(new BigDecimal("79.95")).build(),
                Product.builder().name("Gaming Mouse Pad XL").brand("GameZone").unitPrice(new BigDecimal("39.99")).build(),
                Product.builder().name("Laptop Stand Aluminum").brand("ElevateTech").unitPrice(new BigDecimal("49.00")).build()
        );
    }
}
