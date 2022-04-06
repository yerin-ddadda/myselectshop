package com.sparta.week04_thr.Controller;

import com.sparta.week04_thr.models.Product;
import com.sparta.week04_thr.models.ProductMypriceRequestDto;
import com.sparta.week04_thr.models.ProductRepository;
import com.sparta.week04_thr.models.ProductRequestDto;
import com.sparta.week04_thr.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor //ProductRestController를 사용할때 productRepository가 꼭 있어야합니다
@RestController //JSON으로 응답하는 자동응답기
public class ProductRestController {

    private final ProductRepository productRepository;
    private final ProductService productService;

    @GetMapping("/api/products")
    public List<Product> readProducts(){
        return productRepository.findAll();
    }

    @PostMapping("/api/products")
    public Product createProducts(@RequestBody ProductRequestDto requestDto){
        Product product = new Product(requestDto);
        return productRepository.save(product);
    }

    @PutMapping("/api/products/{id}")
    public Long updateProduct(@PathVariable Long id,@RequestBody ProductMypriceRequestDto requestDto){
        return productService.update(id,requestDto);
    }
}
