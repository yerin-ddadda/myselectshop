package com.sparta.week04_thr.utils;

import com.sparta.week04_thr.models.ItemDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class NaverShopSearch {
    public String search(String query) {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", "qdAYBlCnHECWX8C3c0vs");
        headers.add("X-Naver-Client-Secret", "mJqet4jpvB");
        String body = ""; //body안에 응답 받은 결과물이 들어간다.

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange("https://openapi.naver.com/v1/search/shop.json?query="+query, HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value(); //200이라는 숫자가 status에 들어간다.
        String response = responseEntity.getBody(); //결과화면이 문자열로 response에 들어간다.
        System.out.println("Response status: " + status);
        System.out.println(response);

        return response;
    }

    public List<ItemDto> fromJSONtoItems(String result){
        JSONObject rjson = new JSONObject(result); //문자열을 JSONObject로 만들어 rjson 만들기
        System.out.println(rjson);
        JSONArray items = rjson.getJSONArray("items");

        List<ItemDto> itemDtoList = new ArrayList<>();

        for (int i=0;i<items.length();i++){
            JSONObject itemJson = items.getJSONObject(i);
//            System.out.println(itemJson);
//            String title = itemJson.getString("title");
//            String image = itemJson.getString("image");
//            String link = itemJson.getString("link");
//            int lprice = itemJson.getInt("lprice");
            ItemDto itemDto = new ItemDto(itemJson);
            itemDtoList.add(itemDto);
        }
        return itemDtoList;
    }

}