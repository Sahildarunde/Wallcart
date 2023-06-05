package com.example.Wallcart.dao;

import com.example.Wallcart.Enum.Category;
import com.example.Wallcart.Enum.ProductStatus;
import com.example.Wallcart.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategoryAndPrice(Category category, int price);

    List<Product> findByCategory(Category category);

    List<Product> findTop5ByCategoryOrderByPriceAsc(Category category);

    List<Product> findTop5ByCategoryOrderByPriceDesc(Category category);

    List<Product> findBySellerEmailId(String emailId);

    List<Product> findByCategoryAndProductStatus(Category category, ProductStatus productStatus);

    Product findTopByOrderByPriceDesc();

    Product findTopByOrderByPriceAsc();
}
