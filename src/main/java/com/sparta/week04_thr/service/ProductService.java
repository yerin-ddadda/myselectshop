package com.sparta.week04_thr.service;

import com.sparta.week04_thr.models.ItemDto;
import com.sparta.week04_thr.models.Product;
import com.sparta.week04_thr.models.ProductMypriceRequestDto;
import com.sparta.week04_thr.models.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    //관심 가격 정보가 변경되었을때 id를 받아서 찾고 업데이트
    @Transactional //db정보 업데이트
    public Long update(Long id, ProductMypriceRequestDto requestDto){ //뭐를 업데이트하는지,어떤값을 update하는지

        Product product = productRepository.findById(id).orElseThrow(
                () -> new NullPointerException("아이디가 잘못되었습니다.")
        );
        product.update(requestDto);
        return id;
    }

    @Transactional
    public Long updateBySearch(Long id, ItemDto itemDto){
        Product product = productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다")
        );
        product.updateByItemDto(itemDto);
        return id;
    }

}
