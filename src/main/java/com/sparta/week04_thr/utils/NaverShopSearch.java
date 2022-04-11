package com.sparta.week04_thr.utils;

import com.sparta.week04_thr.models.ItemDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

//검색어 던지면 dto로 데이터 주기
// 1. search함수로 검색해서 문자받고
// 2. 메소드에 받고 결과를 돌려주면 클라이언트는 정보를 받을 수 있다.


//개발자가 직접 작성한 class를 bean으로 등록하기 위한 어노테이션.
//bean 어노테이션은 개발자가 직접 제어가 불가능한 외부 라이브러리 등을 bean으로 만들려고 할때 사용된다.
//@controller, @service, @repository 어노테이션은 @component 어노테이션의 구체화된 형태이다.
@Component //@service처럼 스프링이 권한을 획득한것이다.
public class NaverShopSearch {

    public String search(String query) {
        RestTemplate rest = new RestTemplate();
        //기본적으로 스프링에서 RestTemplate을 사용한다.
        //The RestTemplate offers templates for common scenarios by HTTP method, in addition to the generalized exchange and execute methods that support of less frequent cases.
        //HTTP 메소드에 의한 평범한 기능 템플릿을 제공해주고, 더 나아가 특별한 케이스를 지원하는 exchange와 execute 메소드를 제공해준다.
        //Spring 4.x 부터 지원하는 Spring의 HTTP 통신 템플릿
        //- HTTP 요청 후 Json, xml, String 과 같은 응답을 받을 수 있는 템플릿
        //또는 Header, Content-Type등을 설정하여 외부 API 호출
        //- Http request를 지원하는 HttpClient를 사용함

        HttpHeaders headers = new HttpHeaders();
        //그리고 Header를 넣어줘야한다.
        //Spring Framework에서 제공해주는 HttpHeaders 클래스는 Header를 만들어준다.
        //HTTP POST를 요청할때 보내는 데이터(Body)를 설명해주는 헤더(Header)도 만들어서 같이 보내줘야 한다.
        //그리고 각각 클래스를 만들어주었다.

        headers.add("X-Naver-Client-Id", "qdAYBlCnHECWX8C3c0vs");
        headers.add("X-Naver-Client-Secret", "mJqet4jpvB");
        String body = "";
        //그리고 body를 만들고

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers); //응답 받은 결과물을 여기에 넣어준다
        //HttpEntity클래스는 HTTP요청또는 응답에 해당하는 HttpHeader와 HttpBody를 포함하는 클래스다.
        //Spring에서는 HttpEntity란 클래스를 제공하는데 이 클래스의 역할은 Http 프로토콜을 이용하는 통신의 header와 body 관련 정보를 저장할 수 있도록 한다.
        //위 body 와 headers 를 HttpEntity<> request 에 담는다.
        //즉, 통신 메시지 관련 header와 body의 값들을 하나의 객체로 저장하는 것이 HttpEntity 클래스 객체
        //HttpEntity 를 통해 Http 메시지에 직접 접근하여 body 메시지를 파싱합니다
        //HttpEntity는 body 정보 뿐만 아니라 header정보까지 파싱 가능합니다. Http messgae에 직접 접근하기 때문이지요


        ResponseEntity<String> responseEntity = rest.exchange("https://openapi.naver.com/v1/search/shop.json?query=" + query, HttpMethod.GET, requestEntity, String.class);
        //HttpEntity 클래스를 상속받아 구현한 클래스가 RequestEntity, ResponseEntity 클래스이다.
        // ResponseEntity는 사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스이다. 따라서 HttpStatus, HttpHeaders, HttpBody를 포함한다.
        //HttpEntity와 거의 기능이 같지만 RequestEntity 는 부가적인 기능을 제공합니다. (url 정보 등 ...)
        //RestTemplate의 exchang 메소드 - HTTP 헤더를 새로 만들 수 있고 어떤 HTTP 메서드도 사용가능하다
        //get 요청에 header 값이 필요한 경우 exchange메소드를 사용한다.


        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value(); //잘받아온 200이란 숫자가 여기 들어간다
        String response = responseEntity.getBody(); //직접보낸 결과화면이 문자열 하나로된것, 서버에서 응답해준 데이터가 저장될 것이다.
        System.out.println("Response status: " + status);
        System.out.println(response);

        return response;
    }


    public List<ItemDto> fromJSONtoItems(String result) {
        JSONObject rjson = new JSONObject(result);
        JSONArray items = rjson.getJSONArray("items");

        List<ItemDto> itemDtoList = new ArrayList<>();

        for (int i = 0; i < items.length(); i++) {
            JSONObject itemJson = items.getJSONObject(i);
            ItemDto itemDto = new ItemDto(itemJson);
            itemDtoList.add(itemDto);
        }
        return itemDtoList;
    }
}


//    public static void main(String[] args) {
//        NaverShopSearch naverShopSearch = new NaverShopSearch();
//        String result = naverShopSearch.search("아이맥");
//

//검색 결과를 문자열에서 dto로 바꿔야한다. 그러기 위해서는 org.json(자바에서 json을 다루기위한 라이브러리)라는 패키지를 설치해야한다.
//json은 javascript에서 데이터를 전달하기위해 만들어졌고, key-value형태로 데이터를 갖고있다.


////        //json을 자바에서 다루려면 JSONObject, JSONArray 가 필요하다.
////        //JSONArray json이 배열로 이루어져있을때 배열을 다루는 클래스
////        //JSONObject 중괄호로 시작하는 거를 다루기 위해 필요한것
////        JSONObject rjson = new JSONObject(result); //result문자열을 받아서 JSONObject로 만든다. 아이맥으로 입력한 검색 결과가 rjson이라는걸로 만들어짐
////        System.out.println(rjson);
////        JSONArray items = rjson.getJSONArray("items"); //item이라는 키값으로 검색 결과가 들어와있음
////
////        List<ItemDto> itemDtoList = new ArrayList<>();
////
////        for (int i=0; i<items.length();i++){
////            JSONObject itemJson = items.getJSONObject(i);
//////            System.out.println(itemJson);
//////            String title = itemJson.getString("title");
//////            String link = itemJson.getString("link");
//////            String image = itemJson.getString("image");
//////            int lprice = itemJson.getInt("lprice");
////            ItemDto itemDto = new ItemDto(itemJson);
////            itemDtoList.add(itemDto);
////            배열 검색 결과를 필요로하기때문에
////
////        }
