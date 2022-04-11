package com.sparta.week04_thr.models;

import lombok.Getter;

@Getter//각각 꺼낼수 있도록
public class ProductRequestDto { //상품 생성과 관계가있음
    private String title;
    private String link;
    private String image;
    private int lprice;
}
