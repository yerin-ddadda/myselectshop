package com.sparta.week04_thr.Controller;

import com.sparta.week04_thr.models.Product;
import com.sparta.week04_thr.models.ProductMypriceRequestDto;
import com.sparta.week04_thr.models.ProductRepository;
import com.sparta.week04_thr.models.ProductRequestDto;
import com.sparta.week04_thr.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
@RequiredArgsConstructor // final로 선언된 멤버 변수를 자동으로 생성합니다.
@RestController // JSON으로 데이터를 주고받음을 선언합니다. Json 형태로 객체 데이터를 반환
public class ProductRestController {

    private final ProductRepository productRepository;
    //내가 원할때마다 자동적으로 생성될 수 있도록 RestController한테 꼭 알려주기 위해 final
    //ProductRestController를 사용할때는 반드시 productRepository가 있어야한다.

    private final ProductService productService;

    // 등록된 전체 상품 목록 조회
    @GetMapping("/api/products")
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @PostMapping("/api/products")
    public Product createProduct(@RequestBody ProductRequestDto requestDto){
        //어플리케이션에 전달되는 json 정보가 알아서 삽입되기 위해
        //클라이언트에서 서버로 필요한 데이터를 전송하기 위해서 JSON이라는 데이터를 요청 본문에 담아서 서버로 보내면, 서버에서는 @RequestBody 어노테이션을 사용하여 HTTP 요청 본문에 담긴 값들을 자바 객체로 변환 시켜, 객체에 저장시킵니다.
        Product product = new Product(requestDto);
        return productRepository.save(product);
    }

    @PutMapping("/api/products/{id}")
    public Long updateProduct(@PathVariable Long id,@RequestBody ProductMypriceRequestDto requestDto){ //바꿀 애와 어떻게바꿀지
        //spring3에 추가된 기능 중 하나인데, url 경로에 변수를 넣어주는 것이다.
        return productService.update(id, requestDto);
    }
}
