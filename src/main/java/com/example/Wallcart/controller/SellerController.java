package com.example.Wallcart.controller;

import com.example.Wallcart.Enum.Category;
import com.example.Wallcart.dto.RequestDto.SellerRequestDto;
import com.example.Wallcart.dto.ResponseDto.ProductResponseDto;
import com.example.Wallcart.dto.ResponseDto.SellerResponseDto;
import com.example.Wallcart.exception.SellerNotFoundExecption;
import com.example.Wallcart.model.Seller;
import com.example.Wallcart.service.SellerService;
import com.example.Wallcart.transformer.SellerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.RedisSessionProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired SellerService sellerService;
    @PostMapping("/add")
    public ResponseEntity addSeller(@RequestBody SellerRequestDto sellerRequestDto){

        SellerResponseDto sellerResponseDto = sellerService.addSeller(sellerRequestDto);
        return new ResponseEntity(sellerResponseDto, HttpStatus.CREATED);
    }

    // update the seller ifo based on email
    @PutMapping("/update")
    public ResponseEntity updateSeller(@RequestParam String emailId, @RequestBody SellerRequestDto sellerRequestDto) throws SellerNotFoundExecption {

        try {
            SellerResponseDto sellerResponseDto = sellerService.updateSeller(emailId, sellerRequestDto);
            return new ResponseEntity(sellerResponseDto, HttpStatus.CREATED);
        } catch (SellerNotFoundExecption e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    //get all the seller of all the products of particular category
    @GetMapping("/get/category/{category}")
    public ResponseEntity getByCategory(@PathVariable("category") Category category){

        List<SellerResponseDto> sellerResponseDtos = sellerService.getByCategory(category);
        return new ResponseEntity(sellerResponseDtos, HttpStatus.CREATED);
    }

    //seller with highest number of products
    @GetMapping("/get/highest-number-of-products")
    public ResponseEntity getSellerWithMostProducts(){

        SellerResponseDto sellerResponseDto = sellerService.getSellerWithMostProducts();
        return new ResponseEntity(sellerResponseDto, HttpStatus.CREATED);
    }

    //get all the  products sold by a seller in a category


    // seller with min number of products
    @GetMapping("/get/minimum-number-of-products")
    public ResponseEntity getSellerWithLeastProducts(){

        SellerResponseDto sellerResponseDto = sellerService.getSellerWithLeastProducts();
        return new ResponseEntity(sellerResponseDto, HttpStatus.CREATED);
    }

    //seller(s) selling the costliest product
    @GetMapping("/get/seller-with-costliest-product")
    public ResponseEntity getSellerWithCostliestProduct(){

        SellerResponseDto sellerResponseDto = sellerService.getSellerWithCostliestProduct();
        return new ResponseEntity(sellerResponseDto, HttpStatus.CREATED);
    }


    //seller(s) selling the cheapest product

    @GetMapping("/get/seller-with-cheapest-product")
    public ResponseEntity getSellerWithCheapestProduct(){

        SellerResponseDto sellerResponseDto = sellerService.getSellerWithCheapestProduct();
        return new ResponseEntity(sellerResponseDto, HttpStatus.CREATED);
    }
}
