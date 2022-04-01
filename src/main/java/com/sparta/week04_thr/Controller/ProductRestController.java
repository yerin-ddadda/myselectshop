package com.sparta.week04_thr.Controller;

import com.sparta.week04_thr.models.Product;
import com.sparta.week04_thr.models.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor //ProductRestController를 사용할때 productRepository가 꼭 있어야합니다
@RestController //JSON으로 응답하는 자동응답기
public class ProductRestController {

    private final ProductRepository productRepository;

    @GetMapping("/api/products")
    public List<Product> readProducts(){
        return productRepository.findAll();
    }
}
