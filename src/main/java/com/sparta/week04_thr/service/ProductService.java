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

    @Transactional //DB관련 정보이다 + DB 정보를 업데이트 해줘야한다 -> 트랜젝션이 일어난다.
    public Long update(Long id, ProductMypriceRequestDto requestDto){
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NullPointerException("아이디가 존재하지 않습니다.")
        );
        product.update(requestDto);
        return id;
    }

    @Transactional //db가 업데이트 해야한다
    public Long updateBySearch(Long id, ItemDto itemDto){
        Product product = productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        product.updateByItemDto(itemDto);
        return id;
    }
}
