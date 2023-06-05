package com.example.Wallcart.service.impl;

import com.example.Wallcart.Enum.Category;
import com.example.Wallcart.Enum.ProductStatus;
import com.example.Wallcart.dao.ProductRepository;
import com.example.Wallcart.dao.SellerRepository;
import com.example.Wallcart.dto.RequestDto.ProductRequestDto;
import com.example.Wallcart.dto.RequestDto.SellerRequestDto;
import com.example.Wallcart.dto.ResponseDto.ProductResponseDto;
import com.example.Wallcart.exception.SellerNotFoundExecption;
import com.example.Wallcart.model.Product;
import com.example.Wallcart.model.Seller;
import com.example.Wallcart.service.ProductService;
import com.example.Wallcart.transformer.ProductTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    SellerRepository sellerRepository;
    @Autowired ProductRepository productRepository;
    @Autowired
    JavaMailSender emailSender;
    @Override
    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) throws SellerNotFoundExecption {
        //check if seller exist or not
        Seller seller = sellerRepository.findByEmailId(productRequestDto.getSellerEmailId());
        if(seller==null){
            throw new SellerNotFoundExecption("Email Id not Registered!!");
        }

        //dto --> entity
        Product product = ProductTransformer.productRequestDtoToProduct(productRequestDto);
        product.setSeller(seller);
        seller.getProducts().add(product);

        //save product
        Seller savedSeller = sellerRepository.save(seller);
        Product savedProduct = savedSeller.getProducts().get(savedSeller.getProducts().size()-1);

        //prepare response dto
        return ProductTransformer.productToProductResponseDto(product);
    }

    @Override
    public List<ProductResponseDto> getAllProductsByCategoryAndPrice(Category category, int price){
        List<Product> products = productRepository.findByCategoryAndPrice(category, price);

        //prepare a list of response dto

        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for(Product product : products){
            productResponseDtos.add(ProductTransformer.productToProductResponseDto(product));
        }
        return productResponseDtos;
    }

    @Override
    public List<ProductResponseDto> getAllProductsByCategory(Category category) {
        List<Product> products = productRepository.findByCategory(category);
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for(Product product : products){
            productResponseDtos.add(ProductTransformer.productToProductResponseDto(product));
        }
        return productResponseDtos;
    }

    @Override
    public List<ProductResponseDto> getProductsByCategoryAbove500(Category category) {
        List<Product> products = productRepository.findByCategoryAndPrice(category,500);

        //prepare a list of response dto

        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for(Product product : products){
            productResponseDtos.add(ProductTransformer.productToProductResponseDto(product));
        }
        return productResponseDtos;
    }

    @Override
    public List<ProductResponseDto> getTop5CheapProducts(Category category) {
        List<Product> products = productRepository.findTop5ByCategoryOrderByPriceAsc(category);
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for(Product product : products){
            productResponseDtos.add(ProductTransformer.productToProductResponseDto(product));
        }
        return productResponseDtos;
    }

    @Override
    public List<ProductResponseDto> getTop5CostliestProducts(Category category) {
        List<Product> products = productRepository.findTop5ByCategoryOrderByPriceDesc(category);
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for(Product product : products){
            productResponseDtos.add(ProductTransformer.productToProductResponseDto(product));
        }
        return productResponseDtos;
    }

    @Override
    public List<ProductResponseDto> getAllProductsBySeller(String emailId) {
        List<Product> products = productRepository.findBySellerEmailId(emailId);
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for(Product product : products){
            productResponseDtos.add(ProductTransformer.productToProductResponseDto(product));
        }
        return productResponseDtos;
    }

    @Override
    public List<ProductResponseDto> getOutOfStockProductsByCategory(Category category) {
        List<Product> products = productRepository.findByCategoryAndProductStatus(category, ProductStatus.OUT_OF_STOCK);


        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for(Product product : products){
            productResponseDtos.add(ProductTransformer.productToProductResponseDto(product));

            // send Emails to seller for out of stock products

            String text = "Hii! This is to inform you that your product " +product.getName()
                    +" of category "+ product.getCategory() +" is currently out of stock.";
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("projectdemomail32@gmail.com");
            message.setTo(product.getSeller().getEmailId()); //
            message.setSubject("Your Product is out of stock");
            message.setText(text);
            emailSender.send(message);
        }
        return productResponseDtos;
    }
}
