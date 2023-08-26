package com.bestbuy.ecommerce.service.serviceImpl;

import com.bestbuy.ecommerce.domain.entity.Brand;
import com.bestbuy.ecommerce.domain.repository.BrandRepository;
import com.bestbuy.ecommerce.dto.request.BrandRequest;
import com.bestbuy.ecommerce.dto.request.BrandSearchRequest;
import com.bestbuy.ecommerce.dto.responses.BrandResponse;
import com.bestbuy.ecommerce.exceptions.BrandNotFoundException;
import com.bestbuy.ecommerce.search.BrandSearchDao;
import com.bestbuy.ecommerce.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    private final BrandSearchDao brandSearchDao;

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public BrandResponse createBrand(BrandRequest brandRequest) {
        Brand brand = mapToBrand(brandRequest);
        Brand newBrand = brandRepository.save(brand);
        return mapToBrandResponse(newBrand);
    }

    @Override
    public List<BrandResponse> getAllBrands() {
        List<Brand> brands = brandRepository.findAll();
        return brands.stream().map(this::mapToBrandResponse).collect(Collectors.toList());
    }

    @Override
    public BrandResponse getBrandById(Long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException("Brand not found"));
        return mapToBrandResponse(brand);
    }

    @Override
    public BrandResponse updateBrand(Long brandId, BrandRequest brandRequest) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException("Brand not found"));

        // Update the brand properties
        brand.setBrandDescription(brandRequest.getBrandDescription());
        brand.setBrandName(brandRequest.getBrandName());
        brand.setLogoUrl(brandRequest.getLogoUrl());

        // Save the updated brand
        Brand updatedBrand = brandRepository.save(brand);
        return mapToBrandResponse(updatedBrand);
    }

    @Override
    public String deleteBrand(Long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException("Brand not found"));

        brandRepository.delete(brand);
        return "Brand is successfully deleted";
    }

    @Override
    public List<Brand> searchBrandByName(BrandSearchRequest brandSearchRequest) {
        return brandSearchDao.brandSearchCriteria(brandSearchRequest);
    }

    private BrandResponse mapToBrandResponse(Brand brand) {
        return BrandResponse.builder()
                .brandName(brand.getBrandName())
                .brandDescription(brand.getBrandDescription())
//                .createAt(LocalDateTime.parse(brand.getCreatedAt().format(dateFormat)))
                .build();
    }



    private Brand mapToBrand(BrandRequest brandRequest) {
        return Brand.builder()
                .brandName(brandRequest.getBrandName())
                .brandDescription(brandRequest.getBrandDescription())
                .logoUrl(brandRequest.getLogoUrl())
                .build();
    }
}
