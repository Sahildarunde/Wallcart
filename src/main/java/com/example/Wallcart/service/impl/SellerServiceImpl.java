package com.example.Wallcart.service.impl;

import com.example.Wallcart.Enum.Category;
import com.example.Wallcart.Enum.ProductStatus;
import com.example.Wallcart.dao.ProductRepository;
import com.example.Wallcart.dao.SellerRepository;
import com.example.Wallcart.dto.RequestDto.SellerRequestDto;
import com.example.Wallcart.dto.ResponseDto.ProductResponseDto;
import com.example.Wallcart.dto.ResponseDto.SellerResponseDto;
import com.example.Wallcart.exception.SellerNotFoundExecption;
import com.example.Wallcart.model.Product;
import com.example.Wallcart.model.Seller;
import com.example.Wallcart.service.SellerService;
import com.example.Wallcart.transformer.ProductTransformer;
import com.example.Wallcart.transformer.SellerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {
    @Autowired SellerRepository sellerRepository;
    @Autowired
    ProductRepository productRepository;
    @Override
    public SellerResponseDto addSeller(SellerRequestDto sellerRequestDto) {
        //dto --> entity
        Seller seller = SellerTransformer.sellerRequestDtoToSeller(sellerRequestDto);

        //save to repository
        Seller savedSeller = sellerRepository.save(seller);

        //prepare the response dto
        return SellerTransformer.sellerToSellerResponseDto(savedSeller);
    }

    @Override
    public SellerResponseDto updateSeller(String emailId, SellerRequestDto sellerRequestDto) throws SellerNotFoundExecption {
        Seller seller = sellerRepository.findByEmailId(emailId);
        if(seller == null){
            throw new SellerNotFoundExecption("Sorry! seller Not Found");
        }

        seller.setName(sellerRequestDto.getName());
        seller.setEmailId(sellerRequestDto.getEmailId());
        seller.setMobNo(sellerRequestDto.getMobNo());

        Seller savedSeller = sellerRepository.save(seller);

        return SellerTransformer.sellerToSellerResponseDto(savedSeller);

    }

    @Override
    public List<SellerResponseDto> getByCategory(Category category) {
        List<Product> products = productRepository.findByCategory(category);

        List<SellerResponseDto> sellerResponseDtos = new ArrayList<>();
        for(Product product : products){
            Seller seller = product.getSeller();
            sellerResponseDtos.add(SellerTransformer.sellerToSellerResponseDto(seller));
        }
        return sellerResponseDtos;
    }

    @Override
    public SellerResponseDto getSellerWithMostProducts() {
        List<Seller> sellers = sellerRepository.findAll();
        SellerResponseDto sellerResponseDto = new SellerResponseDto();
        int products = 0;
        for(Seller seller : sellers){
            if(seller.getProducts().size()>products){
                products = seller.getProducts().size();
                sellerResponseDto = SellerTransformer.sellerToSellerResponseDto(seller);
            }
        }
        return sellerResponseDto;

    }

    @Override
    public SellerResponseDto getSellerWithLeastProducts() {
        List<Seller> sellers = sellerRepository.findAll();
        SellerResponseDto sellerResponseDto = new SellerResponseDto();
        int products = Integer.MAX_VALUE;
        for(Seller seller : sellers){
            if(seller.getProducts().size()<products){
                products = seller.getProducts().size();
                sellerResponseDto = SellerTransformer.sellerToSellerResponseDto(seller);
            }
        }
        return sellerResponseDto;
    }

    @Override
    public SellerResponseDto getSellerWithCostliestProduct() {
        Product product = productRepository.findTopByOrderByPriceDesc();
        Seller seller = product.getSeller();
        return SellerTransformer.sellerToSellerResponseDto(seller);
    }

    @Override
    public SellerResponseDto getSellerWithCheapestProduct() {
        Product product = productRepository.findTopByOrderByPriceAsc();
        Seller seller = product.getSeller();
        return SellerTransformer.sellerToSellerResponseDto(seller);
    }

}
