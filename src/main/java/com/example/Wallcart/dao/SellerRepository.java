package com.example.Wallcart.dao;

import com.example.Wallcart.Enum.Category;
import com.example.Wallcart.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Integer> {

    Seller findByEmailId(String emailId);

//    List<Seller> findByCategory(String category);
}
