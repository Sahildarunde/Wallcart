package com.example.Wallcart.controller;

import com.example.Wallcart.Enum.Category;
import com.example.Wallcart.dto.RequestDto.ProductRequestDto;
import com.example.Wallcart.dto.ResponseDto.ProductResponseDto;
import com.example.Wallcart.exception.SellerNotFoundExecption;
import com.example.Wallcart.service.ProductService;
import org.apache.coyote.Response;
import org.hibernate.boot.archive.scan.spi.ClassDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired ProductService productService;
    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody ProductRequestDto productRequestDto){
        try{
            ProductResponseDto productResponseDto = productService.addProduct(productRequestDto);
            return new ResponseEntity(productResponseDto, HttpStatus.CREATED);
        } catch (SellerNotFoundExecption e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/category/{category}/price/{price}")
    public ResponseEntity getAllProductsByCategoryAndPrice(@PathVariable("category") Category category, @PathVariable("price") int price){
        List<ProductResponseDto> productResponseDtoList = productService.getAllProductsByCategoryAndPrice(category,price);
        return new ResponseEntity(productResponseDtoList, HttpStatus.FOUND);
    }

    // get all the product of a particular category
    @GetMapping("/get/category/{category}")
    public ResponseEntity getAllProductsByCategory(@PathVariable("category") Category category){
        List<ProductResponseDto> productResponseDtoList = productService.getAllProductsByCategory(category);
        return new ResponseEntity(productResponseDtoList, HttpStatus.FOUND);
    }

    //get all the product in a category who have price greater than 500
    @GetMapping("/get/category/{category}/500")
    public ResponseEntity getProductsByCategoryAbove500(@PathVariable("category") Category category){
        List<ProductResponseDto> productResponseDtoList = productService.getProductsByCategoryAbove500(category);
        return new ResponseEntity(productResponseDtoList, HttpStatus.FOUND);
    }

    //get the top 5 cheapest products in a category
    @GetMapping("/get/top5/cheapest/{category}")
    public ResponseEntity getTop5CheapProducts(@PathVariable Category category){
        List<ProductResponseDto> productResponseDtoList = productService.getTop5CheapProducts(category);
        return new ResponseEntity(productResponseDtoList, HttpStatus.FOUND);
    }

    //get top 5 costliest product in a category
    @GetMapping("/get/top5/costliest/{category}")
    public ResponseEntity getTop5CostliestProducts(@PathVariable Category category){
        List<ProductResponseDto> productResponseDtoList = productService.getTop5CostliestProducts(category);
        return new ResponseEntity(productResponseDtoList, HttpStatus.FOUND);
    }

    //get all the product of seller based on emailId
    @GetMapping("/get/seller/{emailId}")
    public ResponseEntity getAllProductsBySeller(@PathVariable String emailId){
        List<ProductResponseDto> productResponseDtoList = productService.getAllProductsBySeller(emailId);
        return new ResponseEntity(productResponseDtoList, HttpStatus.FOUND);
    }

    //get all the out of stock product in a particular category & send email to them
    @GetMapping("/get/category/{category}/OUT-OF-STOCK")
    public ResponseEntity getOutOfStockProductsByCategory(@PathVariable("category") Category category){
        List<ProductResponseDto> productResponseDtoList = productService.getOutOfStockProductsByCategory(category);
        return new ResponseEntity(productResponseDtoList, HttpStatus.FOUND);
    }

}
