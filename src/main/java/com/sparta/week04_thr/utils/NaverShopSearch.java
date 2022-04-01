package com.sparta.week04_thr.utils;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class NaverShopSearch {
    public String search() {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", "qdAYBlCnHECWX8C3c0vs");
        headers.add("X-Naver-Client-Secret", "mJqet4jpvB");
        String body = ""; //body안에 응답 받은 결과물이 들어간다.

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange("https://openapi.naver.com/v1/search/shop.json?query=vegun", HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value(); //200이라는 숫자가 status에 들어간다.
        String response = responseEntity.getBody(); //결과화면이 문자열로 response에 들어간다.
        System.out.println("Response status: " + status);
        System.out.println(response);

        return response;
    }

    public static void main(String[] args) {
        NaverShopSearch naverShopSearch = new NaverShopSearch();
        naverShopSearch.search();
    }
}